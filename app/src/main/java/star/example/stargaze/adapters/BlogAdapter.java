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
import star.example.stargaze.R;
import star.example.stargaze.models.response.BlogResp;
import star.example.stargaze.utils.Constants;

import java.util.List;

public class BlogAdapter extends RecyclerView.Adapter<BlogAdapter.BlogViewHolder> {
    private List<BlogResp>data;
    private Context context;
    private OnBlogItemListener listener;

    public BlogAdapter(List<BlogResp> data, Context context, OnBlogItemListener listener) {
        this.data = data;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BlogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.blog_recycler_row,parent,false);
        return new BlogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BlogViewHolder holder, int position) {
        String subject = data.get(position).getSubject();
        holder.event_name.setText(subject);
//       holder.event_price.setText("Prices From : Rs "+data.get(position).getPrice());
        holder.img_thumbnail.setImageResource(R.drawable.concert3);
        if (data.get(position).getImages().size() != 0){
            Glide.with(context).load(Constants.IMG_URL + "" + data.get(position).getImages().get(0).getUrl()).placeholder(R.drawable.place_holder).into(holder.img_thumbnail);
            }else {
            holder.img_thumbnail.setImageResource(R.drawable.place_holder);

        }
       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               listener.onBlogClick(data,position);
           }
       });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class BlogViewHolder extends RecyclerView.ViewHolder {
      TextView event_name;
      ImageView img_thumbnail;
        public BlogViewHolder(@NonNull View itemView) {
            super(itemView);
            event_name = itemView.findViewById(R.id.event_name);
//            event_price = itemView.findViewById(R.id.event_price);
            img_thumbnail = itemView.findViewById(R.id.img_thumbnail);
        }
    }

    public interface OnBlogItemListener{
        void onBlogClick(List<BlogResp> data, int position);
    }

}
