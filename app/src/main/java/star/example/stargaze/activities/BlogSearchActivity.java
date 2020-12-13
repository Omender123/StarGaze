package star.example.stargaze.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import star.example.stargaze.R;
import star.example.stargaze.adapters.BlogSearchAdapter;

import star.example.stargaze.databinding.ActivitySearchBinding;
import star.example.stargaze.models.request.Filters;
import star.example.stargaze.models.request.SearchReqBody;
import star.example.stargaze.models.response.BlogResp;
import star.example.stargaze.sharedPref.MyPreferences;
import star.example.stargaze.sharedPref.PrefConf;
import star.example.stargaze.utils.AppUtils;
import com.google.gson.Gson;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BlogSearchActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivitySearchBinding binding;
    private Context context;
    private View view;
    private Dialog dialog;
    private String search_txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_search);
        context = BlogSearchActivity.this;

        initControl();
    }

    private void initControl() {
        binding.ivBack.setOnClickListener(this);

        binding.etSearch.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_search,0,0,0);
        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(final CharSequence s, int start, int before, int count) {
                updateUI(false, binding.progressBar);
            }


            @Override
            public void afterTextChanged(final Editable s) {

                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {

                        search_txt = s.toString();
                        Filters searchFilters = new Filters(search_txt);
                        SearchReqBody requestBody = new SearchReqBody(searchFilters);

                        if(this != null)
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    if (binding.etSearch.getText().length() > 0) {
                                        setDataWhenClinicPresent();
                                        searching(BlogSearchActivity.this, binding.progressBar, requestBody);
                                    }else setDataWhenClinicNotPresent();
                                }
                            });

                    }

                }, 2500);


            }
        });
    }


    public void searching(final Context context, final ProgressBar progress_bar, SearchReqBody searchRequestBody) {

        if (AppUtils.isNetworkConnected(context)) {
//            updateUI(false, progress_bar);
            Call<List<BlogResp>> call = AppUtils.getApi(context).blogSearch(searchRequestBody);

            call.enqueue(new Callback<List<BlogResp>>() {
                @Override
                public void onResponse(Call<List<BlogResp>> call, Response<List<BlogResp>> response) {
                    updateUI(true, progress_bar);
                    if (response.isSuccessful() && response.body() != null && response.code() == 200) {

                        if (response.body() != null && response.body().size() > 0) {
                            binding.rvList.setLayoutManager(new GridLayoutManager(BlogSearchActivity.this,2));
                            binding.rvList.setAdapter(new BlogSearchAdapter(context, response.body(),listener));
                        } else setDataWhenClinicNotPresent();

                    } else {
                        setDataWhenClinicNotPresent();
                        Toast.makeText(context, "Something went wrong.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<BlogResp>> call, Throwable t) {
                    updateUI(true, binding.progressBar);
                    call.cancel();
                    Toast.makeText(context, "Something went wrong.", Toast.LENGTH_SHORT).show();

                }
            });
        }else{
            Toast.makeText(context,getString(R.string.txt_plz_connect_internet), Toast.LENGTH_SHORT).show();
        }

    }

    public  void updateUI(Boolean isEnable, ProgressBar progressBar) {
        if (isEnable)
            progressBar.setVisibility(View.GONE);
        else
            progressBar.setVisibility(View.VISIBLE);
        if (isEnable) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        } else {
            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            );
        }
    }

    public void setDataWhenClinicNotPresent(){
        binding.rvList.setVisibility(View.GONE);
//        tv.setText(ViewUtils.dataInSelectedLanguage(this,data.getTxt_no_record_found().getEn(),data.getTxt_no_record_found().getAr()));
        //tv.setText("No record found.");
        updateUI(true,binding.progressBar);
        binding.tv.setVisibility(View.VISIBLE);
    }

    public void setDataWhenClinicPresent(){
        binding.rvList.setVisibility(View.VISIBLE);
        binding.tv.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                onBackPressed();
                break;
        }
    }

    BlogSearchAdapter.OnBlogItemListener listener = new BlogSearchAdapter.OnBlogItemListener() {
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
    };
}
