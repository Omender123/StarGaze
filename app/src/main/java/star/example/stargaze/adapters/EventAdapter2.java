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
import star.example.stargaze.models.response.EventResp;
import star.example.stargaze.utils.AppUtils;
import star.example.stargaze.utils.Constants;

import java.util.List;

public class EventAdapter2 extends RecyclerView.Adapter<EventAdapter2.EventViewHolder> {
    private List<EventResp.Event>data;
    private Context context;
    private OnEventItemListener listener;
    private List<EventResp.Event>allEvents;

    public EventAdapter2(List<EventResp.Event> data, Context context, OnEventItemListener listener, List<EventResp.Event>allEvents) {
        this.data = data;
        this.context = context;
        this.listener = listener;
        this.allEvents = allEvents;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.past_event_recycler_row,parent,false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        String name =data.get(position).getName();
       holder.event_name.setText(name);
//       holder.event_price.setText("Prices From : Rs "+data.get(position).getPrice());
        if (data.get(position).getImages() !=null && data.get(position).getImages().size() !=0){
            Glide.with(context).load(Constants.IMG_URL + "" + data.get(position).getImages().get(0).getUrl()).into(holder.img_thumbnail);
        }else {
            holder.img_thumbnail.setImageResource(R.drawable.concert3);

        }
       String date = AppUtils.getDate( data.get(position).getStartDate());
       holder.event_time.setText(date);
       holder.event_loc.setText("Delhi, New Delhi");

       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               listener.onEventItemClick(data,position,allEvents);
           }
       });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class EventViewHolder extends RecyclerView.ViewHolder {
      TextView event_name,event_price,event_time,event_loc,event_presents;
      ImageView img_thumbnail;
        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            event_name = itemView.findViewById(R.id.event_name);
            event_time = itemView.findViewById(R.id.event_time);
            event_loc = itemView.findViewById(R.id.event_loc);
            event_presents = itemView.findViewById(R.id.event_presents);
//            event_price = itemView.findViewById(R.id.event_price);
            img_thumbnail = itemView.findViewById(R.id.img_thumbnail);
        }
    }

    public interface OnEventItemListener{
        void onEventItemClick(List<EventResp.Event> data, int position,List<EventResp.Event>allEvents);
    }

}
