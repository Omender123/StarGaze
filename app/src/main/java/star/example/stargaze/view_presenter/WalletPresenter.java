package star.example.stargaze.view_presenter;


import android.content.Context;

import star.example.stargaze.models.request.RazorCaptureBody;
import star.example.stargaze.models.request.WalletAmountBody;
import star.example.stargaze.models.request.WalletSaveBody;
import star.example.stargaze.models.response.AddWalletDataResp;
import star.example.stargaze.models.response.CapturePaymentSuccessResp;
import star.example.stargaze.models.response.WalletCreateResp;
import star.example.stargaze.utils.AppUtils;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WalletPresenter {
    private WalletView view;

    public WalletPresenter(WalletView view) {
        this.view = view;
    }

    public void walletRazorOrder(WalletAmountBody amountBody, Context context){
        view.showHideProgress(true);
        Call<WalletCreateResp> call = AppUtils.getApi(context).createWalletOrder(amountBody);
        call.enqueue(new Callback<WalletCreateResp>() {
            @Override
            public void onResponse(Call<WalletCreateResp> call, Response<WalletCreateResp> response) {
                view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onRazorWalletOrderSuccess(response.body());
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
            public void onFailure(Call<WalletCreateResp> call, Throwable t) {
                view.showHideProgress(false);
                view.onFailure(t);
            }
        });
    }

    public void razorCapture(RazorCaptureBody razorCaptureBody,Context context){
        view.showHideProgress(true);
        Call<CapturePaymentSuccessResp>call = AppUtils.getApi(context).razorCapturePayment(razorCaptureBody);
        call.enqueue(new Callback<CapturePaymentSuccessResp>() {
            @Override
            public void onResponse(Call<CapturePaymentSuccessResp> call, Response<CapturePaymentSuccessResp> response) {
                view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onRazorPaymentCaptureSuccess(response.body());
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
            public void onFailure(Call<CapturePaymentSuccessResp> call, Throwable t) {
                view.showHideProgress(false);
                view.onFailure(t);
            }
        });
    }

    public void savePaymentData(WalletSaveBody razorSaveDataBody, Context context){
        view.showHideProgress(true);
        Call<AddWalletDataResp>call = AppUtils.getApi(context).saveWalletData(razorSaveDataBody);
        call.enqueue(new Callback<AddWalletDataResp>() {
            @Override
            public void onResponse(Call<AddWalletDataResp>call, Response<AddWalletDataResp> response) {
                view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onDataSaved(response.body());
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
            public void onFailure(Call<AddWalletDataResp> call, Throwable t) {
                view.showHideProgress(false);
                view.onFailure(t);
            }
        });
    }


    public interface WalletView{
        void showHideProgress(boolean isShow);
        void onError(String message);
        void onRazorWalletOrderSuccess(WalletCreateResp resp);
        void onRazorPaymentCaptureSuccess(CapturePaymentSuccessResp successResp);
        void onDataSaved(AddWalletDataResp savedData);
        void onFailure(Throwable t);
    }
}
