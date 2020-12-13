package star.example.stargaze.authentication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import star.example.stargaze.R;
import star.example.stargaze.databinding.ActivityForgotBinding;
import star.example.stargaze.models.request.Phone;
import star.example.stargaze.utils.AppUtils;
import star.example.stargaze.utils.Constants;
import star.example.stargaze.utils.Validation;
import com.google.android.material.snackbar.Snackbar;

public class ForgotActivity extends AppCompatActivity implements ForgotPresenter.ForgotView, View.OnClickListener {
    private ActivityForgotBinding binding;
    private Dialog dialog;
    private View view;
    private Context context;
    private ForgotPresenter presenter;
    private String phone_no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_forgot);
        context = ForgotActivity.this;
        view = binding.getRoot();
        dialog = AppUtils.hideShowProgress(context);
        presenter = new ForgotPresenter(this);

        binding.tvSendOtpBtn.setOnClickListener(this);

    }

    @Override
    public void showHideProgress(boolean isShow) {
        if(isShow){
            dialog.show();
        }else{
            dialog.dismiss();
        }
    }

    @Override
    public void onError(String message) {
        Snackbar.make(view,message,Snackbar.LENGTH_SHORT).show();

    }

    @Override
    public void onSuccess(String message) {
        Snackbar.make(view,"OTP Sent ",Snackbar.LENGTH_SHORT).show();
        if(message.equalsIgnoreCase("ok")){
            Intent intent = new Intent(this, VerifyOTPActivity.class);
            Constants.otpType =2;
            intent.putExtra("phone",phone_no);
            intent.putExtra(Constants.OTPTYPE,Constants.otpType);
            startActivity(intent);
        }
    }

    @Override
    public void onFailure(Throwable t) {
        Snackbar.make(view,t.getMessage(),Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
            if (view.getId()==R.id.tv_send_otp_btn){
                otpSend();
            }
    }

    private void otpSend(){
        phone_no = binding.edPhoneNo.getText().toString().trim();
        if(phone_no.isEmpty()){
            binding.edPhoneNo.setError("Please enter mobile no!");
        }else if(!Validation.isValidPhoneNumber(phone_no)){
            binding.edPhoneNo.setError("Invalid Email Id!");
        }else {
            Phone phone = new Phone(phone_no);
            presenter.sendOtp(phone);
        }
    }
}
