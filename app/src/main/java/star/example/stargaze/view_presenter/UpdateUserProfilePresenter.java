package star.example.stargaze.view_presenter;

import android.content.Context;

import star.example.stargaze.models.request.Password;
import star.example.stargaze.models.request.ProfileEdit;
import star.example.stargaze.models.response.ProfileUpdateResp;
import star.example.stargaze.utils.AppUtils;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateUserProfilePresenter {

    private ProfileView view;

    public UpdateUserProfilePresenter(ProfileView view) {
        this.view = view;
    }

    public void updateProfile(ProfileEdit profile){
        view.showHideProgress(true);
        Call<ProfileUpdateResp> userCall = AppUtils.getApi((Context)view).updateProfile(profile);
        userCall.enqueue(new Callback<ProfileUpdateResp>() {
            @Override
            public void onResponse(Call<ProfileUpdateResp> call, Response<ProfileUpdateResp> response) {
                view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onSuccess(response.body(),response.message());
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
            public void onFailure(Call<ProfileUpdateResp> call, Throwable t) {
                view.showHideProgress(false);
                view.onFailure(t);
            }
        });
    }

    public void changePassword(Password password){
        Call<ResponseBody> call = AppUtils.getApi((Context) view).changePassword(password);
        view.showHideProgress(true);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onChangePassword(response.message());
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
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                view.showHideProgress(false);
                view.onFailure(t);
            }
        });

    }


    public interface ProfileView{
        void showHideProgress(boolean isShow);
        void onError(String message);
        void onSuccess(ProfileUpdateResp resp,String message);
        void onChangePassword(String message);
        void onFailure(Throwable t);
    }
}
