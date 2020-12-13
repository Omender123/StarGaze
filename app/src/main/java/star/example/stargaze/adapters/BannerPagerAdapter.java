package star.example.stargaze.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import star.example.stargaze.R;
import star.example.stargaze.utils.Constants;

import java.util.List;

public class BannerPagerAdapter extends PagerAdapter {
    private List<String> bannerList;
    private LayoutInflater layoutInflater;
    private Context context;

    public BannerPagerAdapter(List<String> bannerList, Context context) {
        this.bannerList = bannerList;
        this.context = context;
        this.layoutInflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = layoutInflater.inflate(R.layout.pager_adapter_row,container,false);
        ImageView img_banner_display = view.findViewById(R.id.img_banner_display);
//        img_banner_display.setImageResource(bannerList.get(position));
        Glide.with(context).load(Constants.IMG_URL +""+bannerList.get(position)).placeholder(R.drawable.place_holder).into(img_banner_display);
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        if (bannerList==null)
            return 0;
        return bannerList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view ==object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout)object);
    }


}
