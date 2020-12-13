package star.example.stargaze.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import star.example.stargaze.R;
import star.example.stargaze.activities.AddToWalletActivity;
import star.example.stargaze.activities.MainActivity;

import star.example.stargaze.adapters.WalletHistoryAdapter;
import star.example.stargaze.databinding.FragmentWalletBinding;

import star.example.stargaze.models.response.WalletResp;
import star.example.stargaze.utils.AppUtils;
import star.example.stargaze.utils.Constants;
import star.example.stargaze.view_presenter.WalletPresenter;
import com.google.android.material.snackbar.Snackbar;
import com.razorpay.PaymentData;

import org.json.JSONObject;

import java.text.DecimalFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentWallet extends Fragment implements View.OnClickListener {

    private Context context;
    private FragmentWalletBinding binding;
    private Dialog dialog;
    private View view;
    private WalletPresenter presenter;
    private PaymentData paymentData1;
    private int payableAmount;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_wallet,container,false);
        view = binding.getRoot();
        dialog = AppUtils.hideShowProgress(context);

    /*    binding.tvAddMoney.setOnClickListener(this);
*/
        getWallet(context);
        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity){
            this.context = context;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            /*
            case R.id.tv_add_money:
                Intent intent = new Intent(context, AddToWalletActivity.class);
                startActivity(intent);
//                createRazorWallet();
                break;*/
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        getWallet(context);
    }

    public void getWallet(Context context){
        dialog.show();
        DecimalFormat precision = new DecimalFormat("0.00");
        Call<WalletResp> call = AppUtils.getApi(context).getWallet();
        call.enqueue(new Callback<WalletResp>() {
            @Override
            public void onResponse(Call<WalletResp> call, Response<WalletResp> response) {
              dialog.dismiss();
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    double balance = response.body().getClosingBalance();
                    binding.tvWalletAmount.setText(Constants.RUPEE+""+precision.format(balance));
                    binding.walletHistoryRecycler.setLayoutManager(new GridLayoutManager(context,1));
                    binding.walletHistoryRecycler.setAdapter(new WalletHistoryAdapter(response.body().getTransactions(),context));

                } else {
                    try {
                        String errorRes = response.errorBody().string();
                        JSONObject jsonObject =new JSONObject(errorRes);
                        String err_msg  = jsonObject.getString("error");
                        int status= jsonObject.getInt("status");
                        if(err_msg.equalsIgnoreCase("Token Expired")){
                            AppUtils.alertMessage((AppCompatActivity)context,"Your account is logged in to new device or your session is expired!");
                        }else {
                            Snackbar.make(view, err_msg, Snackbar.LENGTH_SHORT).show();
                        }

                    }
                    catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<WalletResp> call, Throwable t) {
               dialog.dismiss();
                Snackbar.make(view,t.getMessage(),Snackbar.LENGTH_SHORT).show();

            }
        });
    }
}
