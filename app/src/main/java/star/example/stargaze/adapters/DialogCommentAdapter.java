package star.example.stargaze.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import star.example.stargaze.R;

import star.example.stargaze.models.response.EventCommentResp;
import star.example.stargaze.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class DialogCommentAdapter extends RecyclerView.Adapter<DialogCommentAdapter.CommentViewHolder> {
   private Context context;
//   private List<Comment>comments;
    private List<EventCommentResp>comments;
   private boolean isLike = false;
   private boolean isDislike = false;
   private LikeListener listener;
    public DialogCommentAdapter(Context context,  List<EventCommentResp> comments,LikeListener listener) {
        this.context = context;
        this.comments = comments;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_comment_recycler_row,parent,false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Glide.with(context).load(Constants.IMG_URL+""+comments.get(position).getProfileImage()).apply(new RequestOptions().circleCrop()).placeholder(R.drawable.ic_profile_pic2).into(holder.sender_pic);
       holder.sender_name.setText(comments.get(position).getName());
       holder.txt_comment.setText(comments.get(position).getComment());
       holder.text_dislike_count.setText(""+comments.get(position).getDislike());
        holder.text_like_count.setText(""+comments.get(position).getLike());
        holder.img_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isLike){
                    isLike = true;
                    holder.img_like.setImageResource(R.drawable.ic_like_white2);
                    listener.onLike(true);
                }else {
                    isLike = false;
                    holder.img_like.setImageResource(R.drawable.ic_like2);
                    listener.onDislike(true);
                }
            }
        });
        holder.img_dislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isDislike){
                    isDislike = true;
                    holder.img_dislike.setImageResource(R.drawable.ic_dislike_white2);
                }else {
                    isDislike = false;
                    holder.img_dislike.setImageResource(R.drawable.ic_dislike2);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {
        ImageView img_like,img_dislike,sender_pic;
        private TextView sender_name,text_message_time,txt_comment,text_like_count,text_dislike_count;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            img_like = itemView.findViewById(R.id.img_like);
            img_dislike = itemView.findViewById(R.id.img_dislike);
            sender_pic = itemView.findViewById(R.id.sender_pic);
            sender_name = itemView.findViewById(R.id.sender_name);
            text_message_time = itemView.findViewById(R.id.text_message_time);
            txt_comment = itemView.findViewById(R.id.txt_comment);
            text_like_count = itemView.findViewById(R.id.text_like_count);
            text_dislike_count = itemView.findViewById(R.id.text_dislike_count);

        }
    }
    public void update(List<EventCommentResp> commentList){
        comments = new ArrayList<>();
        comments.addAll(commentList);
        notifyDataSetChanged();
    }
    public interface LikeListener{
        void onLike(boolean isLike);
        void onDislike(boolean isDislike);
    }
}
