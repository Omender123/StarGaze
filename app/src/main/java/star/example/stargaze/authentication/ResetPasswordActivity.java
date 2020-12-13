package star.example.stargaze.authentication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import star.example.stargaze.R;
import star.example.stargaze.databinding.ActivityResetPasswordBinding;
import star.example.stargaze.models.request.Password;
import star.example.stargaze.utils.AppUtils;
import star.example.stargaze.utils.Constants;
import com.google.android.material.snackbar.Snackbar;

public class ResetPasswordActivity extends AppCompatActivity implements PresenterResetPassword.ResetPasswordView, View.OnClickListener {
    private ActivityResetPasswordBinding binding;
    private PresenterResetPassword presenter;
    private Dialog dialog;
    private View view;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_reset_password);
        context = ResetPasswordActivity.this;
        dialog = AppUtils.hideShowProgress(context);
        view = binding.getRoot();
        presenter = new PresenterResetPassword(this);
        binding.backArrow.setOnClickListener(this);
        binding.tvChangeBtn.setOnClickListener(this);
    }

    @Override
    public void showHideProgress(boolean isShow) {
        if (isShow) {
            dialog.show();
        } else {
            dialog.dismiss();
        }
    }

    @Override
    public void onError(String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess(String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
        if (message.equalsIgnoreCase("Ok")) {
            Intent intent = new Intent(this, SuccessActivity.class);
            Constants.successMsg = getResources().getString(R.string.password_changed);
            intent.putExtra(Constants.OTPTYPE, Constants.successMsg);
            Constants.otpType = 2;
            intent.putExtra(Constants.OTPTYPE, Constants.otpType);
            startActivity(intent);
        }
    }

    @Override
    public void onFailure(Throwable t) {
        Snackbar.make(view, t.getMessage(), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_arrow:
                onBackPressed();
                break;
            case R.id.tv_change_btn:
                resetPass();
                break;
        }
    }

    private void resetPass() {
        String newPass = binding.edNewPass.getText().toString().trim();
        String c_pass = binding.edNewCPass.getText().toString().trim();
     /* String oldPass = binding.edOldPass.getText().toString().trim();
*/
        if (newPass.isEmpty()) {
            binding.edNewPass.setError("Empty Password!");
        } else if (c_pass.isEmpty()) {
            binding.edNewCPass.setError("Empty Password!");
        } else if (!newPass.equals(c_pass)) {
          //  binding.edNewCPass.setError("Confirm Password!");
            Toast.makeText(context, "Password Do not match", Toast.LENGTH_SHORT).show();
        } /*else if (oldPass.isEmpty()) {
            binding.edOldPass.setError("Empty Old Password!");
        }*/ else {
            Password password = new Password(newPass,c_pass);
            presenter.resetPassword(password);
        }
    }
}
