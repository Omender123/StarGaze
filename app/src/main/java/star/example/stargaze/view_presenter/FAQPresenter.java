package star.example.stargaze.view_presenter;


import android.content.Context;

import star.example.stargaze.models.response.FAQResp;
import star.example.stargaze.utils.AppUtils;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FAQPresenter {
    private FAQView view;

    public FAQPresenter(FAQView view) {
        this.view = view;
    }

    public void getFaq(Context context){
        view.showHideProgress(true);
        Call<List<FAQResp>> call = AppUtils.getApi(context).getFAQ();
        call.enqueue(new Callback<List<FAQResp>>() {
            @Override
            public void onResponse(Call<List<FAQResp>> call, Response<List<FAQResp>> response) {
                view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onSuccess(response.body());
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
            public void onFailure(Call<List<FAQResp>>call, Throwable t) {
                view.showHideProgress(false);
                view.onFailure(t);
            }
        });
    }

    public interface FAQView{
        void showHideProgress(boolean isShow);
        void onError(String message);
        void onSuccess(List<FAQResp> eventResp);

        void onFailure(Throwable t);
    }
}
