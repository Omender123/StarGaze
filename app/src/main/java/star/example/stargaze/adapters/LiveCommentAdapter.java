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
import star.example.stargaze.models.Comment;
import star.example.stargaze.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class LiveCommentAdapter extends RecyclerView.Adapter<LiveCommentAdapter.CommentViewHolder>{
    private List<Comment>comments;
    private Context context;

    public LiveCommentAdapter(List<Comment> comments, Context context) {
        this.comments = comments;
        this.context = context;
    }

    @NonNull
    @Override
    public LiveCommentAdapter.CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View  view = LayoutInflater.from(context).inflate(R.layout.item_msg_recieved,
               parent,false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LiveCommentAdapter.CommentViewHolder holder, int position) {
        System.out.println("comment size "+comments.size());
        holder.comment.setText(comments.get(position).getLiveComment());
        char name  = comments.get(position).getName().charAt(0);

        holder.userName.setText(""+name);
        Glide.with(context).load(Constants.IMG_URL+""+comments.get(position).getProfile()).apply(new RequestOptions().circleCrop()).placeholder(R.drawable.ic_profile_pic).into(holder.sender_pic);
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView comment, userName;
        ImageView sender_pic;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            comment = itemView.findViewById(R.id.txt_comment);
            userName = itemView.findViewById(R.id.sender_name);
            sender_pic = itemView.findViewById(R.id.sender_pic);
        }
    }
    public void update(List<Comment> commentList){
        comments = new ArrayList<>();
               comments.addAll(commentList);
        notifyDataSetChanged();
    }
}
