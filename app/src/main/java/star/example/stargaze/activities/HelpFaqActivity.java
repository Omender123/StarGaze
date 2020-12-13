package star.example.stargaze.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.os.Bundle;

import star.example.stargaze.R;
import star.example.stargaze.databinding.ActivityHelpFaqBinding;

public class HelpFaqActivity extends AppCompatActivity {
    private ActivityHelpFaqBinding binding;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_help_faq);

    }

    private void setUpTab(){

    }
}
