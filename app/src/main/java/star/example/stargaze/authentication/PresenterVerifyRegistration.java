package star.example.stargaze.authentication;

import android.content.Context;


import star.example.stargaze.models.request.Phone;
import star.example.stargaze.models.request.RegisterOTP;
import star.example.stargaze.utils.AppUtils;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PresenterVerifyRegistration {
    private RegisterOTPView view;

    public PresenterVerifyRegistration(RegisterOTPView view) {
        this.view = view;
    }

    public void  verifyRegister(RegisterOTP otp){
        view.showHideProgress(true);
        Call<ResponseBody> call = AppUtils.getApi((Context)view).registerOtpVerify(otp);
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

    public void resendRegisterOtp(Phone phone){
        view.showHideProgress(true);
        Call<ResponseBody> userCall = AppUtils.getApi((Context)view).resendRegisterOtp(phone);
        userCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onResend(response.message());
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

    public interface RegisterOTPView{
        void showHideProgress(boolean isShow);
        void onError(String message);
        void onSuccess(String message);
        void onFailure(Throwable t);
        void onResend(String message);
    }
}
