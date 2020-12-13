package star.example.stargaze.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import star.example.stargaze.R;
import star.example.stargaze.adapters.LiveCommentAdapter;
import star.example.stargaze.databinding.ActivityLiveStreamBinding;
import star.example.stargaze.models.Comment;
import star.example.stargaze.models.response.CommentResp;
import star.example.stargaze.models.response.EventResp;
import star.example.stargaze.reaction_animation.Direction;
import star.example.stargaze.reaction_animation.ZeroGravityAnimation;
import star.example.stargaze.sharedPref.MyPreferences;
import star.example.stargaze.sharedPref.PrefConf;
import star.example.stargaze.utils.Constants;
import star.example.stargaze.view_presenter.LiveCommentPresenter;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class LiveStreamActivity extends AppCompatActivity implements View.OnClickListener, LiveCommentPresenter.LiveCommentView {

    private ActivityLiveStreamBinding binding;
    private Context context;
    private List<EventResp.Event>events;
    private int position;

    private Socket socket;
    private LiveCommentPresenter presenter;
    private List<Comment> commentList;
    private LiveCommentAdapter commentAdapter;
    private String userName;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_live_stream);
        context = LiveStreamActivity.this;
        presenter = new LiveCommentPresenter(this);
        commentList = new ArrayList<>();
        position = getIntent().getIntExtra("pos",0);
        Gson gson = new Gson();
        Type type = new TypeToken<List<EventResp.Event>>() {
        }.getType();
        events = gson.fromJson(MyPreferences.getInstance(this).getString(PrefConf.EVENTLISTSTR, ""), type);
        if(events.get(position).getEventType()!=null && events.get(position).getEventType().equalsIgnoreCase("Ongoing")){
            binding.txtLiveLabel.setVisibility(View.VISIBLE);
        }else {
            binding.txtLiveLabel.setVisibility(View.GONE);
        }
        joinSocket();
//        setData();
        playMedia();
        binding.sendMsgBtn.setOnClickListener(this);
        binding.imgHeartReactionBtn.setOnClickListener(this);
    }

    private void joinSocket(){
        try {
            userName = MyPreferences.getInstance(context).getString(PrefConf.KEY_USER_NAME,"");
            socket = IO.socket("https://api.stargaze.digital");

            socket.connect();
            socket.emit("join",userName);
            try {
                JSONObject event = new JSONObject();
                event.put("eventId",events.get(position).getId());
                socket.emit("getEvent",event);
            }catch (JSONException je){
                je.printStackTrace();
            }

            getSocketEvent();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }

    private void getSocketEvent(){
    socket.on("eventRes", new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONArray data = (JSONArray) args[0];// single event data for past comments

                    System.out.println("eveeent "+data.toString());
                }
            });
        }
    });

}

    private void setChatScreen(){
        BottomSheetDialog dialog = new BottomSheetDialog(this,R.style.SheetDialog);
        dialog.setContentView(R.layout.custom_chat_screen_layout);
        View view = dialog.findViewById(R.id.relative_container);
        RecyclerView commentRecycler = dialog.findViewById(R.id.chat_recycler);
        EditText edit_chat_box = dialog.findViewById(R.id.edit_chat_box);
        ImageView send_msg_btn = dialog.findViewById(R.id.send_msg_btn);

        userName = MyPreferences.getInstance(context).getString(PrefConf.KEY_USER_NAME,"");

        socket.on("join", args -> runOnUiThread(() -> {
            String data = (String) args[0];
            Toast.makeText(context,data,Toast.LENGTH_SHORT).show();
        }));

        send_msg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    String userPic = MyPreferences.getInstance(context).getString(PrefConf.PROFILEPIC,"");
                    JSONObject object = new JSONObject();
                    JSONObject user = new JSONObject();
                    user.put("name",userName);
                    user.put("profileImage",userPic);
                    object.put("user",user);
                    object.put("comment",edit_chat_box.getText().toString());
                    object.put("eventId",events.get(position).getId());
                    socket.emit("message",object);

                }catch (JSONException e){
                    e.printStackTrace();
                }

                edit_chat_box.setText(" ");

                }

        });

        socket.on("message", args -> runOnUiThread(() -> {
            JSONObject data = (JSONObject) args[0];
            getSocketEvent();
            try {
                JSONObject user = data.getJSONObject("user");
                String name = user.getString("name");
                String profile = user.getString("profileImage");
                String liveComment = data.getString("comment");
                Comment m = new Comment(name,profile,liveComment);
                commentList.add(m);
                System.out.println("comment "+commentList);
                commentRecycler.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));                                // notify the adapter to update the recycler view
                if(commentList.size() ==1) {
                    commentAdapter = new LiveCommentAdapter(commentList, context);
                    commentRecycler.setAdapter(commentAdapter);
                    commentAdapter.notifyDataSetChanged();
                }
                getSocketEvent();
                commentAdapter.update(commentList) ;
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }));

        socket.on("userdisconnect", args -> runOnUiThread(() -> {
            String data = (String) args[0];

            Toast.makeText(context,data,Toast.LENGTH_SHORT).show();

        }));


        dialog.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_heart_reaction_btn:
                animate_emo_ji();
                break;
            case R.id.send_msg_btn:
                setChatScreen();
                break;
        }

    }

//    private void setData(){
//
//        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
//        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
//        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
//         player = ExoPlayerFactory.newSimpleInstance(this, trackSelector);
//
//        binding.simplePlayer.setPlayer(player);
//        // Create RTMP Data Source
//        play();
//
//
//    }
//
//    private void play(){
//        player.prepare(initMediaSource(events.get(position).getUrl()));
//        player.setPlayWhenReady(true);
//    }
//
//    private MediaSource initMediaSource(String url){
//        RtmpDataSourceFactory rtmpDataSourceFactory = new RtmpDataSourceFactory();
//        MediaSource videoSource = new ExtractorMediaSource
//                .Factory(rtmpDataSourceFactory)
//                .createMediaSource(Uri.parse(url));
//       return videoSource;
//    }

    private void playMedia() {
        if(events.get(position).getIntroUrl().isEmpty()) {

        }else {
            videoUrl = Constants.IMG_URL + "" + events.get(position).getUrl();
            defaultBandwidthMeter = new DefaultBandwidthMeter();
            factory = new AdaptiveTrackSelection.Factory(defaultBandwidthMeter);
            trackSelector = new DefaultTrackSelector(factory);
            loadControl = new DefaultLoadControl();
            simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector, loadControl);
            binding.simplePlayer.setPlayer(simpleExoPlayer);
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
    @Override
    protected void onResume() {
        super.onResume();
//        binding.simplePlayer.getPlayer().getCurrentPosition();
//        player.seekTo(binding.simplePlayer.getPlayer().getCurrentPosition());
//        play();
    }

    @Override
    protected void onPause() {
        super.onPause();
        simpleExoPlayer.stop();
    }

    @Override
    protected void onStop() {
        super.onStop();
        simpleExoPlayer.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        socket.disconnect();
        simpleExoPlayer.stop();
        simpleExoPlayer.release();
    }


    public void emoji_one() {
        // You can change the number of emojis that will be flying on screen
        for (int i = 0; i < 5; i++) {
            flyEmoJi(R.drawable.ic_favorite_red);
        }
    }


    public void emoji_two(){
        for(int i=0;i<5;i++) {
            flyEmoJi(R.drawable.ic_favorite_red);
        }

    }


    public void animate_emo_ji(){
        for(int i=0;i<10;i++) {
            flyEmoJi(R.drawable.reaction_heart);
        }
    }

    public void flyEmoJi(final int resId) {
        ZeroGravityAnimation animation = new ZeroGravityAnimation();
        animation.setCount(1);
        animation.setScalingFactor(0.5f);
        animation.setOriginationDirection(Direction.BOTTOM);
        animation.setDestinationDirection(Direction.TOP);
        animation.setImage(resId);
        animation.setAnimationListener(new Animation.AnimationListener() {
                                           @Override
                                           public void onAnimationStart(Animation animation) {

                                           }
                                           @Override
                                           public void onAnimationEnd(Animation animation) {

                                           }

                                           @Override
                                           public void onAnimationRepeat(Animation animation) {

                                           }
                                       }
        );

        ViewGroup container = findViewById(R.id.reaction_holder);
        animation.play(this,container);

    }


    @Override
    public void showHideProgress(boolean isShow) {

    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void onCommentGet(CommentResp resp) {

    }

//    @Override
//    public void onCommentPost(String message) {
//
//    }

    @Override
    public void onFailure(Throwable t) {

    }
}
