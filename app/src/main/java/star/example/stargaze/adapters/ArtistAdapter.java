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
import star.example.stargaze.models.response.CelebrityResp;
import star.example.stargaze.utils.Constants;

import java.util.List;

public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ArtistViewHolder> {
    private Context context;
    private List<CelebrityResp.Celebrity>celebrities;
    private  final int artist_alignment_left =1;
    private  final int artist_alignment_right =2;
    private ArtistListener listener;


    public ArtistAdapter(Context context, List<CelebrityResp.Celebrity> celebrities,ArtistListener listener) {
        this.context = context;
        this.celebrities = celebrities;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ArtistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view ;
        switch (viewType){
            case artist_alignment_left:
                    view =LayoutInflater.from(context).inflate(R.layout.artist_row_left_align,
                            parent,false);
                    return  new ArtistViewHolder(view);
            case artist_alignment_right:
                view = LayoutInflater.from(context).inflate(R.layout.artist_row_right_align,
                        parent,false);
                return new ArtistViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistViewHolder holder, int position) {
        CelebrityResp.Celebrity celebrity = celebrities.get(position);
            holder.tv_artist_name.setText(celebrity.getName());
            holder.tv_artist_desc.setText(celebrity.getDescription());
//        System.out.println("arist "+ Constants.IMG_URL + "" + celebrity.getImages().get(0).getUrl());

            if(celebrity.getImages().size()!=0) {
                Glide.with(context).load(Constants.IMG_URL + "" + celebrity.getImages().get(0).getUrl()).into(holder.img_artist);
            }else {
                    holder.img_artist.setImageResource(R.drawable.artist);
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onArtistClick(celebrities,position);
                }
            });
        }

    @Override
    public int getItemViewType(int position) {
        if(position%2==0){
            return artist_alignment_left;
        }else {
            return artist_alignment_right;
        }

    }
    @Override
    public int getItemCount() {
        if(celebrities.size() ==0)
            return 0;
        return celebrities.size();
    }

    public class ArtistViewHolder extends RecyclerView.ViewHolder {
        ImageView img_artist;
        TextView tv_artist_name,tv_artist_desc;
        public ArtistViewHolder(@NonNull View itemView) {
            super(itemView);
            img_artist = itemView.findViewById(R.id.img_artist);
            tv_artist_name = itemView.findViewById(R.id.tv_artist_name);
            tv_artist_desc = itemView.findViewById(R.id.tv_artist_desc);
        }
    }

    public interface ArtistListener{
        void onArtistClick(List<CelebrityResp.Celebrity>celebrities,int position);
    }
}
