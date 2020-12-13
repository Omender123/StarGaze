package star.example.stargaze.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import star.example.stargaze.R;
import star.example.stargaze.databinding.ActivityConfirmBinding;

public class ConfirmActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityConfirmBinding binding;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_confirm);
        context = ConfirmActivity.this;
        binding.txtSkipBtn.setOnClickListener(this);
        binding.tvBookMoreBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.txt_skip_btn:
                finish();
                break;
            case R.id.tv_book_more_btn:
                Intent intent = new Intent(context,LiveStreamActivity.class);
                startActivity(intent);
                break;
        }

    }
}
