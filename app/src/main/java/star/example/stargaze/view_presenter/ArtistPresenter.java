package star.example.stargaze.view_presenter;


import android.content.Context;

import star.example.stargaze.models.request.EventFilterBody;
import star.example.stargaze.models.response.CelebrityResp;
import star.example.stargaze.utils.AppUtils;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArtistPresenter {
    private ArtistView view;

    public ArtistPresenter(ArtistView view) {
        this.view = view;
    }

    public void getArtist(int page, int limit, EventFilterBody filterBody,Context context){
        view.showHideProgress(true);
        Call<CelebrityResp> call = AppUtils.getApi(context).getCelebrity(page,limit,filterBody);
        call.enqueue(new Callback<CelebrityResp>() {
            @Override
            public void onResponse(Call<CelebrityResp> call, Response<CelebrityResp> response) {
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
            public void onFailure(Call<CelebrityResp> call, Throwable t) {
                view.showHideProgress(false);
                view.onFailure(t);
            }
        });
    }

    public interface ArtistView{
        void showHideProgress(boolean isShow);
        void onError(String message);
        void onSuccess(CelebrityResp eventResp);

        void onFailure(Throwable t);
    }
}
