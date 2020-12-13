package star.example.stargaze.view_presenter;


import android.content.Context;

import star.example.stargaze.models.response.ProfileImageResp;
import star.example.stargaze.utils.AppUtils;

import org.json.JSONObject;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityPresenter {
    private MainView view;

    public MainActivityPresenter(MainView view) {
        this.view = view;
    }

    public void uploadImage(MultipartBody.Part image){
        Call<ProfileImageResp> call = AppUtils.getApi((Context) view).uploadPic(image);
        view.showHideProgress(true);
        call.enqueue(new Callback<ProfileImageResp>() {
            @Override
            public void onResponse(Call<ProfileImageResp> call, Response<ProfileImageResp> response) {
                view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onProfileUpload(response.body());
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
            public void onFailure(Call<ProfileImageResp> call, Throwable t) {
                view.showHideProgress(false);
                view.onFailure(t);
            }
        });

    }

    public interface MainView{
        void showHideProgress(boolean isShow);
        void onError(String message);
        void onProfileUpload(ProfileImageResp imageResp);
        void onFailure(Throwable t);
    }
}
