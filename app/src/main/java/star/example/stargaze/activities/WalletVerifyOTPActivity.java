package star.example.stargaze.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import star.example.stargaze.R;
import star.example.stargaze.databinding.ActivityWalletVerifyOtpBinding;
import star.example.stargaze.models.response.CapturePaymentSuccessResp;
import star.example.stargaze.models.response.RazorOrderResp;
import star.example.stargaze.models.response.WalletVerifyResp;
import star.example.stargaze.utils.AppUtils;
import star.example.stargaze.view_presenter.ApplyWalletPresenter;
import com.google.android.material.snackbar.Snackbar;

import okhttp3.ResponseBody;

public class WalletVerifyOTPActivity extends AppCompatActivity implements ApplyWalletPresenter.ApplyWalletView, View.OnClickListener {
    private ActivityWalletVerifyOtpBinding binding;
    private Context context;
    private Dialog dialog;
    private View view;
    private String orderId;
    private ApplyWalletPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_wallet_verify_otp);
        context = WalletVerifyOTPActivity.this;
        orderId = getIntent().getStringExtra("orderId");
        presenter = new ApplyWalletPresenter(this);
        dialog = AppUtils.hideShowProgress(context);
        view = binding.getRoot();
        binding.tvContinueBtn.setOnClickListener(this);
        binding.tvResendBtn.setOnClickListener(this);
    }

    @Override
    public void showHideProgress(boolean isShow) {
        if(isShow){
            dialog.show();
        }else {
            dialog.dismiss();
        }
    }

    @Override
    public void onError(String message) {
        if(message.equalsIgnoreCase("Token Expired")){
            AppUtils.alertMessage(this,"Your account is logged in to new device or your session is expired!");
        }else {
            Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void OnResendOtp(ResponseBody responseBody) {
        Snackbar.make(view,"OTP Sent To YOUR Mobile Number!",Snackbar.LENGTH_SHORT).show();

    }

    @Override
    public void OnVerifyOtp(WalletVerifyResp walletVerifyResp) {
//        Snackbar.make(view,"Payment Success",Snackbar.LENGTH_SHORT).show();
        AppUtils.showMessageOKCancel("Payment Success\n"+"Event :"+walletVerifyResp.getEvent()+"\nOrder No :"+walletVerifyResp.getOrderNumber(),this, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(context,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public void onRazorOrderSuccess(RazorOrderResp razorOrderResp) {

    }

    @Override
    public void onRazorCaptureSuccess(CapturePaymentSuccessResp successResp) {

    }

    @Override
    public void onRazorDataSaved(ResponseBody responseBody) {

    }

    @Override
    public void onFailure(Throwable t) {
        Snackbar.make(view,t.getMessage(),Snackbar.LENGTH_SHORT).show();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_resend_btn:
                presenter.resendWalletOtp(orderId,true,context);
                break;
            case R.id.tv_continue_btn:
                setData();
                break;
        }

    }

    private void setData(){
        String otp = binding.edWalletOtp.getText().toString().trim();
        if(otp.isEmpty()){
            binding.edWalletOtp.setError("Enter OTP!");
        }else {
            presenter.WalletVerifyOtp(orderId,otp,context);
        }
    }
}
