package star.example.stargaze.view_presenter;

import android.content.Context;

import star.example.stargaze.models.response.OrderHistoryResp;
import star.example.stargaze.utils.AppUtils;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderHistoryPresenter {

    private OrderHistoryView view;

    public OrderHistoryPresenter(OrderHistoryView view) {
        this.view = view;
    }

    public void getOrders(Context context){
        view.showHideProgress(true);
        Call<List<OrderHistoryResp>> call = AppUtils.getApi(context).getAllOrders();
        call.enqueue(new Callback<List<OrderHistoryResp>> () {
            @Override
            public void onResponse(Call<List<OrderHistoryResp>>  call, Response<List<OrderHistoryResp>>  response) {
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
            public void onFailure(Call<List<OrderHistoryResp>>  call, Throwable t) {
                view.showHideProgress(false);
                view.onFailure(t);
            }
        });
    }

   public interface OrderHistoryView{
        void showHideProgress(boolean isShow);
        void onError(String message);
        void onSuccess(List<OrderHistoryResp> historyResp);
        void onFailure(Throwable t);
    }
}
