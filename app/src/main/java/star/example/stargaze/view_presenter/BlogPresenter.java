package star.example.stargaze.view_presenter;


import android.content.Context;

import star.example.stargaze.models.response.BlogResp;
import star.example.stargaze.utils.AppUtils;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BlogPresenter {
    private BlogView view;

    public BlogPresenter(BlogView view) {
        this.view = view;
    }

    public void getBlog(Context context){
        view.showHideProgress(true);
        Call<List<BlogResp>> call = AppUtils.getApi(context).getBlog();
        call.enqueue(new Callback<List<BlogResp>>() {
            @Override
            public void onResponse(Call<List<BlogResp>> call, Response<List<BlogResp>> response) {
                view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onSuccess(response.body());
                } else {
                    try {
                        String errorRes = response.errorBody().string();
                        JSONObject jsonObject =new JSONObject(errorRes);
                        String err_msg  = jsonObject.getString("error");
                        int status= jsonObject.getInt("status");
                        view.onError(err_msg);
                    }
                    catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<BlogResp>> call, Throwable t) {
                view.showHideProgress(false);
                view.onFailure(t);
            }
        });
    }

    public interface BlogView{
        void showHideProgress(boolean isShow);
        void onError(String message);
        void onSuccess(List<BlogResp> blogResp);
        void onFailure(Throwable t);
    }
}
