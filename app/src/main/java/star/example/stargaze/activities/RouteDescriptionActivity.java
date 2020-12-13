package star.example.stargaze.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;

import star.example.stargaze.R;
import star.example.stargaze.databinding.ActivityRouteDescriptionBinding;

public class RouteDescriptionActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityRouteDescriptionBinding binding;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_route_description);
        context = RouteDescriptionActivity.this;

        Spanned text = Html.fromHtml("<b>"+"&nbsp&nbsp&nbsp Wednesday,June 17"+"</b> <br/> "+"<font color=#B9B7B7>"+"&nbsp&nbsp&nbsp 5:00 PM-5:30 PM"+"</font>");
        binding.txtEventTitleAndTime.setText(text);
        Spanned text2 = Html.fromHtml("<b>"+"&nbsp&nbsp&nbsp Free"+"</b> <br/> "+"<font color=#B9B7B7>"+"&nbsp&nbsp&nbsp on Eventbrite"+"</font>");
        binding.txtLabelShare.setText(text2);
        binding.imgBackArrow.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.img_back_arrow){
            onBackPressed();
        }
    }
}
