package star.example.stargaze.authentication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.view.WindowManager;

import star.example.stargaze.R;
import star.example.stargaze.activities.MainActivity;
import star.example.stargaze.databinding.ActivityLoginBinding;
import star.example.stargaze.models.request.UserCredential;
import star.example.stargaze.models.response.LoginRespBean;
import star.example.stargaze.sharedPref.MyPreferences;
import star.example.stargaze.sharedPref.PrefConf;
import star.example.stargaze.utils.AppUtils;
import star.example.stargaze.utils.Constants;
import star.example.stargaze.utils.Validation;
import com.google.android.material.snackbar.Snackbar;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, LoginPresenter.LoginView {
    private ActivityLoginBinding binding;
    private Context context;
    private Dialog dialog;
    private View view;
    private LoginPresenter presenter;
    private String user_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_login);
        context = LoginActivity.this;
        setTwoTextColor();
        dialog = AppUtils.hideShowProgress(context);
        view = binding.getRoot();
        presenter = new LoginPresenter(this);
        binding.imgLogin.setOnClickListener(this);
        binding.gotoSignUp.setOnClickListener(this);
        binding.tvForgotBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()){
            case R.id.img_login:
                doLogin();
                break;
            case R.id.tv_forgot_btn:
                intent = new Intent(context,ForgotActivity.class);
                break;
            case R.id.goto_sign_up:
                intent = new Intent(context,ReferralCodeActivity.class);
                break;
        }
        if(intent!=null){
            startActivity(intent);
        }
    }
private void setTwoTextColor(){
    Spanned text = Html.fromHtml("Not a User?"+"<b> "+"<font color=#D9163D>"+
            "SignUp" +"</b></font> ");
    binding.gotoSignUp.setText(text);
}
    @Override
    public void showHideLoginProgress(boolean isShow) {
        if(isShow){
            dialog.show();
        }else {
            dialog.dismiss();
        }
    }

    @Override
    public void onLoginError(String message) {
        Snackbar.make(view,message,Snackbar.LENGTH_SHORT).show();

        System.out.println("eerrr "+message);
        if(message.equalsIgnoreCase("Please verify your phone no before proceeding")){
            Intent intent = new Intent(this, VerifyRegistrationActivity.class);
            Constants.otpType = 1;
            intent.putExtra(Constants.OTPTYPE,Constants.otpType);
            intent.putExtra("phone",user_name);
            startActivity(intent);
        }
    }

    @Override
    public void onLoginSuccess(LoginRespBean response, String message) {
        Snackbar.make(view,message,Snackbar.LENGTH_SHORT).show();
        MyPreferences.getInstance(context).putBoolean(PrefConf.KEY_IS_LOGGED_IN,true);
        MyPreferences.getInstance(context).putString(PrefConf.KEY_USER_ID,response.getResult().getId());
        MyPreferences.getInstance(context).putString(PrefConf.KEY_LOGIN_TOKEN,response.getResult().getJwtToken());
        System.out.println("user id "+response.getResult().getId());
        MyPreferences.getInstance(context).putString(PrefConf.KEY_USER_NAME,response.getResult().getName());
//        MyPreferences.getInstance(context).putString(PrefConf.KEY_MY_REFERRAL_CODE,response.getResult().getReferalCode());
        MyPreferences.getInstance(context).putString(PrefConf.KEY_PARENT_REFERRAL_CODE,response.getResult().getReferalCode());
        MyPreferences.getInstance(context).putString(PrefConf.PROFILEPIC,response.getResult().getProfileImage());

        MyPreferences.getInstance(context).putString(PrefConf.KEY_USER_PHONE,response.getResult().getPhone());
        MyPreferences.getInstance(context).putString(PrefConf.KEY_USER_EMAIL,response.getResult().getEmail());

//        Snackbar.make(mView,mesage,Snackbar.LENGTH_SHORT).show();
        if (message.equalsIgnoreCase("ok")){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onLoginFailure(Throwable t) {
        System.out.println("eerrr "+t.getMessage());
        Snackbar.make(view,t.getMessage(),Snackbar.LENGTH_SHORT).show();
    }

    private  void doLogin(){
        user_name = binding.edUserName.getText().toString().trim();
        String pass = binding.edPass.getText().toString().trim();
        String fcmToken = MyPreferences.getInstance(context).getString(PrefConf.DEVICE_TOKEN,"");

        if(user_name.isEmpty()){
            binding.edUserName.setError("Empty Field!");
        }else if(!Validation.isValidPhoneNumber(user_name)){
            binding.edUserName.setError("Enter valid Mobile number!");
        }else if(pass.isEmpty()){
            binding.edPass.setError("Password is empty!");
        }else {
            System.out.println("fcm tk"+fcmToken);
            UserCredential credential =  new UserCredential(user_name,pass,fcmToken,"Mobile");
            presenter.loginUser(credential);
        }
    }
}
