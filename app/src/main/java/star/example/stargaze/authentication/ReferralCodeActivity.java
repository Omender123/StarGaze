package star.example.stargaze.authentication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import star.example.stargaze.R;
import star.example.stargaze.databinding.ActivityReferralCodeBinding;

public class ReferralCodeActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityReferralCodeBinding binding;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_referral_code);
        context = ReferralCodeActivity.this;
        binding.tvNextBtn.setOnClickListener(this);
        binding.tvSkipBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_next_btn:
                String code = binding.edReferralCode.getText().toString().trim();
                if(code.isEmpty()){
                    binding.edReferralCode.setError("Please Enter Referral Code!");
                }else {
                    setCode(code);
                }
                break;
            case R.id.tv_skip_btn:
                setCode("");
                break;
        }
    }
    private void setCode(String code){

        Intent intent = new Intent(context,SignUpActivity.class);
        intent.putExtra("refCode",code);
        startActivity(intent);
    }
}
