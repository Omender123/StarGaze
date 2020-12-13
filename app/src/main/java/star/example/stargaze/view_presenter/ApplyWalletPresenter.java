package star.example.stargaze.view_presenter;


import android.content.Context;

import star.example.stargaze.models.response.CapturePaymentSuccessResp;
import star.example.stargaze.models.response.RazorOrderResp;
import star.example.stargaze.models.response.WalletVerifyResp;
import star.example.stargaze.utils.AppUtils;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApplyWalletPresenter {
    private ApplyWalletView view;

    public ApplyWalletPresenter(ApplyWalletView view) {
        this.view = view;
    }

    public void resendWalletOtp(String orderId,boolean isWalletSelected,Context context){
        view.showHideProgress(true);
        Call<ResponseBody> call = AppUtils.getApi(context).walletSelectedResendOtp(orderId,isWalletSelected);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.OnResendOtp(response.body());
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

    public void WalletVerifyOtp(String orderId,String otp,Context context){
        view.showHideProgress(true);
        Call<WalletVerifyResp> call = AppUtils.getApi(context).isWalletSelectedVerifyOtp(orderId,otp);
        call.enqueue(new Callback<WalletVerifyResp>() {
            @Override
            public void onResponse(Call<WalletVerifyResp> call, Response<WalletVerifyResp> response) {
                view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.OnVerifyOtp(response.body());
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
            public void onFailure(Call<WalletVerifyResp> call, Throwable t) {
                view.showHideProgress(false);
                view.onFailure(t);
            }
        });
    }

    public interface ApplyWalletView{
        void showHideProgress(boolean isShow);
        void onError(String message);
        void OnResendOtp(ResponseBody responseBody);
        void OnVerifyOtp(WalletVerifyResp walletVerifyResp);
        void onRazorOrderSuccess(RazorOrderResp razorOrderResp);
        void onRazorCaptureSuccess(CapturePaymentSuccessResp successResp);
        void onRazorDataSaved(ResponseBody responseBody);
        void onFailure(Throwable t);
    }
}
