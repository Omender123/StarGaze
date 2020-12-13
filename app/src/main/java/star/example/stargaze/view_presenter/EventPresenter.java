package star.example.stargaze.view_presenter;


import android.content.Context;

import star.example.stargaze.models.request.EventFilterBody;
import star.example.stargaze.models.response.Banner;
import star.example.stargaze.models.response.EventResp;
import star.example.stargaze.utils.AppUtils;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventPresenter {
    private EventView view;

    public EventPresenter(EventView view) {
        this.view = view;
    }

    public void getEvents(int page, int limit, EventFilterBody filterBody,Context context){
        view.showHideProgress(true);
        Call<EventResp> call = AppUtils.getApi(context).getEvent(page,limit,filterBody);
        call.enqueue(new Callback<EventResp>() {
            @Override
            public void onResponse(Call<EventResp> call, Response<EventResp> response) {
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
            public void onFailure(Call<EventResp> call, Throwable t) {
                view.showHideProgress(false);
                view.onFailure(t);
            }
        });
    }
    public  void getBanner(Context context){
        view.showHideProgress(true);
        Call<List<Banner>> call = AppUtils.getApi(context).getBanners();
        call.enqueue(new Callback<List<Banner>>() {
            @Override
            public void onResponse(Call<List<Banner>>call, Response<List<Banner>> response) {
                view.showHideProgress(false);
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    view.onBannerSuccess(response.body());
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
            public void onFailure(Call<List<Banner>> call, Throwable t) {

            }
        });
    }
    public interface EventView{
        void showHideProgress(boolean isShow);
        void onError(String message);
        void onSuccess(EventResp eventResp);
        void onBannerSuccess(List<Banner>banners);
        void onFailure(Throwable t);
    }
}
