package star.example.stargaze.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import star.example.stargaze.R;
import star.example.stargaze.adapters.MyEventAdapter;

import star.example.stargaze.databinding.ActivityMyEventsBinding;
import star.example.stargaze.models.PastData;
import star.example.stargaze.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

public class MyEventsActivity extends AppCompatActivity implements MyEventAdapter.OnPastEventItemListener, View.OnClickListener {

    private ActivityMyEventsBinding binding;
    private Context context;
    private Dialog dialog;
    private View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_my_events);
        context = MyEventsActivity.this;
        view = binding.getRoot();
        dialog = AppUtils.hideShowProgress(context);
        binding.recyclerMyEvent.setLayoutManager(new GridLayoutManager(context,1));
        binding.recyclerMyEvent.setAdapter(new MyEventAdapter(getPastData(),context,this));
//        binding.imgBackArrow.setOnClickListener(this);
//
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.img_back_arrow){
            onBackPressed();
        }
    }

    @Override
    public void onEventItemClick() {

    }

    private List<PastData> getPastData(){
        List<PastData>data = new ArrayList<>();
        int [] images ={R.drawable.concert,R.drawable.concert2,R.drawable.concert3,R.drawable.concert4,};
        String[]eventNames ={"comedy show","Shaha-Jan-Night-drama","Shane_E_Bihar","College Fun Festive"};

        for(int i=0;i<images.length;i++){
            PastData pastData = new PastData(eventNames[i],"Sunday 09:00 PM","District 9- Vancouver,India",images[i],"District Presents");
            data.add(pastData);
        }
        return data;
    }
}
