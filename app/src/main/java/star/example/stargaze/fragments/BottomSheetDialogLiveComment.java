package star.example.stargaze.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import star.example.stargaze.R;
import star.example.stargaze.activities.EventDetailsActivity;
import star.example.stargaze.adapters.DialogCommentAdapter;

import star.example.stargaze.databinding.BottomSheetDialogLiveCommentLayoutBinding;
import star.example.stargaze.models.Comment;
import star.example.stargaze.models.response.EventCommentResp;
import star.example.stargaze.models.response.EventResp;
import star.example.stargaze.sharedPref.MyPreferences;
import star.example.stargaze.sharedPref.PrefConf;
import star.example.stargaze.utils.Constants;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BottomSheetDialogLiveComment extends BottomSheetDialogFragment implements View.OnClickListener {
    private BottomSheetListener mListener;
    private Context context;
    private BottomSheetDialogLiveCommentLayoutBinding binding;
    private String userName;
    private Socket socket;
    private List<EventResp.Event>events;
    private int position;
    private List<Comment> commentList;
    private DialogCommentAdapter commentAdapter;
    private AppCompatActivity activity;
    private List<EventCommentResp> eventCommentResps;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.bottom_sheet_dialog_live_comment_layout, container, false);
        commentList = new ArrayList<>();
        eventCommentResps = new ArrayList<>();
        Glide.with(context).load(Constants.IMG_URL+""+MyPreferences.getInstance(context).getString(PrefConf.PROFILEPIC,"")).apply(new RequestOptions().circleCrop()).placeholder(R.drawable.ic_profile_pic2).into(binding.senderPic);

        binding.sendMsgBtn.setOnClickListener(this);
        binding.imgClosePopup.setOnClickListener(this);
        socketJoin();
        return binding.getRoot();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof EventDetailsActivity){
            this.context = context;
            this.activity = (AppCompatActivity)context;
        }
        try {
            mListener = (BottomSheetListener) context;

        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement BottomSheetListener");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_close_popup:
                mListener.onDialogClose("");
                dismiss();
                break;
            case R.id.send_msg_btn:
                sendMessage();
                break;

        }
    }

    DialogCommentAdapter.LikeListener likeListener = new DialogCommentAdapter.LikeListener() {
        @Override
        public void onLike(boolean isLike) {
            mListener.onLikeClick(isLike);
        }

        @Override
        public void onDislike(boolean isDislike) {
            mListener.onDislikeClick(isDislike);
        }
    };


    public interface BottomSheetListener {
        void onDialogClose(String text);
        void onLikeClick(boolean isLike);
        void onDislikeClick(boolean isDislike);

    }
    private void socketJoin(){
        try {
            userName = MyPreferences.getInstance(getContext()).getString(PrefConf.KEY_USER_NAME,"");
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
            socketJoinOn();
            getSocketEvent();
            socketMessageOn();
            socketUserDisconnect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }
    private void getSocketEvent(){
        socket.on("eventRes", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                JSONArray data = (JSONArray) args[0];// single event data for past comments
                                 for (int i =0;i<data.length();i++){
                                     try {
                                         JSONObject jsonObject = data.getJSONObject(i);
                                         JSONObject userObj = jsonObject.getJSONObject("user");
                                         EventCommentResp  commentResp = new EventCommentResp();
                                         commentResp.setComment(jsonObject.getString("comment"));
                                         commentResp.setLike(jsonObject.getInt("like"));
                                         commentResp.setDislike(jsonObject.getInt("dislike"));
                                         commentResp.setHappy(jsonObject.getInt("happy"));
                                         commentResp.setAngry(jsonObject.getInt("angry"));
                                         commentResp.setCreatedAt(jsonObject.getString("createdAt"));
                                         commentResp.setName(userObj.getString("name"));
                                         commentResp.setProfileImage(userObj.getString("profileImage"));
                                         commentResp.setOngoing(false);
                                         eventCommentResps.add(commentResp);

                                     }catch (JSONException je){
                                         je.printStackTrace();
                                     }
                                 }
                                Collections.reverse(eventCommentResps);
                                binding.chatRecycler.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));                                // notify the adapter to update the recycler view
                                    commentAdapter = new DialogCommentAdapter(context,eventCommentResps,likeListener);
                                    binding.chatRecycler.setAdapter(commentAdapter);
                                    commentAdapter.notifyDataSetChanged();
                                commentAdapter.update(eventCommentResps) ;
                                System.out.println("eveeent bottomsheet "+data.toString());
                            }
                        });
            }
        });

    }
    private void socketJoinOn(){
        socket.on("join", args ->activity.runOnUiThread(() -> {
            String data = (String) args[0];
            Toast.makeText(context, data, Toast.LENGTH_SHORT).show();
        }));
    }
    private void socketMessageOn(){
        socket.on("message", args ->activity.runOnUiThread(() -> {
            JSONObject data = (JSONObject) args[0];
//            getSocketEvent();
            try {
                JSONObject user = data.getJSONObject("user");
                String name = user.getString("name");
                String profile = user.getString("profileImage");
                String liveComment = data.getString("comment");
                Comment m = new Comment(name,profile,liveComment);

                EventCommentResp  commentResp = new EventCommentResp();
                commentResp.setComment(liveComment);
                commentResp.setLike(0);
                commentResp.setDislike(0);
                commentResp.setHappy(0);
                commentResp.setAngry(0);
                commentResp.setCreatedAt("");
                commentResp.setName(name);
                commentResp.setProfileImage(profile);
                commentResp.setOngoing(false);
                eventCommentResps.add(commentResp);


                commentList.add(m);
                System.out.println("comment "+commentList);
                Collections.reverse(eventCommentResps);
                binding.chatRecycler.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));                                // notify the adapter to update the recycler view
                if(commentList.size() ==1) {
                    commentAdapter = new DialogCommentAdapter( context,eventCommentResps,likeListener);
                    binding.chatRecycler.setAdapter(commentAdapter);
                    commentAdapter.notifyDataSetChanged();
                }
                commentAdapter.update(eventCommentResps) ;
            } catch (JSONException e) {
                e.printStackTrace();
            }



        }));
    }
    private void socketUserDisconnect(){
        socket.on("userdisconnect", args -> activity.runOnUiThread(() -> {
            String data = (String) args[0];

            Toast.makeText(context,data,Toast.LENGTH_SHORT).show();

        }));
    }
    private void sendMessage(){
        try {
            String userPic = MyPreferences.getInstance(context).getString(PrefConf.PROFILEPIC,"");
            JSONObject object = new JSONObject();
            JSONObject user = new JSONObject();
            user.put("name",userName);
            user.put("profileImage",userPic);
            object.put("user",user);
            object.put("comment",binding.editChatBox.getText().toString());
            object.put("eventId",events.get(position).getId());
            socket.emit("message",object);

        }catch (JSONException e){
            e.printStackTrace();
        }

        binding.editChatBox.setText(" ");
    }

    public void setData(List<EventResp.Event>eventList,int position){
        this.events = eventList;
        this.position = position;
    }
}
