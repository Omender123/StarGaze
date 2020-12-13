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
import star.example.stargaze.models.PastData;

import java.util.List;

public class MyEventAdapter extends RecyclerView.Adapter<MyEventAdapter.EventViewHolder> {
    private List<PastData>data;
    private Context context;
    private OnPastEventItemListener listener;

    public MyEventAdapter(List<PastData> data, Context context, OnPastEventItemListener listener) {
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
       holder.event_name.setText(data.get(position).getEventName());
       holder.event_time.setText(data.get(position).getEventDate());
       holder.event_loc.setText(data.get(position).getEventLoc());
       holder.event_presents.setText(data.get(position).getPresents());
       holder.img_thumbnail.setImageResource(data.get(position).getThumbnailIcon());

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
