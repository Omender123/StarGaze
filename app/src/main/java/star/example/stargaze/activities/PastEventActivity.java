package star.example.stargaze.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import star.example.stargaze.R;

import star.example.stargaze.adapters.PastEventAdapter;
import star.example.stargaze.databinding.ActivityPastEventBinding;
import star.example.stargaze.models.PastData;

import java.util.ArrayList;
import java.util.List;

public class PastEventActivity extends AppCompatActivity implements
        PastEventAdapter.OnPastEventItemListener, View.OnClickListener {
    private ActivityPastEventBinding binding;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_past_event);
        context = PastEventActivity.this;
//        binding.recyclerPastEvent.setLayoutManager(new GridLayoutManager(context,1));
//        binding.recyclerPastEvent.setAdapter(new PastEventAdapter(getPastData(),context,this));
        binding.imgBackArrow.setOnClickListener(this);
    }

    private List<PastData> getPastData(){
        List<PastData>data = new ArrayList<>();
        int [] images ={R.drawable.concert,R.drawable.concert2,R.drawable.concert3,R.drawable.concert4,R.drawable.concert,R.drawable.concert2,R.drawable.concert3,R.drawable.concert4,R.drawable.concert,R.drawable.concert2};
        String[]eventNames ={"comedy show","Shaha-Jan-Night-drama","Shane_E_Bihar","College Fun Festive","comedy show","Shaha-Jan-Night-drama","Shane_E_Bihar","College Fun Festive","comedy show","Shaha-Jan-Night-drama"};

        for(int i=0;i<images.length;i++){
            PastData pastData = new PastData(eventNames[i],"Sunday 09:00 PM","District 9- Vancouver,India",images[i],"District Presents");
            data.add(pastData);
        }
        return data;
    }

    @Override
    public void onEventItemClick() {

    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.img_back_arrow){
            onBackPressed();
        }
    }
}
