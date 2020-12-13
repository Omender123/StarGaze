package star.example.stargaze.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import star.example.stargaze.R;
import star.example.stargaze.adapters.EventAdapter;
import star.example.stargaze.databinding.ActivityArtistDetailBinding;
import star.example.stargaze.models.response.CelebrityResp;
import star.example.stargaze.models.response.EventResp;
import star.example.stargaze.sharedPref.MyPreferences;
import star.example.stargaze.sharedPref.PrefConf;
import star.example.stargaze.utils.AppUtils;
import star.example.stargaze.utils.CenteredToolbar;
import star.example.stargaze.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class ArtistDetailActivity extends AppCompatActivity implements EventAdapter.OnEventItemListener {
    private ActivityArtistDetailBinding binding;
    private Context context;
    private int position;
    private List<CelebrityResp.Celebrity> celebrities;
    private CenteredToolbar toolbar;
    private List<EventResp.Event>events;
    private String subDesc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_artist_detail);
        context = ArtistDetailActivity.this;
        position = getIntent().getIntExtra("pos",0);
        AppUtils.setUpToolbar(this,binding.tool.toolbar,true,false,"Artist");
        setData();
        setRecycler();
    }

    private void setData(){
        Gson gson = new Gson();
        Type type = new TypeToken<List<CelebrityResp.Celebrity>>() {
        }.getType();
        celebrities = gson.fromJson(MyPreferences.getInstance(this).getString(PrefConf.ARTISTSTR, ""), type);
        if(celebrities.get(position).getImages().size()!=0) {
            Glide.with(context).load(Constants.IMG_URL + "" + celebrities.get(position).getImages().get(0).getUrl())
                    .apply(new RequestOptions().circleCrop()).into(binding.imgArtist);
        }else {
            binding.imgArtist.setImageResource(R.drawable.artist);
        }
        binding.tvAboutArtist.setText(celebrities.get(position).getDescription());
        binding.tvArtistName.setText(celebrities.get(position).getName());
        if(celebrities.get(position).getDescription().length()>=40) {
            subDesc = celebrities.get(position).getDescription().substring(0, 40);
        }else {
            subDesc = celebrities.get(position).getDescription();

        }
        binding.tvArtistDesc.setText(subDesc);



    }

    private void setRecycler(){
        Gson gson = new Gson();
        Type type = new TypeToken<List<EventResp.Event>>() {
        }.getType();
        events = gson.fromJson(MyPreferences.getInstance(this).getString(PrefConf.EVENTLISTSTR2, ""), type);
        binding.eventRecycler.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        binding.eventRecycler.setAdapter(new EventAdapter(events,context,this));
    }

    @Override
    public void onEventItemClick(List<EventResp.Event> data, int position) {

    }
}
