package star.example.stargaze.authentication;

import android.content.Context;


import star.example.stargaze.models.request.Password;
import star.example.stargaze.utils.AppUtils;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PresenterResetPassword {
    ResetPasswordView view;

    public PresenterResetPassword(ResetPasswordView view) {
        this.view = view;
    }
    public void resetPassword(Password password){
        Call<ResponseBody> call = AppUtils.getApi((Context) view).changePassword(password);
        view.showHideProgress(true);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onSuccess(response.message());
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
    public  interface  ResetPasswordView{
        void showHideProgress(boolean isShow);
        void onError(String message);
        void onSuccess(String message);
        void onFailure(Throwable t);
    }
}
