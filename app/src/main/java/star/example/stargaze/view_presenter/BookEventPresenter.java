package star.example.stargaze.view_presenter;


import android.content.Context;

import star.example.stargaze.models.request.EventOrderBody;
import star.example.stargaze.models.request.RazorCaptureBody;
import star.example.stargaze.models.request.RazorOrderBody;
import star.example.stargaze.models.request.RazorSaveDataBody;
import star.example.stargaze.models.response.ApplyCouponResp;
import star.example.stargaze.models.response.OrderResp;
import star.example.stargaze.models.response.CapturePaymentSuccessResp;
import star.example.stargaze.models.response.PaymentDataSaveResp;
import star.example.stargaze.models.response.PrivacyResp;
import star.example.stargaze.models.response.RazorOrderResp;
import star.example.stargaze.models.response.WalletResp;
import star.example.stargaze.utils.AppUtils;

import org.json.JSONObject;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookEventPresenter {
    private BookEventView view;

    public BookEventPresenter(BookEventView view) {
        this.view = view;
    }

    public void orderEvent(EventOrderBody eventOrderBody, Context context){
        view.showHideProgress(true);
        Call<OrderResp> call = AppUtils.getApi(context).bookEvent(eventOrderBody);
        call.enqueue(new Callback<OrderResp>() {
            @Override
            public void onResponse(Call<OrderResp> call, Response<OrderResp> response) {
                view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onEventOrderSuccess(response.body());
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
            public void onFailure(Call<OrderResp> call, Throwable t) {
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

    public void razorCapture(RazorCaptureBody razorCaptureBody,Context context){
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

    public void savePaymentData(String orderId,RazorSaveDataBody razorSaveDataBody,Context context){
        view.showHideProgress(true);
        Call<PaymentDataSaveResp>call = AppUtils.getApi(context).razorDataSave(orderId,razorSaveDataBody);
        call.enqueue(new Callback<PaymentDataSaveResp>() {
            @Override
            public void onResponse(Call<PaymentDataSaveResp> call, Response<PaymentDataSaveResp> response) {
                view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    System.out.println("ssss "+response.body());
                    view.onRazorDataSaved(response.body());
                } else {
                    try {
                        String errorRes = response.errorBody().string();
                        System.out.println("ssss er "+errorRes);
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

    public void sendWalletOtp(String orderId,boolean isWalletSelected,Context context){
        view.showHideProgress(true);
        Call<ResponseBody> call = AppUtils.getApi(context).walletSelectedResendOtp(orderId,isWalletSelected);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onWalletOtpSuccess(response.body());
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

    public void applyDiscountCode(String orderId,String coupan,String userId,Context context){
        view.showHideProgress(true);
        Call<ApplyCouponResp> call = AppUtils.getApi(context).applyDiscount(orderId,coupan,userId);
        call.enqueue(new Callback<ApplyCouponResp>() {
            @Override
            public void onResponse(Call<ApplyCouponResp> call, Response<ApplyCouponResp> response) {
                view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onCouponApplied(response.body());
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
            public void onFailure(Call<ApplyCouponResp> call, Throwable t) {
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

    public void getAboutUs(Context context){
        view.showHideProgress(true);
        Call<List<PrivacyResp>> call = AppUtils.getApi(context).getAboutUs();
        call.enqueue(new Callback<List<PrivacyResp>>() {
            @Override
            public void onResponse(Call<List<PrivacyResp>> call, Response<List<PrivacyResp>> response) {
                view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onPrivacyResult(response.body());
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
            public void onFailure(Call<List<PrivacyResp>> call, Throwable t) {
                view.showHideProgress(false);
                view.onFailure(t);

            }
        });
    }

    public interface BookEventView{
        void showHideProgress(boolean isShow);
        void onError(String message);
        void onEventOrderSuccess(OrderResp resp);
        void onRazorOrderSuccess(RazorOrderResp razorOrderResp);
        void onRazorCaptureSuccess(CapturePaymentSuccessResp successResp);
        void onRazorDataSaved(PaymentDataSaveResp responseBody);
        void onWalletOtpSuccess(ResponseBody responseBody);
        void onCouponApplied(ApplyCouponResp applyCouponResp);
        void onGetWalletBalance(WalletResp walletResp);
        void onPrivacyResult(List<PrivacyResp> privacyRespList );
        void onFailure(Throwable t);
    }
}
