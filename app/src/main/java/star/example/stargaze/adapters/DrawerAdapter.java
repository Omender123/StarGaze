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


public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.DrawerViewHolder> {


    public static int [] menu_icon_arr = {R.drawable.order,R.drawable.wallet_icon,R.drawable.my_ref_code,/*R.drawable.ic_share,*/
            R.drawable.help_sup/*,R.drawable.ic_blog_icon*//*,R.drawable.ic_change_password*/,R.drawable.logout_icon};
    public static int[] menu_titles ={R.string.my_order,R.string.my_wallet,R.string.my_referral,
            /*R.string.drawer_nav_share,*/R.string.drawer_nav_customer_support,/*R.string.drawer_nav_blog,*//*R.string.forgot_password,*/R.string.drawer_nav_logout};

    private DrawerMenuItemClickListener listener;
    private Context context;

    public DrawerAdapter(DrawerMenuItemClickListener listener, Context context) {
        this.listener = listener;
        this.context = context;
    }

    @NonNull
    @Override
    public DrawerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.nav_drawer_recycler_row,parent,false);
        return new DrawerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DrawerViewHolder holder, int position) {

            holder.icon.setImageResource(menu_icon_arr[position]);
            holder.title.setText(menu_titles[position]);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onDrawerItemClick(position);
                }
            });
    }

    @Override
    public int getItemCount() {
        return menu_icon_arr.length;
    }


    public class DrawerViewHolder extends RecyclerView.ViewHolder {
        ImageView icon,arrow;
        TextView title;
        public DrawerViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.menu_icon);
            title = itemView.findViewById(R.id.menu_name);
//            arrow = itemView.findViewById(R.id.right_arrow);

        }
    }


   public interface DrawerMenuItemClickListener{
        void onDrawerItemClick(int position);
    }
}
