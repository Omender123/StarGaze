package star.example.stargaze.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Dialog;
import android.content.Context;

import android.os.Bundle;
import android.view.View;

import star.example.stargaze.R;
import star.example.stargaze.databinding.ActivityUpdateProfileBinding;
import star.example.stargaze.models.request.Password;
import star.example.stargaze.models.request.ProfileEdit;
import star.example.stargaze.models.response.ProfileUpdateResp;
import star.example.stargaze.sharedPref.MyPreferences;
import star.example.stargaze.sharedPref.PrefConf;
import star.example.stargaze.utils.AppUtils;
import star.example.stargaze.utils.Validation;
import star.example.stargaze.view_presenter.UpdateUserProfilePresenter;
import com.google.android.material.snackbar.Snackbar;


public class UpdateProfileActivity extends AppCompatActivity implements UpdateUserProfilePresenter.ProfileView, View.OnClickListener {

    private ActivityUpdateProfileBinding binding;
    private UpdateUserProfilePresenter presenter;
    private Context context;
    private Dialog dialog;
    private View view;
    private String oldPass ="";
    private String newPass ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding = DataBindingUtil.setContentView(this,R.layout.activity_update_profile);
       context = UpdateProfileActivity.this;
       view = binding.getRoot();
       presenter = new UpdateUserProfilePresenter(this);
       dialog = AppUtils.hideShowProgress(context);
        setEditables();
       binding.backArrow.setOnClickListener(this);
       binding.tvUpdateBtn.setOnClickListener(this);



    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back_arrow:
                onBackPressed();
                break;
            case R.id.tv_update_btn:
                updateUserProfile();
                break;

        }
    }


    private void setEditables(){

        if(MyPreferences.getInstance(this).getBoolean(PrefConf.KEY_IS_LOGGED_IN,false)) {
            binding.scrollContainer.setVisibility(View.VISIBLE);
            binding.txtLabelMsg.setVisibility(View.GONE);
            binding.edEditName.setText(MyPreferences.getInstance(context).getString(PrefConf.KEY_USER_NAME,""));
            binding.edEditPhone.setText(MyPreferences.getInstance(context).getString(PrefConf.KEY_USER_PHONE,""));
            binding.edEditEmail.setText(MyPreferences.getInstance(context).getString(PrefConf.KEY_USER_EMAIL,""));

        }else {
                binding.scrollContainer.setVisibility(View.GONE);
                binding.txtLabelMsg.setVisibility(View.VISIBLE);
        }

    }

    private void updateUserProfile() {
        String name = binding.edEditName.getText().toString().trim();
        String email = binding.edEditEmail.getText().toString().trim();
         oldPass = binding.edEditOldPass.getText().toString().trim();
         newPass = binding.edEditNewPass.getText().toString().trim();
        if (name.isEmpty()){
            binding.edEditName.setError("Name can't be Empty!");
        }else if(email.isEmpty()){
            binding.edEditEmail.setError("Email can't be Empty!");
        }else if(!Validation.isValidEmail(email)){
            binding.edEditEmail.setError("Not a valid email!");
        }else if(oldPass.isEmpty()){
            binding.edEditOldPass.setError("Enter old password!");
        }else if(newPass.isEmpty()){
            binding.edEditNewPass.setError("Enter new password!");
        } else {
            ProfileEdit profile = new ProfileEdit(name,email);
            presenter.updateProfile(profile);
        }
    }

    private void changePass(){
        Password password = new Password(oldPass,newPass);
        presenter.changePassword(password);
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
        if(message.equalsIgnoreCase("Token Expired")){
            AppUtils.alertMessage(this,"Your account is logged in to new device or your session is expired!");
        }else {
            Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSuccess(ProfileUpdateResp resp, String message) {
        if(message.equalsIgnoreCase("ok")){

            MyPreferences.getInstance(this).putBoolean(PrefConf.KEY_IS_PROFILE_UPDATED,true);
            MyPreferences.getInstance(this).putString(PrefConf.KEY_USER_NAME,resp.getName());
            MyPreferences.getInstance(this).putString(PrefConf.KEY_USER_EMAIL,resp.getEmail());

            changePass();

        }
    }

    @Override
    public void onChangePassword(String message) {

        AppUtils.showMessageOKCancel("Your Profile Updated Successfully!",this, (dialogInterface, i) -> finish());

    }

    @Override
    public void onFailure(Throwable t) {
        Snackbar.make(view,t.getMessage(),Snackbar.LENGTH_SHORT).show();
    }
}
