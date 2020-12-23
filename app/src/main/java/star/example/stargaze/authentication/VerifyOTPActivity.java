package star.example.stargaze.authentication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import star.example.stargaze.R;
import star.example.stargaze.databinding.ActivityVerifyOtpBinding;
import star.example.stargaze.models.request.Otp;
import star.example.stargaze.models.request.Phone;
import star.example.stargaze.utils.AppUtils;
import com.google.android.material.snackbar.Snackbar;

public class VerifyOTPActivity extends AppCompatActivity implements VerifyOtpPresenter.OTPView, View.OnClickListener {
    private ActivityVerifyOtpBinding binding;
    private Dialog dialog;
    private VerifyOtpPresenter presenter;
    private View mView;
    private int otpType;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_verify_otp);
        dialog = AppUtils.hideShowProgress(this);
        context = VerifyOTPActivity.this;
        presenter = new VerifyOtpPresenter(this);
        otpType = getIntent().getIntExtra("otpType",0);
        mView=binding.getRoot();
        binding.backArrow.setOnClickListener(this);
        binding.tvVerifyOtpBtn.setOnClickListener(this);
        binding.tvResendBtn.setOnClickListener(this);
    }

    @Override
    public void showHideProgress(boolean isShow) {
        if(isShow){
            binding.otpProgress.setVisibility(View.VISIBLE);
        }else {
            binding.otpProgress.setVisibility(View.GONE);
        }
    }

    @Override
    public void onError(String message) {
        Snackbar.make(mView,message,Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess(boolean isTrue, String message) {
        Snackbar.make(mView, message, Snackbar.LENGTH_SHORT).show();

        Intent intent = new Intent(this, ResetPasswordActivity.class);
        String phone_no = getIntent().getStringExtra("phone");
            intent.putExtra("phones",phone_no);
        startActivity(intent);
    }

    @Override
    public void onFailure(Throwable t) {
        Snackbar.make(mView,t.getMessage(),Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onResend(String message) {
        Snackbar.make(mView,"OTP Sent",Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_verify_otp_btn:
                verify();
                break;
            case R.id.back_arrow:
                onBackPressed();
                break;
            case R.id.tv_resend_btn:
                resend();
                break;
        }
    }

    private void verify(){
        String otp_code = binding.edOtpCode.getText().toString().trim();
        if(otp_code.isEmpty()){
            binding.edOtpCode.setError("Enter OTP!");
        }else {
            Otp otp = new Otp(otp_code);
            presenter.sendOtp(otp);
        }
    }

    private void resend(){
        String phone_no = getIntent().getStringExtra("phone");
        Phone phone = new Phone(phone_no);
        presenter.resendOtp(phone);

    }
}
