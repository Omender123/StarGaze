package star.example.stargaze.view_presenter;


import android.content.Context;

import star.example.stargaze.models.response.EventPaidCheckResp;
import star.example.stargaze.utils.AppUtils;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoVerificationPresenter {
    private VideoVerificationView view;

    public VideoVerificationPresenter(VideoVerificationView view) {
        this.view = view;
    }

    public void getVideoOtp(String eventId,Context context){
        view.showHideProgress(true);
        Call<ResponseBody> call = AppUtils.getApi(context).getVidOtp(eventId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onVidOtpSuccess(response.message());
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

    public void resendVideoOtp(String eventId,Context context){
        view.showHideProgress(true);
        Call<ResponseBody> call = AppUtils.getApi(context).resendVidOtp(eventId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onVidOtpResendSuccess(response.message());
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

    public void verifyVideoOtp(String code ,String eventId,Context context){
        view.showHideProgress(true);
        Call<ResponseBody> call = AppUtils.getApi(context).verifyVideo(code,eventId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onVidOtpVerifySuccess(response.message());
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

    public void paidEvent(String userId,String eventId,Context context){
        view.showHideProgress(true);
        Call<EventPaidCheckResp> call = AppUtils.getApi(context).isEventPaid(eventId, userId);
        call.enqueue(new Callback<EventPaidCheckResp>() {
            @Override
            public void onResponse(Call<EventPaidCheckResp> call, Response<EventPaidCheckResp> response) {
                view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.isEventPaid(response.body());
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
            public void onFailure(Call<EventPaidCheckResp> call, Throwable t) {
                view.showHideProgress(false);
                view.onFailure(t);
            }
        });
    }

    public interface VideoVerificationView{
        void showHideProgress(boolean isShow);
        void onError(String message);
        void onVidOtpSuccess(String message);
        void onVidOtpResendSuccess(String message);
        void onVidOtpVerifySuccess(String message);
        void isEventPaid(EventPaidCheckResp paidCheckResp);
        void onFailure(Throwable t);
    }
}
