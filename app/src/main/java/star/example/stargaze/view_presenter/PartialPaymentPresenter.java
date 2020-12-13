package star.example.stargaze.view_presenter;


import android.content.Context;

import star.example.stargaze.models.PartialPayResp;
import star.example.stargaze.models.request.RazorCaptureBody;
import star.example.stargaze.models.request.RazorOrderBody;
import star.example.stargaze.models.request.RazorSaveDataBody;
import star.example.stargaze.models.response.CapturePaymentSuccessResp;
import star.example.stargaze.models.response.PaymentDataSaveResp;
import star.example.stargaze.models.response.RazorOrderResp;
import star.example.stargaze.models.response.WalletResp;
import star.example.stargaze.utils.AppUtils;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PartialPaymentPresenter {
    private PartialPaymentView view;

    public PartialPaymentPresenter(PartialPaymentView view) {
        this.view = view;
    }

    public void partialPay(String orderId,Context context){
        view.showHideProgress(true);
        Call<PartialPayResp> call = AppUtils.getApi(context).partialPayment(orderId);
        call.enqueue(new Callback<PartialPayResp>() {
            @Override
            public void onResponse(Call<PartialPayResp> call, Response<PartialPayResp> response) {
                view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onPartialPaymentSuccess(response.body());
                } else {
                    try {
                        String errorRes = response.errorBody().string();
                        JSONObject jsonObject =new JSONObject(errorRes);
                        String err_msg  = jsonObject.getString("error");
                        int status= jsonObject.getInt("status");
                    }
                    catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<PartialPayResp> call, Throwable t) {
                view.showHideProgress(false);
                view.onFailure(t);

            }
        });
    }

    public void razorOrder(RazorOrderBody razorOrderBody, Context context){
        view.showHideProgress(true);
        Call<RazorOrderResp> call = AppUtils.getApi(context).createRazorOrder(razorOrderBody);
        call.enqueue(new Callback<RazorOrderResp>() {
            @Override
            public void onResponse(Call<RazorOrderResp> call, Response<RazorOrderResp> response) {
                view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onRazorOrderSuccess(response.body());
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
            public void onFailure(Call<RazorOrderResp> call, Throwable t) {
                view.showHideProgress(false);
                view.onFailure(t);
            }
        });
    }

    public void razorCapture(RazorCaptureBody razorCaptureBody, Context context){
        view.showHideProgress(true);
        Call<CapturePaymentSuccessResp>call = AppUtils.getApi(context).razorCapturePayment(razorCaptureBody);
        call.enqueue(new Callback<CapturePaymentSuccessResp>() {
            @Override
            public void onResponse(Call<CapturePaymentSuccessResp> call, Response<CapturePaymentSuccessResp> response) {
                view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onRazorCaptureSuccess(response.body());
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

    public void savePaymentData(String orderId, RazorSaveDataBody razorSaveDataBody, Context context){
        view.showHideProgress(true);
        Call<PaymentDataSaveResp>call = AppUtils.getApi(context).razorDataSave(orderId,razorSaveDataBody);
        call.enqueue(new Callback<PaymentDataSaveResp>() {
            @Override
            public void onResponse(Call<PaymentDataSaveResp> call, Response<PaymentDataSaveResp> response) {
                view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onRazorDataSaved(response.body());
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
            public void onFailure(Call<PaymentDataSaveResp> call, Throwable t) {
                view.showHideProgress(false);
                view.onFailure(t);
            }
        });
    }

    public void getWallet(Context context){
        view.showHideProgress(true);
        Call<WalletResp> call = AppUtils.getApi(context).getWallet();
        call.enqueue(new Callback<WalletResp>() {
            @Override
            public void onResponse(Call<WalletResp> call, Response<WalletResp> response) {
                view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onGetWalletBalance(response.body());
                } else {
                    try {
                        String errorRes = response.errorBody().string();
                        JSONObject jsonObject =new JSONObject(errorRes);
                        String err_msg  = jsonObject.getString("error");
                        int status= jsonObject.getInt("status");
                    }
                    catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<WalletResp> call, Throwable t) {
                view.showHideProgress(false);
                view.onFailure(t);

            }
        });
    }
    public interface PartialPaymentView{
        void showHideProgress(boolean isShow);
        void onError(String message);
        void onPartialPaymentSuccess(PartialPayResp payResp);
        void onRazorOrderSuccess(RazorOrderResp razorOrderResp);
        void onRazorCaptureSuccess(CapturePaymentSuccessResp successResp);
        void onRazorDataSaved(PaymentDataSaveResp responseBody);
        void onGetWalletBalance(WalletResp walletResp);
        void onFailure(Throwable t);
    }
}
