package star.example.stargaze.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import star.example.stargaze.R;
import star.example.stargaze.adapters.UpcomingEventAdapter;
import star.example.stargaze.databinding.ActivityDetailsEventBinding;
import star.example.stargaze.fragments.BottomSheetDialogLiveComment;
import star.example.stargaze.models.response.EventPaidCheckResp;
import star.example.stargaze.models.response.EventResp;
import star.example.stargaze.sharedPref.MyPreferences;
import star.example.stargaze.sharedPref.PrefConf;
import star.example.stargaze.utils.AppUtils;
import star.example.stargaze.utils.Constants;
import star.example.stargaze.view_presenter.VideoVerificationPresenter;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.LoopingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.razorpay.PaymentData;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class EventDetailsActivity extends AppCompatActivity implements View.OnClickListener,
        VideoVerificationPresenter.VideoVerificationView, UpcomingEventAdapter.OnUpcomingEventListener, BottomSheetDialogLiveComment.BottomSheetListener {
    private ActivityDetailsEventBinding binding;
    private Context context;
    private List<EventResp.Event> events;
    private List<EventResp.Event> upcomingEvents;
    private List<EventResp.Event> allEvents;
    private int position;
    private Dialog dialog;
    private View view;
    private double payableAmount;
    private PaymentData paymentData1;
    private String eventOrderId;
    private VideoVerificationPresenter presenter;
    private boolean isFromHomeItemClick;
    private boolean isUpcomingItem;


    private SimpleExoPlayer simpleExoPlayer;
    private SimpleExoPlayerView simpleExoPlayerView;
    private AdaptiveTrackSelection.Factory factory;

    private ProgressBar progressBar;
    private TrackSelector trackSelector;
    private LoadControl loadControl;
    private DefaultBandwidthMeter defaultBandwidthMeter;
    private LoopingMediaSource loopingMediaSource;
    private int lastSongIndex = 0;
    private String videoUrl;
    private BottomSheetDialogLiveComment bottomSheetDialogLiveComment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_details_event);
        context = EventDetailsActivity.this;
        position = getIntent().getIntExtra("pos", 0);
        dialog = AppUtils.hideShowProgress(context);
        view = binding.getRoot();
        isFromHomeItemClick = getIntent().getBooleanExtra("isFromHomeItemClick", false);
        Gson gson = new Gson();
        Type type = new TypeToken<List<EventResp.Event>>() {
        }.getType();

        events = gson.fromJson(MyPreferences.getInstance(this).getString(PrefConf.EVENTLISTSTR, ""), type);
        setData(events, position);
        getAllEvents();

        if (isFromHomeItemClick) {
            setUpcomingEvents(events);
        } else {
            setUpcomingEvents(allEvents);
        }

        presenter = new VideoVerificationPresenter(this);

        binding.imgBackArrow.setOnClickListener(this);
        binding.share.setOnClickListener(this);
//        binding.txtLabelShow.setOnClickListener(this);
        binding.playBtn.setOnClickListener(this);
        binding.txtWatchNowBtn.setOnClickListener(this);
        binding.imgDownArrowBtn.setOnClickListener(this);
    }


    private void playMedia() {
        if (events.get(position).getIntroUrl().isEmpty()) {

        } else {

            videoUrl = Constants.IMG_URL + "" + events.get(position).getIntroUrl();
            defaultBandwidthMeter = new DefaultBandwidthMeter();
            factory = new AdaptiveTrackSelection.Factory(defaultBandwidthMeter);
            trackSelector = new DefaultTrackSelector(factory);
            loadControl = new DefaultLoadControl();
            simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector, loadControl);
            binding.exoPlayer.setPlayer(simpleExoPlayer);


            DefaultBandwidthMeter dBandwidthMeter = new DefaultBandwidthMeter();
            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this,
                    Util.getUserAgent(this, "com.exoplayerdemo"), dBandwidthMeter);
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
            // below line you can pass video url
            MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(videoUrl),
                    dataSourceFactory, extractorsFactory, null, null);

            loopingMediaSource = new LoopingMediaSource(mediaSource);
            simpleExoPlayer.prepare(mediaSource);

            simpleExoPlayer.setPlayWhenReady(true);

            simpleExoPlayer.addListener(new ExoPlayer.EventListener() {
                @Override
                public void onTimelineChanged(Timeline timeline, @Nullable Object manifest, int reason) {

                }

                @Override
                public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

                }

                @Override
                public void onLoadingChanged(boolean isLoading) {

                }

                @Override
                public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                    if (playbackState == ExoPlayer.STATE_BUFFERING) {
                        binding.progressBar.setVisibility(View.VISIBLE);
                    } else {
                        binding.progressBar.setVisibility(View.GONE);
                    }
                    if (playbackState == ExoPlayer.STATE_ENDED) {
                        lastSongIndex++;
                        binding.playBtn.setVisibility(View.VISIBLE);
//                        playMedia();
                    }
                }

                @Override
                public void onRepeatModeChanged(int repeatMode) {

                }

                @Override
                public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

                }

                @Override
                public void onPlayerError(ExoPlaybackException error) {
                    if (!TextUtils.isEmpty(error.getMessage())) {
                        Log.d("ERROR::", error.getMessage());
                        Toast.makeText(getApplicationContext(), "Error::" + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onPositionDiscontinuity(int reason) {
                    int latestSongIndex = simpleExoPlayer.getCurrentWindowIndex();
                    if (latestSongIndex != lastSongIndex) {
                        lastSongIndex = latestSongIndex;
                    }
                }

                @Override
                public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

                }

                @Override
                public void onSeekProcessed() {

                }
            });
        }
    }

    private void getAllEvents() {
        Gson gson = new Gson();
        Type type = new TypeToken<List<EventResp.Event>>() {
        }.getType();
        allEvents = gson.fromJson(MyPreferences.getInstance(this).getString(PrefConf.ALLEVENTSTR, ""), type);
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.img_back_arrow:
                onBackPressed();
                break;
            case R.id.share:
                AppUtils.shareVideo(context, events.get(position).getUrl());
                break;

            case R.id.txt_watch_now_btn:
                presenter.paidEvent(MyPreferences.getInstance(context).getString(PrefConf.KEY_USER_ID, ""), events.get(position).getId(), context);

                break;

            case R.id.play_btn:
                binding.playBtn.setVisibility(View.GONE);
                playMedia();
                break;
            case R.id.img_down_arrow_btn:
                bottomSheetDialogLiveComment = new BottomSheetDialogLiveComment();
                bottomSheetDialogLiveComment.setData(events, position);
                bottomSheetDialogLiveComment.show(getSupportFragmentManager(), "bottomSheetDialogLiveComment");

                break;

        }
        if (intent != null) {
            startActivity(intent);
        }
    }

    private void setData(List<EventResp.Event> data, int pos) {

        Spanned dateTime = Html.fromHtml("<b>" + AppUtils.getDate(data.get(pos).getStartDate()) + "</b> <br/> " + "<font color=#B9B7B7>" +
                AppUtils.getTime(data.get(pos).getStartDate()) + "-" +
                AppUtils.getTime(data.get(pos).getEndDate())
        );
        Spanned artist = Html.fromHtml(
                "Artist : " + "</font>" + "<font color=#B9B7B7>" +
                        data.get(pos).getArtist() + "<br/>" + "<font color=#FFFFFF>Price : </font>" + "<font color=#B9B7B7>" + Constants.RUPEE + data.get(pos).getPrice() + "</font>");

        Spanned event_title = Html.fromHtml("<b>" + data.get(pos).getName() + "</b> <br/> " + "New Delhi");
        binding.txtEventDateAndTime.setText(dateTime);
        binding.txtEventTitle.setText(event_title);
        binding.txtArtistAndPrice.setText(artist);
        binding.tvEventDescription.setText(data.get(pos).getDescription());
        payableAmount = events.get(pos).getPrice();

        System.out.println("event id " + data.get(pos).getId());
    }

    private void setUpcomingEvents(List<EventResp.Event> allEvents) {
        upcomingEvents = new ArrayList<>();

        for (int i = 0; i < allEvents.size(); i++) {
            EventResp.Event event = allEvents.get(i);
            if (event.getEventType() != null) {
                if (event.getEventType().equals("Upcoming")) {
                    upcomingEvents.add(event);
                }
            }
        }

        binding.footerRecycler.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        binding.footerRecycler.setAdapter(new UpcomingEventAdapter(upcomingEvents, context, this));
    }

    @Override
    public void showHideProgress(boolean isShow) {
        if (isShow) {
            dialog.show();
        } else {
            dialog.dismiss();
        }
    }

    @Override
    public void onError(String message) {
        if (message.equalsIgnoreCase("Token Expired")) {
            AppUtils.alertMessage(this, "Your account is logged in to new device or your session is expired!");
        } else {
            Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onVidOtpSuccess(String message) {
        if (message.equalsIgnoreCase("ok")) {
            Intent intent = new Intent(context, VideoVerificationActivity.class);
            intent.putExtra("eventId", events.get(position).getId());
            intent.putExtra("pos", position);
            startActivity(intent);
        }
    }

    @Override
    public void onVidOtpResendSuccess(String message) {

    }

    @Override
    public void onVidOtpVerifySuccess(String message) {

    }

    @Override
    public void isEventPaid(EventPaidCheckResp paidCheckResp) {
        if (paidCheckResp.getResult()) {
            presenter.getVideoOtp(events.get(position).getId(), context);
        } else {
            showPurchaseDialog();
        }
    }

    @Override
    public void onFailure(Throwable t) {
        Snackbar.make(view, t.getMessage(), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onUpcomingItemClick(List<EventResp.Event> data, int pos) {
        Gson gson = new Gson();
        String eventListStr = gson.toJson(data);
        System.out.println("json str " + eventListStr);
        MyPreferences.getInstance(context).putString(PrefConf.EVENTLISTSTR, eventListStr);
        isUpcomingItem = true;
        events = data;
        position = pos;
        setData(data, position);
    }

    private void showPurchaseDialog() {
        BottomSheetDialog dialogSheet = new BottomSheetDialog(this, R.style.SheetDialog);
        dialogSheet.setContentView(R.layout.purchase_dialog);

        TextView tv_purchase_btn = dialogSheet.findViewById(R.id.tv_purchase_btn);

        tv_purchase_btn.setOnClickListener(view -> {

            Intent intent = new Intent(context, MyTicketActivity.class);
            intent.putExtra("pos", position);
            startActivity(intent);
            dialogSheet.dismiss();
        });

        dialogSheet.show();
    }


    @Override
    public void onDislikeClick(boolean isDislike) {

    }

    @Override
    public void onDialogClose(String text) {
        bottomSheetDialogLiveComment.dismiss();
    }

    @Override
    public void onLikeClick(boolean isLike) {

    }
}
