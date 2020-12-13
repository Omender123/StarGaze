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

public class BlogSearchAdapter extends RecyclerView.Adapter<BlogSearchAdapter.SearchViewHolder> {

    private Context context;
    private List<BlogResp>searchList;
    private OnBlogItemListener listener;

    public BlogSearchAdapter(Context context, List<BlogResp> searchList,OnBlogItemListener listener) {
        this.context = context;
        this.searchList = searchList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.blog_search_recycler_row, parent, false);

        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        String subject = searchList.get(position).getSubject();
        holder.event_name.setText(subject);
//       holder.event_price.setText("Prices From : Rs "+data.get(position).getPrice());
        holder.img_thumbnail.setImageResource(R.drawable.concert3);
        if (searchList.get(position).getImages().size() != 0){
            Glide.with(context).load(Constants.IMG_URL + "" + searchList.get(position).getImages().get(0).getUrl()).placeholder(R.drawable.place_holder).into(holder.img_thumbnail);
        }else {
            holder.img_thumbnail.setImageResource(R.drawable.place_holder);

        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onBlogClick(searchList,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (searchList==null)
            return 0;
        return searchList.size();
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder {
        TextView event_name;
        ImageView img_thumbnail;
        public SearchViewHolder(@NonNull View itemView) {
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
