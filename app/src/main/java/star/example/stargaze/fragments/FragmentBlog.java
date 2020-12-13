package star.example.stargaze.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.viewpager.widget.ViewPager;

import star.example.stargaze.R;
import star.example.stargaze.activities.BlogDetailActivity;
import star.example.stargaze.activities.BlogSearchActivity;
import star.example.stargaze.activities.MainActivity;
import star.example.stargaze.adapters.BlogAdapter;
import star.example.stargaze.adapters.BlogPagerAdapter;

import star.example.stargaze.databinding.FragmentBlogBinding;

import star.example.stargaze.models.response.BlogResp;
import star.example.stargaze.sharedPref.MyPreferences;
import star.example.stargaze.sharedPref.PrefConf;
import star.example.stargaze.utils.AppUtils;
import star.example.stargaze.view_presenter.BlogPresenter;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class FragmentBlog extends Fragment implements BlogPresenter.BlogView, BlogAdapter.OnBlogItemListener, View.OnClickListener {

    private FragmentBlogBinding binding;
    private Context context;
    private View view;
    private Dialog dialog;
    private BlogPresenter presenter;

    private Timer timer;
    private final long delay = 400;
    private final long repeat = 2500;
    private int currentPage = 0;
    private Handler handler;
    private ImageView[] dots;
    private int dots_count = 0;
    private LinearLayout dots_panel;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_blog, container, false);
        dialog = AppUtils.hideShowProgress(context);
        view = binding.getRoot();
        handler = new Handler();
        presenter = new BlogPresenter(this);
        presenter.getBlog(context);
        binding.txtSearch.setOnClickListener(this);
        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof MainActivity){
            this.context = context;
        }
    }

    @Override
    public void showHideProgress(boolean isShow) {
        if(isShow){
            dialog.show();
        }else{
            dialog.dismiss();
        }
    }

    @Override
    public void onError(String message) {
        if(message.equalsIgnoreCase("Token Expired")){
            AppUtils.alertMessage((AppCompatActivity) context,"Your account is logged in to new device or your session is expired!");
        }else {
            Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSuccess(List<BlogResp> blogResp) {

        List<String>blogImages = new ArrayList<>();
        if(blogResp!=null){
            for(int i =0;i<blogResp.size();i++){
                BlogResp blog = blogResp.get(i);
                if(blog.getImages().size()>0)
                    blogImages.add(blog.getImages().get(0).getUrl());
            }
        }
        if(blogImages.size()>0){

            if (blogResp.size() > 0) {
                BlogPagerAdapter pagerAdapter = new BlogPagerAdapter(blogImages,blogResp, context);
                binding.blogPager.setAdapter(pagerAdapter);
                final Runnable update = () -> {
                    if (currentPage == dots_count) {
                        currentPage = 0;
                    }
                    binding.blogPager.setCurrentItem(currentPage++, true);
                };

                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        handler.post(update);
                    }
                }, delay, repeat);

                dots_count = pagerAdapter.getCount();
                dots = new ImageView[dots_count];
                for (int i = 0; i < dots_count; i++) {
                    dots[i] = new ImageView(context);
                    dots[i].setImageDrawable(ContextCompat.getDrawable(context, R.drawable.non_active_dot));

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                    params.setMargins(8, 0, 8, 0);

                    binding.dotsPanel.addView(dots[i], params);

                }
                dots[0].setImageDrawable(ContextCompat.getDrawable(context, R.drawable.active_dot));

                binding.blogPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        for (int i = 0; i < dots_count; i++) {
                            dots[i].setImageDrawable(ContextCompat.getDrawable(context, R.drawable.non_active_dot));
                        }

                        dots[position].setImageDrawable(ContextCompat.getDrawable(context, R.drawable.active_dot));

                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {
                    }
                });

            }
            binding.pagerContainer.setVisibility(View.VISIBLE);

            binding.blogRecyclerView.setLayoutManager(new GridLayoutManager(context,2));
            binding.blogRecyclerView.setAdapter(new BlogAdapter(blogResp,context,this));
        }
    }

    @Override
    public void onFailure(Throwable t) {

    }

    @Override
    public void onBlogClick(List<BlogResp> data, int position) {
        Gson gson = new Gson();
        String blogListStr = gson.toJson(data);
        System.out.println("json str "+blogListStr);
        MyPreferences.getInstance(context).putString(PrefConf.BLOGSTSTR,blogListStr);

        Intent intent = new Intent(context, BlogDetailActivity.class);
        intent.putExtra("pos",position);
//        intent.putExtra(Constants.BLOG,blogListStr)
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.txt_search){
            Intent intent = new Intent(context, BlogSearchActivity.class);
            startActivity(intent);
        }
    }
}
