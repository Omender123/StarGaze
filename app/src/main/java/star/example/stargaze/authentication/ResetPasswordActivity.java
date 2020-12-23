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
import star.example.stargaze.models.request.Update_Password;
import star.example.stargaze.utils.AppUtils;
import star.example.stargaze.utils.Constants;
import com.google.android.material.snackbar.Snackbar;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;


public class ResetPasswordActivity extends AppCompatActivity implements/* PresenterResetPassword.ResetPasswordView,*/ View.OnClickListener {
    private ActivityResetPasswordBinding binding;
    private PresenterResetPassword presenter;
    private Dialog dialog;
    private View view;
    private Context context;
    KProgressHUD pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_reset_password);
        context = ResetPasswordActivity.this;
        dialog = AppUtils.hideShowProgress(context);
        view = binding.getRoot();
     //   presenter = new PresenterResetPassword(this);
        binding.backArrow.setOnClickListener(this);
        binding.tvChangeBtn.setOnClickListener(this);
       /*  String phone_no = getIntent().getStringExtra("phones");
        Toast.makeText(context, ""+phone_no, Toast.LENGTH_SHORT).show();*/
    }

   /* @Override
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

   */ @Override
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
     //   String phone_no = getIntent().getStringExtra("phone");

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
           /* Update_Password password = new Update_Password(newPass,c_pass,phone_no);
            presenter.resetPassword(password);*/
            change_Password();
        }
    }

    public void change_Password(){

        pDialog=  KProgressHUD.create(ResetPasswordActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait.....")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

        showpDialog();

        String newPass = binding.edNewPass.getText().toString().trim();
        String c_pass = binding.edNewCPass.getText().toString().trim();
        String phone_no = getIntent().getStringExtra("phones");

         String url="https://api.stargaze.digital/api/user/update_password";

        StringRequest request=new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                hidepDialog();
                Intent intent = new Intent(getApplicationContext(), SuccessActivity.class);
                Constants.successMsg = getResources().getString(R.string.password_changed);
                intent.putExtra(Constants.OTPTYPE, Constants.successMsg);
                Constants.otpType = 2;
                intent.putExtra(Constants.OTPTYPE, Constants.otpType);
                startActivity(intent);

                Toast.makeText(context, "Password Changed Successfully", Toast.LENGTH_SHORT).show();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hidepDialog();
                parseVolleyError(error);

                // Toast.makeText(getApplicationContext(), ""+error.toString(), Toast.LENGTH_LONG).show();
                // Toast.makeText(Login_Activity.this, ""+error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("password", newPass);
                params.put("confirmedPassword", c_pass);
                params.put("phone",phone_no);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();

                // headers.put("Authorization", "Bearer "+Token);

                return headers;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }
    public void parseVolleyError(VolleyError error) {
        try {
            String responseBody = new String(error.networkResponse.data, "utf-8");
            JSONObject data = new JSONObject(responseBody);

            String message=data.getString("error");
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
        } catch (UnsupportedEncodingException errorr) {
        }
    }
    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
   }

