package star.example.stargaze.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import star.example.stargaze.R;
import star.example.stargaze.activities.MainActivity;
import star.example.stargaze.authentication.LoginActivity;
import star.example.stargaze.sharedPref.MyPreferences;
import star.example.stargaze.sharedPref.PrefConf;

public class SplashActivity extends AppCompatActivity {

    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        handler = new Handler();
        scheduleSplash();
    }
    private void scheduleSplash(){
        int SPLASH_TIME_OUT =2500;
        handler.postDelayed(this::openMainActivity,SPLASH_TIME_OUT);
    }
    private void openMainActivity(){
        if(MyPreferences.getInstance(this).getBoolean(PrefConf.KEY_IS_LOGGED_IN,false)){
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
        }else {
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
    }
}
