package star.example.stargaze.authentication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;

import star.example.stargaze.R;
import star.example.stargaze.databinding.ActivitySignUpBinding;
import star.example.stargaze.models.request.User;
import star.example.stargaze.sharedPref.MyPreferences;
import star.example.stargaze.sharedPref.PrefConf;
import star.example.stargaze.utils.AppUtils;
import star.example.stargaze.utils.Constants;
import star.example.stargaze.utils.Validation;

import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;

public class SignUpActivity extends AppCompatActivity implements SignUpPresenter.SignUpView,View.OnClickListener,DatePickerDialog.OnDateSetListener {
    private ActivitySignUpBinding binding;
    private Context context;
    private Dialog dialog;
    private SignUpPresenter presenter;
    private View view;
    private String phone;
    private String refCode;
    private String mDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_sign_up);
        view = binding.getRoot();
        context = SignUpActivity.this;
        refCode = getIntent().getStringExtra("refCode");
        presenter = new SignUpPresenter(this);
        dialog = AppUtils.hideShowProgress(context);
        binding.gotoSignIn.setOnClickListener(this);
        binding.imgRegister.setOnClickListener(this);
        binding.edDob.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_register:
                    doRegister();
                break;
            case R.id.goto_sign_in:
                Intent intent = new Intent(context,LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.ed_dob:
                showPickerDialog();
                break;
        }

    }
    private void showPickerDialog() {

        DatePickerDialog dtPickerDlg =  new DatePickerDialog(this, android.R.style.Theme_Holo_Dialog,this, 2020, 01, 01);
        dtPickerDlg.getDatePicker().setSpinnersShown(true);
        dtPickerDlg.getDatePicker().setCalendarViewShown(false);
        dtPickerDlg.setTitle("Select Date Of Birth");
        dtPickerDlg.show();
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
        Snackbar.make(view,message,Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess(String message) {
        Snackbar.make(view,"Please verify email,OTP Sent to Your Email!",Snackbar.LENGTH_SHORT).show();
        if(message.equalsIgnoreCase("Ok")   ) {
            Intent intent = new Intent(this, VerifyRegistrationActivity.class);
            Constants.otpType = 1;
            intent.putExtra(Constants.OTPTYPE,Constants.otpType);
            intent.putExtra("phone",phone);
            startActivity(intent);
        }
    }

    @Override
    public void onFailure(Throwable t) {
        Snackbar.make(view,t.getMessage(),Snackbar.LENGTH_SHORT).show();
    }


    private  void  doRegister(){
        String userName = binding.edFirstName.getText().toString().trim();

        String email = binding.edEmail.getText().toString().trim();
        phone = binding.edMobile.getText().toString().trim();
        String pass = binding.edPass.getText().toString().trim();
        String c_pass = binding.edCPass.getText().toString().trim();
        String fcmToken =  MyPreferences.getInstance(context).getString(PrefConf.DEVICE_TOKEN,"");
        System.out.println("fcm Tknrr "+fcmToken);
//        String dob = binding.edDob.getText().toString().trim();
        if(userName.isEmpty()){
            binding.edFirstName.setError("Empty Field!");
        }else if(email.isEmpty()){
            binding.edEmail.setError("Empty Field!");
        }else if(!Validation.isValidEmail(email)){
            binding.edEmail.setError("Enter a valid email!");
        }else if(mDate.isEmpty()){
           Snackbar.make(view,"select your date of birth!",Snackbar.LENGTH_LONG).show();
        } else if(phone.isEmpty()){
            binding.edMobile.setError("Empty Field!");
        }else if(!Validation.isValidPhoneNumber(phone)){
            binding.edMobile.setError("Phone number not valid!");
        }else if(pass.isEmpty()){
            binding.edPass.setError("Empty Field!");
        }else if (c_pass.isEmpty()){
            binding.edPass.setError("Empty Field!");
        }else if(!pass.equals(c_pass)){
            binding.edCPass.setError("Confirm Password!");
        }
        else {

//            int number = Integer.parseInt(phone);
            User user = new User(userName,phone,email,pass,refCode,mDate,fcmToken,"Mobile");
            presenter.createUser(user);
        }


    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month);
        c.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        mDate = DateFormat.format("yyyy-MM-dd",c).toString();
        binding.edDob.setText(mDate);

    }
}
