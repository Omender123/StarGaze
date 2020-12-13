package star.example.stargaze.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import star.example.stargaze.R;
import star.example.stargaze.models.response.BlogResp;
import star.example.stargaze.utils.Constants;

import java.util.List;

public class BlogPagerAdapter extends PagerAdapter {
    private List<String> blogList;
    private List<BlogResp>blogs;
    private LayoutInflater layoutInflater;
    private Context context;

    public BlogPagerAdapter(List<String> blogList,List<BlogResp>blogs, Context context) {
        this.blogList = blogList;
        this.context = context;
        this.blogs =blogs;
        this.layoutInflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = layoutInflater.inflate(R.layout.pager_adapter_blog_row,container,false);
        ImageView img_banner_display = view.findViewById(R.id.img_banner_display);
        TextView tv_blog_subject = view.findViewById(R.id.tv_blog_subject);
//        img_banner_display.setImageResource(bannerList.get(position));
        tv_blog_subject.setText(blogs.get(position).getSubject());
        Glide.with(context).load(Constants.IMG_URL +""+blogList.get(position)).placeholder(R.drawable.place_holder).into(img_banner_display);
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        if (blogList==null)
            return 0;
        return blogList.size();
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
