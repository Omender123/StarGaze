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

import star.example.stargaze.models.response.EventResp;

import java.util.List;
import java.util.Map;

public class HorizontalCateAdapter extends RecyclerView.Adapter<HorizontalCateAdapter.CateViewHolder> {
    private List<String>cateList;
    private Context context;
    private int lastCheckedPosition = -1;
    private CateClickListener listener;
    private Map<String,List<EventResp.Event>>map;
    private List<EventResp.Event>allEvents;

    public HorizontalCateAdapter(List<String> cateList, Map<String,List<EventResp.Event>> map, Context context, CateClickListener listener, List<EventResp.Event>allEvents) {
        this.cateList = cateList;
        this.context = context;
        this.map = map;
        this.listener = listener;
        this.allEvents =allEvents;
    }

    @NonNull
    @Override
    public CateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_recycler_row,parent,false);
        return new CateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CateViewHolder holder, int position) {

        if(lastCheckedPosition==-1){

            holder.red_indicator.setImageResource(R.drawable.cate_selected_indicator_red);
            holder.cateTitle.setTextColor(context.getResources().getColor(R.color.white));
            lastCheckedPosition = position;
        }else if(position == lastCheckedPosition){

            holder.red_indicator.setImageResource(R.drawable.cate_selected_indicator_red);
            holder.cateTitle.setTextColor(context.getResources().getColor(R.color.white));
        }else {
              holder.red_indicator.setImageResource(R.drawable.cate_un_selected_indicator_red);;
            holder.cateTitle.setTextColor(context.getResources().getColor(R.color.gray_opacity));
        }
        holder.cateTitle.setText(cateList.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    lastCheckedPosition = position;
                    notifyDataSetChanged();
                    listener.onEventCateClick(position,map,allEvents);

            }
        });
    }

    @Override
    public int getItemCount() {
        return cateList.size();
    }

    public class CateViewHolder extends RecyclerView.ViewHolder {
        TextView cateTitle;
        ImageView red_indicator;
        public CateViewHolder(@NonNull View itemView) {
            super(itemView);
            cateTitle = itemView.findViewById(R.id.tv_cate_title);
            red_indicator = itemView.findViewById(R.id.img_red_indicator);
        }
    }
    public interface  CateClickListener{
        void onEventCateClick(int position,Map<String,List<EventResp.Event>>map,List<EventResp.Event>allEvents);
    }
}
