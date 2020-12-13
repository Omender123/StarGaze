package star.example.stargaze.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;


import star.example.stargaze.R;
import star.example.stargaze.activities.EventDetailsActivity;
import star.example.stargaze.activities.MainActivity;
import star.example.stargaze.adapters.EventAdapter2;
import star.example.stargaze.adapters.HorizontalCateAdapter;

import star.example.stargaze.databinding.FragmentMyEventBinding;
import star.example.stargaze.models.PastData;
import star.example.stargaze.models.request.EventFilterBody;
import star.example.stargaze.models.request.Filters;
import star.example.stargaze.models.request.Sort;
import star.example.stargaze.models.response.Banner;
import star.example.stargaze.models.response.EventResp;
import star.example.stargaze.sharedPref.MyPreferences;
import star.example.stargaze.sharedPref.PrefConf;
import star.example.stargaze.utils.AppUtils;
import star.example.stargaze.view_presenter.EventPresenter;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FragmentMyEvent extends Fragment implements EventAdapter2.OnEventItemListener,
        HorizontalCateAdapter.CateClickListener, EventPresenter.EventView, View.OnClickListener {

    private FragmentMyEventBinding binding;
    private Dialog dialog;
    private View view;
    private Context context;
    private AlertDialog alertDialog;
    private EventPresenter presenter;
    private List<EventResp.Event> upcoming_events ;
    private List<EventResp.Event> past_events ;
    private List<EventResp.Event> ongoing_events;
    private Map<String,List<EventResp.Event>> eventMap;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_event,container,false);
        dialog = AppUtils.hideShowProgress(getContext());


        view = binding.getRoot();
        dialog = AppUtils.hideShowProgress(context);
        presenter= new EventPresenter(this);
        getEvent();
//        binding.recyclerMyEvent.setLayoutManager(new GridLayoutManager(context,1));
//        binding.recyclerMyEvent.setAdapter(new MyEventAdapter(getPastData(),context));
//        binding.imgBackArrow.setOnClickListener(this);
        return binding.getRoot();
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity)
        {
            this.context =context;
        }

    }

    @Override
    public void onClick(View view) {

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

    public  void privacyDialog(final Context context) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context,R.style.CustomDialog);
        LayoutInflater inflater =((AppCompatActivity)context).getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.privacy_policy_layout, null);

        dialogBuilder.setView(dialogView);

        TextView okay = dialogView.findViewById(R.id.txt_book_show_btn);
        okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
//        cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                alertDialog.dismiss();
//            }
//        });

        alertDialog = dialogBuilder.create();

        alertDialog.show();

    }

    @Override
    public void onEventItemClick(List<EventResp.Event> data, int position,List<EventResp.Event>allEvents) {
        Gson gson = new Gson();
        String eventListStr = gson.toJson(data);
        System.out.println("json str "+eventListStr);
        MyPreferences.getInstance(context).putString(PrefConf.EVENTLISTSTR,eventListStr);
        saveAllEvent(allEvents);
        Intent intent = new Intent(context, EventDetailsActivity.class);
        intent.putExtra("pos",position);
        intent.putExtra("isFromHomeItemClick",false);
        startActivity(intent);
    }

    private void saveAllEvent(List<EventResp.Event>allEvents){
        Gson gson = new Gson();
        String eventListStr = gson.toJson(allEvents);
        System.out.println("json str "+eventListStr);
        MyPreferences.getInstance(context).putString(PrefConf.ALLEVENTSTR,eventListStr);
    }
    @Override
    public void onEventCateClick(int position,Map<String,List<EventResp.Event>>map,List<EventResp.Event>allEvent) {

        binding.recyclerMyEvent.setLayoutManager(new GridLayoutManager(context,1));
        binding.recyclerMyEvent.setAdapter( new EventAdapter2(map.get(String.valueOf(position)),context,this,allEvent));
    }

    @Override
    public void showHideProgress(boolean isShow) {
        if(isShow){
            dialog.show();
        }else {
            dialog.dismiss();
        }
    }

    @Override
    public void onError(String message) {
        if(message.equalsIgnoreCase("Token Expired")){
            AppUtils.alertMessage((AppCompatActivity) context,"Your account is logged in to new device or your session is expired!");
        }else {
            Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSuccess(EventResp eventResp) {
        upcoming_events = new ArrayList<>();
        past_events = new ArrayList<>();
        ongoing_events = new ArrayList<>();
        List<String> dataList = new ArrayList<>();
        if(eventResp!=null){
            for(int i=0;i<eventResp.getEvents().size();i++){
                EventResp.Event event = eventResp.getEvents().get(i);
                if(event.getEventType()!=null) {
                    if (event.getEventType().equalsIgnoreCase("Upcoming")) {
                        upcoming_events.add(event);
                    } else if (event.getEventType().equalsIgnoreCase("Pastevent")) {
                        past_events.add(event);
                    } else {
                        ongoing_events.add(event);
                    }
                }
            }

            eventMap = new HashMap<>();
            eventMap.put("0",ongoing_events);
            eventMap.put("1",upcoming_events);
            eventMap.put("2",past_events);
        }

        binding.recyclerEventCategory.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        binding.recyclerEventCategory.setAdapter(new HorizontalCateAdapter(setData(eventResp.getEvents()),eventMap,context,this,eventResp.getEvents()));
        binding.recyclerMyEvent.setLayoutManager(new GridLayoutManager(context,1));
        binding.recyclerMyEvent.setAdapter( new EventAdapter2(eventMap.get("0"),context,this,eventResp.getEvents()));
//        System.out.println("events "+upcoming_events.size() + "ps "+past_events.size() +"ong "+ongoing_events.size());

    }

    @Override
    public void onBannerSuccess(List<Banner> banners) {

    }

    @Override
    public void onFailure(Throwable t) {
        Snackbar.make(view,t.getMessage(),Snackbar.LENGTH_SHORT).show();
    }

    private List<String> setData(List<EventResp.Event> events){
        List<String> dataList = new ArrayList<>();
        dataList.add("Featured");
        dataList.add("Upcoming");
        dataList.add("Ongoing");
        return dataList;

    }

    private void getEvent(){
        Filters filters = new Filters("");
        Sort sort = new Sort();
        EventFilterBody filterBody = new EventFilterBody(filters,sort);
        presenter.getEvents(1,20,filterBody,context);
    }
}
