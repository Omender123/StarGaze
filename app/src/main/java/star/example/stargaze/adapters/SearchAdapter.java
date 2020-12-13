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
import star.example.stargaze.models.response.SearchResp;
import star.example.stargaze.utils.Constants;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    private Context context;
    private List<SearchResp>searchList;

    public SearchAdapter(Context context, List<SearchResp> searchList) {
        this.context = context;
        this.searchList = searchList;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.search_recycler_row, parent, false);

        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        SearchResp event = searchList.get(position);
        String name = event.getName();
        holder.event_name.setText(name);
//       holder.event_price.setText("Prices From : Rs "+data.get(position).getPrice());
        holder.img_thumbnail.setImageResource(R.drawable.concert3);
        if (event.getImages().size() != 0){
            Glide.with(context).load(Constants.IMG_URL + "" + event.getImages().get(0).getUrl()).into(holder.img_thumbnail);
        }else {
            holder.img_thumbnail.setImageResource(R.drawable.concert3);

        }
    }

    @Override
    public int getItemCount() {
        if (searchList==null)
            return 0;
        return searchList.size();
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder {
        TextView event_name,event_price;
        ImageView img_thumbnail;
        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            event_name = itemView.findViewById(R.id.event_name);
//            event_price = itemView.findViewById(R.id.event_price);
            img_thumbnail = itemView.findViewById(R.id.img_thumbnail);
        }
    }
}
