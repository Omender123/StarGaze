package star.example.stargaze.authentication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import star.example.stargaze.R;
import star.example.stargaze.databinding.ActivitySuccessBinding;
import star.example.stargaze.utils.Constants;

public class SuccessActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivitySuccessBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_success);
        binding.tvOkay.setOnClickListener(this);
        setText();
    }
    private void setText(){
        String success = getIntent().getStringExtra(Constants.msg);
        int type = getIntent().getIntExtra(Constants.OTPTYPE,0);
        binding.txtSuccess.setText(success);
        if(type == 1){
            binding.dummyText.setVisibility(View.GONE);
        }else {
            binding.dummyText.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.tv_okay){
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
