package star.example.stargaze.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;

import star.example.stargaze.R;
import star.example.stargaze.databinding.ActivityMyTicketBinding;
import star.example.stargaze.models.response.EventResp;
import star.example.stargaze.sharedPref.MyPreferences;
import star.example.stargaze.sharedPref.PrefConf;
import star.example.stargaze.utils.AppUtils;
import star.example.stargaze.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class MyTicketActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityMyTicketBinding binding;
    private Context context;
    private List<EventResp.Event>events;
    private int position;
    private boolean alternate= true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_my_ticket);
        context = MyTicketActivity.this;
        position = getIntent().getIntExtra("pos",0);
        setData();
        binding.tvAddDiscountCode.setOnClickListener(this);
        binding.tvBookNextBtn.setOnClickListener(this);
        binding.imgBackArrow.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_add_discount_code:
                if(alternate) {
                    binding.discountContainer.setVisibility(View.VISIBLE);
                    alternate = false;
                }else {
                    binding.discountContainer.setVisibility(View.GONE);
                    alternate = true;
                }
                break;
            case R.id.tv_book_next_btn:
                String discountCode = binding.edDiscountCode.getText().toString().trim();

                Intent intent = new Intent(context,TaxActivity.class);
                intent.putExtra("pos",position);
                intent.putExtra("code",discountCode);
                startActivity(intent);

                break;
            case R.id.img_back_arrow:
                onBackPressed();
                break;
        }
    }

    private void setData(){
        Gson gson = new Gson();
        Type type = new TypeToken<List<EventResp.Event>>() {
        }.getType();
        events = gson.fromJson(MyPreferences.getInstance(this).getString(PrefConf.EVENTLISTSTR, ""), type);
        Spanned text = Html.fromHtml("<b>"+events.get(position).getName()+"</b> <br/> "+"<font color=#B9B7B7>"+
                AppUtils.getDate(events.get(position).getStartDate()) +" "+
                AppUtils.getTime(events.get(position).getStartDate()));

        Spanned artist = Html.fromHtml("<b>"+events.get(position).getArtist()+"</b><br/><font color=#757373>"+"Price : "+ Constants.RUPEE +events.get(position).getPrice());
        binding.txtEventTitleAndTime.setText(text);
        binding.tvArtistName.setText(artist);
//        payableAmount = events.get(position).getPrice();

        System.out.println("event id "+events.get(position).getId());
    }
}
