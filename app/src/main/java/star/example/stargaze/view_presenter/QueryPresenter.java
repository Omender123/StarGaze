package star.example.stargaze.view_presenter;


import android.content.Context;

import star.example.stargaze.models.request.ContactBody;
import star.example.stargaze.utils.AppUtils;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QueryPresenter {
    private QueryView view;

    public QueryPresenter(QueryView view) {
        this.view = view;
    }

    public void contactUs(ContactBody contactBody, Context context){

        view.showHideProgress(true);
        Call<ResponseBody> call = AppUtils.getApi(context).cantactUs(contactBody);
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

    public interface QueryView{
        void showHideProgress(boolean isShow);
        void onError(String message);
        void onSuccess(String success);
        void onFailure(Throwable t);
    }
}
