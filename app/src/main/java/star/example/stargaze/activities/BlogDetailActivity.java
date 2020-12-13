package star.example.stargaze.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import star.example.stargaze.R;

import star.example.stargaze.adapters.MoreBlogAdapter;

import star.example.stargaze.databinding.ActivityBlogDetailBinding;
import star.example.stargaze.models.response.BlogResp;
import star.example.stargaze.sharedPref.MyPreferences;
import star.example.stargaze.sharedPref.PrefConf;
import star.example.stargaze.utils.AppUtils;
import star.example.stargaze.utils.Constants;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BlogDetailActivity extends AppCompatActivity implements View.OnClickListener, MoreBlogAdapter.OnBlogItemListener {
    private ActivityBlogDetailBinding binding;
    private Context context;
    private List<BlogResp>blog;
    private int position;
    private boolean isAlternate = true;
    private View view;
    private Dialog dialog;
    private boolean isLiked = false;
    private  boolean isDisliked = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_blog_detail);
        context = BlogDetailActivity.this;
        position = getIntent().getIntExtra("pos",0);
        view = binding.getRoot();
        dialog = AppUtils.hideShowProgress(context);
        Gson gson = new Gson();
        Type type = new TypeToken<List<BlogResp>>() {
        }.getType();
        blog = gson.fromJson(MyPreferences.getInstance(this).getString(PrefConf.BLOGSTSTR, ""), type);
        setData(blog,position);
        String  img =  "https://stargazeevents.s3.ap-south-1.amazonaws.com/celebrity/1596099406639.jpeg";
        Glide.with(context).load(img).apply(new RequestOptions().circleCrop()).into(binding.imgWriter);
        binding.moreBlogRecycler.setLayoutManager(new LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false));
        binding.moreBlogRecycler.setAdapter(new MoreBlogAdapter(blog,context,this));

        binding.relativeAlternate.setOnClickListener(this);
        binding.imgLike.setOnClickListener(this);
        binding.imgDislike.setOnClickListener(this);

    }


    private void setData(List<BlogResp> blog,int position){

        binding.txtContent.setText(blog.get(position).getArticle());

        Spanned text = Html.fromHtml("<b>"+blog.get(position).getSubject()+"</b> <br/> "+"<font color=#B9B7B7>"+
                blog.get(position).getWriter());
        binding.txtContentHeader.setText(text);


        if (blog.get(position).getImages().size() != 0){
            Glide.with(context).load(Constants.IMG_URL + "" + blog.get(position).getImages().get(0).getUrl()).placeholder(R.drawable.place_holder).into(binding.imgHeader);

//            Glide.with(context).load(Constants.IMG_URL + "" + blogs.get(position).getImages().get(0).getUrl()).placeholder(R.drawable.place_holder).into(binding.imgMiddle);
        }else {
           binding.imgMiddle.setImageResource(R.drawable.place_holder);

        }

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.relative_alternate:
                if (isAlternate){
                    binding.linearCommentsContainer.setVisibility(View.VISIBLE);
                    isAlternate = false;
                    binding.imgAlternateUpDown.setImageResource(R.drawable.up_arrow);
                }else {
                    binding.linearCommentsContainer.setVisibility(View.GONE);
                    isAlternate = true;
                    binding.imgAlternateUpDown.setImageResource(R.drawable.down_arrow);
                }

                break;

            case R.id.img_like:
                if(!isDisliked) {
                    likeDislike(context, true, "like");
                    isLiked = true;
                    isDisliked = true;
                }
                break;
            case R.id.img_dislike:
                if(!isLiked) {
                    likeDislike(context, false, "dislike");
                    isLiked = true;
                    isDisliked = true;
                }
                break;

        }
//        if (view.getId() == R.id.relative_alternate){
//            if (isAlternate){
//                binding.linearCommentsContainer.setVisibility(View.VISIBLE);
//                isAlternate = false;
//                binding.imgAlternateUpDown.setImageResource(R.drawable.up_arrow);
//            }else {
//                binding.linearCommentsContainer.setVisibility(View.GONE);
//                isAlternate = true;
//                binding.imgAlternateUpDown.setImageResource(R.drawable.down_arrow);
//            }
//        }
    }

    public void likeDislike(Context context,boolean islike,String key){
        dialog.show();
        Call<ResponseBody> call = AppUtils.getApi(context).blogLikeDislike(blog.get(position).getId(),key);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                dialog.dismiss();
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    if(islike){
                        binding.imgLike.setImageResource(R.drawable.ic_like_white);
                    }else {
                        binding.imgDislike.setImageResource(R.drawable.ic_dislike_white);
                    }
                } else {
                    try {
                        String errorRes = response.errorBody().string();
                        JSONObject jsonObject =new JSONObject(errorRes);
                        String err_msg  = jsonObject.getString("error");
                        int status= jsonObject.getInt("status");
                        if(err_msg.equalsIgnoreCase("Token Expired")){
                            AppUtils.alertMessage((AppCompatActivity)context,"Your account is logged in to new device or your session is expired!");
                        }else {
                            Snackbar.make(view, err_msg, Snackbar.LENGTH_SHORT).show();
                        }

                    }
                    catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                dialog.dismiss();
                Snackbar.make(view,t.getMessage(),Snackbar.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onBlogClick(List<BlogResp> data, int position) {
              isLiked = false;
              isDisliked = false;
                this.blog = data;
                setData(data,position);
        binding.imgLike.setImageResource(R.drawable.ic_like);
        binding.imgDislike.setImageResource(R.drawable.ic_dislike);
    }
}
