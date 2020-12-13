package star.example.stargaze.view_presenter;


import android.content.Context;

import star.example.stargaze.models.response.CommentResp;
import star.example.stargaze.utils.AppUtils;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LiveCommentPresenter {
    private LiveCommentView view;

    public LiveCommentPresenter(LiveCommentView view) {
        this.view = view;
    }

    public void getLiveComments(String eventId,Context context){
        view.showHideProgress(true);
        Call<CommentResp> call = AppUtils.getApi(context).getLiveComment(eventId);
        call.enqueue(new Callback<CommentResp>() {
            @Override
            public void onResponse(Call<CommentResp> call, Response<CommentResp> response) {
                view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onCommentGet(response.body());
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
            public void onFailure(Call<CommentResp> call, Throwable t) {
                view.showHideProgress(false);
                view.onFailure(t);
            }
        });
    }

//    public void postComments(String eventId, LiveUserCommentBody commentBody, Context context){
//        view.showHideProgress(true);
//        Call<ResponseBody> call = AppUtils.getApi(context).postLiveComment(eventId,commentBody);
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                view.showHideProgress(false);
//                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
//                    view.onCommentPost(response.message());
//                } else {
//                    try {
//                        String errorRes = response.errorBody().string();
//                        JSONObject jsonObject =new JSONObject(errorRes);
//                        String err_msg  = jsonObject.getString("error");
//                        int status= jsonObject.getInt("status");
//                        view.onError(err_msg);
//                    }
//                    catch (Exception ex){
//                        ex.printStackTrace();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                view.showHideProgress(false);
//                view.onFailure(t);
//            }
//        });
//    }

    public interface LiveCommentView{
        void showHideProgress(boolean isShow);
        void onError(String message);
        void onCommentGet(CommentResp resp);
//        void onCommentPost(String message);
        void onFailure(Throwable t);
    }
}
