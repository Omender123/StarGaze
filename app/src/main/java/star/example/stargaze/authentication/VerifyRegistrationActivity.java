package star.example.stargaze.authentication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import star.example.stargaze.R;
import star.example.stargaze.databinding.ActivityVerifyRegistrationBinding;
import star.example.stargaze.models.request.Phone;
import star.example.stargaze.models.request.RegisterOTP;
import star.example.stargaze.utils.AppUtils;
import star.example.stargaze.utils.Constants;
import com.google.android.material.snackbar.Snackbar;

public class VerifyRegistrationActivity extends AppCompatActivity implements PresenterVerifyRegistration.RegisterOTPView, View.OnClickListener {

        private ActivityVerifyRegistrationBinding binding;
        private PresenterVerifyRegistration presenter;
        private View view;
        private String phone="";
        private int otpType;
        private Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_verify_registration);
        view = binding.getRoot();
        presenter = new PresenterVerifyRegistration(this);
        dialog = AppUtils.hideShowProgress(this);
        otpType = getIntent().getIntExtra("otpType",0);

        binding.backArrow.setOnClickListener(this);
        binding.tvVerifyOtpBtn.setOnClickListener(this);
        binding.tvResendBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_verify_otp_btn:
                setOtp();
                break;
            case R.id.back_arrow:
                onBackPressed();
                break;
            case R.id.tv_resend_btn:
                resend();
                break;
        }
    }

    @Override
    public void showHideProgress(boolean isShow) {
        if (isShow){
            dialog.show();
//            binding.otpProgress.setVisibility(View.VISIBLE);
        }else {
            dialog.dismiss();
//            binding.otpProgress.setVisibility(View.GONE);
        }
    }

    @Override
    public void onError(String message) {
        Snackbar.make(view,message,Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess(String message) {
        Snackbar.make(view,message,Snackbar.LENGTH_SHORT).show();
        Intent intent = new Intent(this,SuccessActivity.class);
        intent.putExtra(Constants.msg,getResources().getString(R.string.phone_verified));
        intent.putExtra(Constants.OTPTYPE,otpType);
        startActivity(intent);
    }

    @Override
    public void onFailure(Throwable t) {
        Snackbar.make(view,t.getMessage(),Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onResend(String message) {

    }

    private void  setOtp(){
        String otp = binding.edOtpCode.getText().toString().trim();
        phone = getIntent().getStringExtra("phone");
        if(otp.isEmpty()){
            binding.edOtpCode.setError("enter otp!");
        }else  if(phone.isEmpty()){
            Snackbar.make(view,"empty phone number",Snackbar.LENGTH_SHORT).show();
        }else {
            RegisterOTP registerOTP = new RegisterOTP(phone,otp);
            presenter.verifyRegister(registerOTP);
        }


    }

    private void resend(){
        String phone_no="";
        phone_no = getIntent().getStringExtra("phone");
        if(phone_no.isEmpty()){
            Snackbar.make(view,"email id is empty",Snackbar.LENGTH_SHORT).show();
        }else {
            Phone phone = new Phone(phone_no);
            presenter.resendRegisterOtp(phone);
        }

    }
}
