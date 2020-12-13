package star.example.stargaze.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import star.example.stargaze.R;
import star.example.stargaze.databinding.ActivityVideoVerificationBinding;
import star.example.stargaze.models.response.EventPaidCheckResp;
import star.example.stargaze.utils.AppUtils;
import star.example.stargaze.view_presenter.VideoVerificationPresenter;
import com.google.android.material.snackbar.Snackbar;

public class VideoVerificationActivity extends AppCompatActivity implements VideoVerificationPresenter.VideoVerificationView, View.OnClickListener {
    private ActivityVideoVerificationBinding binding;
    private Context context;
    private View view;
    private Dialog dialog;
    private VideoVerificationPresenter presenter;
    private String eventId;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_video_verification);
        context = VideoVerificationActivity.this;
        view = binding.getRoot();
        dialog = AppUtils.hideShowProgress(context);
        position = getIntent().getIntExtra("pos",0);
        eventId = getIntent().getStringExtra("eventId");
        AppUtils.setUpToolbar(this,binding.tool.toolbar,true,false,"Watch Now");
        presenter = new VideoVerificationPresenter(this);
        binding.tvWatchNowBtn.setOnClickListener(this);
        binding.tvResendBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_watch_now_btn:
                setOtp();
                break;
            case R.id.tv_resend_btn:
                presenter.resendVideoOtp(eventId,context);
                break;
        }
    }

    @Override
    public void showHideProgress(boolean isShow) {
        if (isShow){
            dialog.show();
        }else {
            dialog.dismiss();
        }
    }

    @Override
    public void onError(String message) {
        Snackbar.make(view,message,Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onVidOtpSuccess(String message) {

    }

    @Override
    public void onVidOtpResendSuccess(String message) {

    }

    @Override
    public void onVidOtpVerifySuccess(String message) {
            if(message.equalsIgnoreCase("ok")){
                Intent intent = new Intent(context,LiveStreamActivity.class);
                intent.putExtra("pos",position);
                startActivity(intent);
                finish();
            }
    }

    @Override
    public void isEventPaid(EventPaidCheckResp paidCheckResp) {

    }

    @Override
    public void onFailure(Throwable t) {
        Snackbar.make(view,t.getMessage(),Snackbar.LENGTH_SHORT).show();

    }

    private void setOtp(){
        String code = binding.edVidOtp.getText().toString().trim();
        if(code.isEmpty()){
            binding.edVidOtp.setError("Please enter video code!");
        }else {
//            int iCode = Integer.valueOf(code);
            presenter.verifyVideoOtp(code, eventId, context);
        }
    }
}
