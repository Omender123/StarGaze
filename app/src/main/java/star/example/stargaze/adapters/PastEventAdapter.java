package star.example.stargaze.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import star.example.stargaze.R;
import star.example.stargaze.models.response.OrderHistoryResp;
import star.example.stargaze.utils.AppUtils;

import java.util.List;

public class PastEventAdapter extends RecyclerView.Adapter<PastEventAdapter.EventViewHolder> {
    private List<OrderHistoryResp>data;
    private Context context;
    private OnPastEventItemListener listener;

    public PastEventAdapter(List<OrderHistoryResp> data, Context context, OnPastEventItemListener listener) {
        this.data = data;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.past_event_recycler_row,parent,false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
       holder.event_name.setText(data.get(position).getEvent().getName());
       holder.event_time.setText(  AppUtils.getDate(data.get(position).getEvent().getStartDate()));
       holder.event_loc.setText(data.get(position).getEvent().getArtist());
//       holder.event_presents.setText(data.get(position).);
       holder.img_thumbnail.setImageResource(R.drawable.concert);

       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               listener.onEventItemClick();
           }
       });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class EventViewHolder extends RecyclerView.ViewHolder {
      TextView event_name,event_time,event_loc,event_presents;
      ImageView img_thumbnail;
        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            event_name = itemView.findViewById(R.id.event_name);
            event_time = itemView.findViewById(R.id.event_time);
            event_loc = itemView.findViewById(R.id.event_loc);
            event_presents = itemView.findViewById(R.id.event_presents);
            img_thumbnail = itemView.findViewById(R.id.img_thumbnail);
        }
    }

    public interface OnPastEventItemListener{
        void onEventItemClick();
    }

}
