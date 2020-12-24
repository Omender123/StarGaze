package star.example.stargaze.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;

import android.app.Dialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import star.example.stargaze.R;
import star.example.stargaze.databinding.ActivityMyReferralBinding;
import star.example.stargaze.models.response.ReferralResp;
import star.example.stargaze.sharedPref.MyPreferences;
import star.example.stargaze.sharedPref.PrefConf;
import star.example.stargaze.utils.AppUtils;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyReferralActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityMyReferralBinding binding;
    private Context context;
    private View view;
    private Dialog dialog;
    CardView textcopy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_my_referral);
        context = MyReferralActivity.this;
        view = binding.getRoot();
        dialog = AppUtils.hideShowProgress(context);
        getRefCode(context);
        binding.imgBackArrow.setOnClickListener(this);
        binding.shareReferralCode.setOnClickListener(this);
        textcopy = findViewById(R.id.textcopy);

        String code = MyPreferences.getInstance(context).getString(PrefConf.KEY_MY_REFERRAL_CODE,"");


        textcopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager cm = (ClipboardManager)getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setText(code);
                Toast.makeText(getApplicationContext(), "Copied ", Toast.LENGTH_SHORT).show();
            }
        });



    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_back_arrow:
                onBackPressed();
                break;
            case R.id.share_referral_code:
                shareReferral(context);
                break;


        }
    }

    public static void shareReferral(Context context) {
        String code = MyPreferences.getInstance(context).getString(PrefConf.KEY_MY_REFERRAL_CODE,"");
        try {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            String sAux = "Hey,\n Its amazing install stargaze and use this\n Referral code : "+code +"\n Download "+ context.getResources().getString(R.string.app_name) + "\n";
            sAux = sAux +"Click : "+ "https://play.google.com/store/apps/details?id=" + context.getPackageName() + "\n";
            i.putExtra(Intent.EXTRA_TEXT, sAux);
            context.startActivity(Intent.createChooser(i, "choose one"));
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void getRefCode(Context context){
        dialog.show();
        Call<ReferralResp> call = AppUtils.getApi(context).getReferralCode();
        call.enqueue(new Callback<ReferralResp>() {
            @Override
            public void onResponse(Call<ReferralResp> call, Response<ReferralResp> response) {
                dialog.dismiss();
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    MyPreferences.getInstance(context).putString(PrefConf.KEY_MY_REFERRAL_CODE,response.body().getMyReferalcode());
                    binding.txtReferralCode.setText(response.body().getMyReferalcode());

                } else {
                    try {
                        String errorRes = response.errorBody().string();
                        JSONObject jsonObject =new JSONObject(errorRes);
                        String err_msg  = jsonObject.getString("error");
                        int status= jsonObject.getInt("status");
                        if(err_msg.equalsIgnoreCase("Token Expired")){
                            AppUtils.alertMessage((AppCompatActivity)context,"Your account is logged in to new device or your session is expired!");
                        }else {
                            Snackbar.make(view, err_msg, Snackbar.LENGTH_SHORT).show();
                        }

                    }
                    catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ReferralResp> call, Throwable t) {
                dialog.dismiss();
                Snackbar.make(view,t.getMessage(),Snackbar.LENGTH_SHORT).show();

            }
        });
    }
}
