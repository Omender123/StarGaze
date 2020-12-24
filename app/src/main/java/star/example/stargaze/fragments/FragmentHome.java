package star.example.stargaze.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.viewpager.widget.ViewPager;


import star.example.stargaze.R;

import star.example.stargaze.activities.EventDetailsActivity;
import star.example.stargaze.activities.SearchActivity;
import star.example.stargaze.activities.MainActivity;
import star.example.stargaze.adapters.BannerPagerAdapter;
import star.example.stargaze.adapters.DrawerAdapter;
import star.example.stargaze.adapters.EventAdapter;
import star.example.stargaze.adapters.HorizontalCateAdapter;

import star.example.stargaze.databinding.FragmentHomeBinding;
import star.example.stargaze.models.TempData;
import star.example.stargaze.models.request.EventFilterBody;
import star.example.stargaze.models.request.Filters;
import star.example.stargaze.models.request.Sort;
import star.example.stargaze.models.response.Banner;
import star.example.stargaze.models.response.EventResp;
import star.example.stargaze.sharedPref.MyPreferences;
import star.example.stargaze.sharedPref.PrefConf;
import star.example.stargaze.utils.AppUtils;
import star.example.stargaze.utils.ClickableViewPager;
import star.example.stargaze.view_presenter.EventPresenter;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class FragmentHome extends Fragment implements EventAdapter.OnEventItemListener,
//        DrawerAdapter.DrawerMenuItemClickListener,
        View.OnClickListener, HorizontalCateAdapter.CateClickListener, EventPresenter.EventView {
    private FragmentHomeBinding binding;

    private Dialog dialog;
    private Context context;

    private RelativeLayout view;
    private Timer timer;
    private final long delay = 2500;
    private final long repeat = 2500;
    private int currentPage = 0;
    private Handler handler;
    private ImageView[] dots;
    private int dots_count = 0;
    private LinearLayout dots_panel;
    private DrawerAdapter drawerAdapter;
    private EventPresenter presenter;
    private List<EventResp.Event> upcoming_events ;
    private List<EventResp.Event> past_events ;
    private List<EventResp.Event> ongoing_events;
    private Map<String,List<EventResp.Event>>eventMap;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        dialog = AppUtils.hideShowProgress(context);
        view = binding.getRoot().findViewById(R.id.root_layout);
        handler = new Handler();


        presenter = new EventPresenter(this);
        getEvent();
        presenter.getBanner(context);

        binding.txtSearch.setOnClickListener(this);
        return binding.getRoot();
    }

    private List<Integer> getBanner(){
        List<Integer>img = new ArrayList<>();
        img.add(R.drawable.concert);
        img.add(R.drawable.concert2);
        img.add(R.drawable.concert4);
        img.add(R.drawable.concert3);
        return img;
    }

//    @Override
//    public void onSuccess(List<BannerBean> banners, List<String> imgUrl) {
//        if (banners.size() > 0) {
//            BannerPagerAdapter pagerAdapter = new BannerPagerAdapter(imgUrl, context);
//            binding.bannerPager.setAdapter(pagerAdapter);
//            final Runnable update = () -> {
//                if (currentPage == dots_count) {
//                    currentPage = 0;
//                }
//                binding.bannerPager.setCurrentItem(currentPage++, true);
//            };
//
//            timer = new Timer();
//            timer.schedule(new TimerTask() {
//                @Override
//                public void run() {
//                    handler.post(update);
//                }
//            }, delay, repeat);
//
//            dots_count = pagerAdapter.getCount();
//            dots = new ImageView[dots_count];
//            for (int i = 0; i < dots_count; i++) {
//                dots[i] = new ImageView(context);
//                dots[i].setImageDrawable(ContextCompat.getDrawable(context, R.drawable.non_active_dot));
//
//                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//
//                params.setMargins(8, 0, 8, 0);
//
//                binding.dotsPanel.addView(dots[i], params);
//
//            }
//            dots[0].setImageDrawable(ContextCompat.getDrawable(context, R.drawable.active_dot));
//
//            binding.bannerPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//                @Override
//                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//                }
//
//                @Override
//                public void onPageSelected(int position) {
//                    for (int i = 0; i < dots_count; i++) {
//                        dots[i].setImageDrawable(ContextCompat.getDrawable(context, R.drawable.non_active_dot));
//                    }
//
//                    dots[position].setImageDrawable(ContextCompat.getDrawable(context, R.drawable.active_dot));
//
//                }
//
//                @Override
//                public void onPageScrollStateChanged(int state) {
//                }
//            });
//
//        }
//        binding.pagerContainer.setVisibility(View.VISIBLE);
//    }

    private void getEvent(){
        Filters filters = new Filters("");
        Sort sort = new Sort();
        EventFilterBody filterBody = new EventFilterBody(filters,sort);
        presenter.getEvents(1,20,filterBody,context);
    }
    @Override
    public void onAttach(@NonNull Context context) {
        if (context instanceof MainActivity){
            this.context = context;
        }
        super.onAttach(context);
    }

    @Override
    public void onPause() {
        super.onPause();
        if(timer!=null)
            timer.cancel();
    }

    @Override
    public void onResume() {
        super.onResume();
//        if(MyPreferences.getInstance(context).getBoolean(PrefConf.KEY_IS_PROFILE_UPDATED,false)){
//            binding.head.tvHeadNavUserName.setText(MyPreferences.getInstance(context).getString(PrefConf.KEY_USER_NAME,""));
//        }
    }

    private List<String> setData(){
        List<String> dataList = new ArrayList<>();
        dataList.add("Featured");
        dataList.add("Upcoming");
        dataList.add("Recent");
        return dataList;

    }

    private List<TempData> getEventData(){
        List<TempData> data = new ArrayList<>();
        for(int i =0 ;i<10;i++){
            TempData data1 = new TempData("17/07/2020","Comedy Show","Summer Festival","Delhi","500",R.drawable.concert);
            data.add(data1);
        }
        return data;
    }

    private List<TempData> getEventData1(){
        List<TempData> data = new ArrayList<>();
        for(int i =0 ;i<6;i++){
            TempData data1 = new TempData("17/07/2020","Comedy Show","Summer Festival","Delhi","500",R.drawable.concert3);
            data.add(data1);
        }
        return data;
    }

    private List<TempData> getEventData2(){
        List<TempData> data = new ArrayList<>();
        for(int i =0 ;i<4;i++){
            TempData data1 = new TempData("17/07/2020","Comedy Show","Summer Festival","Delhi","500",R.drawable.concert4);
            data.add(data1);
        }
        return data;
    }

    private Map<String,List<TempData>> getMap(){
        Map<String,List<TempData>>map = new HashMap<>();
        map.put("0",getEventData());
        map.put("1",getEventData1());
        map.put("2",getEventData2());

        return map;
    }

    @Override
    public void onEventItemClick(List<EventResp.Event>data,int position) {

        Gson gson = new Gson();
        String eventListStr = gson.toJson(data);
        System.out.println("json str "+eventListStr);
        MyPreferences.getInstance(context).putString(PrefConf.EVENTLISTSTR,eventListStr);

        Intent intent = new Intent(context, EventDetailsActivity.class);
        intent.putExtra("pos",position);
        intent.putExtra("isFromHomeItemClick",true);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.txt_search){
            Intent intent = new Intent(context, SearchActivity.class);
            startActivity(intent);
        }

    }

    @Override
    public void onEventCateClick(int position,Map<String,List<EventResp.Event>>map,List<EventResp.Event>allEvents) {
        binding.parentRecyclerView.setLayoutManager(new GridLayoutManager(context,2));
        binding.parentRecyclerView.setAdapter( new EventAdapter(map.get(String.valueOf(position)),context,this));
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
        Gson gson = new Gson();
        String eventListStr = gson.toJson(eventResp.getEvents());
        System.out.println("json str "+eventListStr);
        MyPreferences.getInstance(context).putString(PrefConf.EVENTLISTSTR2,eventListStr);


        upcoming_events = new ArrayList<>();
        past_events = new ArrayList<>();
        ongoing_events = new ArrayList<>();


        binding.parentRecyclerView.setLayoutManager(new GridLayoutManager(context,2));
        binding.parentRecyclerView.setAdapter( new EventAdapter(eventResp.getEvents(),context,this));
        System.out.println("events "+upcoming_events.size() + "ps "+past_events.size() +"ong "+ongoing_events.size());

    }

    @Override
    public void onBannerSuccess(List<Banner> banners) {
        List<String>bannerIcons = new ArrayList<>();
        if(banners!=null){
            for(int i =0;i<banners.size();i++){
                Banner banner = banners.get(i);
                if(banner.getImages().size()>0)
                    bannerIcons.add(banner.getImages().get(0).getUrl());
            }
        }
        if(bannerIcons.size()>0){

            if (banners.size() > 0) {
           BannerPagerAdapter pagerAdapter = new BannerPagerAdapter(bannerIcons, context);
            binding.bannerPager.setAdapter(pagerAdapter);
            final Runnable update = () -> {
                if (currentPage == dots_count) {
                    currentPage = 0;
                }
                binding.bannerPager.setCurrentItem(currentPage++, true);
            };

            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(update);
                }
            }, delay, repeat);

            dots_count = pagerAdapter.getCount();
            dots = new ImageView[dots_count];
            for (int i = 0; i < dots_count; i++) {
                dots[i] = new ImageView(context);
                dots[i].setImageDrawable(ContextCompat.getDrawable(context, R.drawable.non_active_dot));

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                params.setMargins(8, 0, 8, 0);

                binding.dotsPanel.addView(dots[i], params);

            }
            dots[0].setImageDrawable(ContextCompat.getDrawable(context, R.drawable.active_dot));

            binding.bannerPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    for (int i = 0; i < dots_count; i++) {
                        dots[i].setImageDrawable(ContextCompat.getDrawable(context, R.drawable.non_active_dot));
                    }

                    dots[position].setImageDrawable(ContextCompat.getDrawable(context, R.drawable.active_dot));

                }

                @Override
                public void onPageScrollStateChanged(int state) {
                }
            });

        }
        binding.pagerContainer.setVisibility(View.VISIBLE);

        }

        binding.bannerPager.setOnItemClickListener(new ClickableViewPager.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                FragmentMyEvent fragmentMyEvent = new FragmentMyEvent();
                setFrameLayout(fragmentMyEvent);
//                Intent intent = new Intent(context,MyEventsActivity.class);
//                startActivity(intent);
            }
        });
    }

    private void setFrameLayout(Fragment fragment) {
        FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.fragment_container, fragment);
        ft.commit();

    }

    @Override
    public void onFailure(Throwable t) {
        Snackbar.make(view,t.getMessage(),Snackbar.LENGTH_SHORT).show();
    }

}

