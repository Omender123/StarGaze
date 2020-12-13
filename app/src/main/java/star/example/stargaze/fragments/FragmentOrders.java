package star.example.stargaze.fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;


import star.example.stargaze.R;
import star.example.stargaze.activities.MainActivity;
import star.example.stargaze.adapters.OrderHistoryAdapter;

import star.example.stargaze.databinding.FragmentOrdersBinding;
import star.example.stargaze.models.PastData;
import star.example.stargaze.models.response.OrderHistoryResp;
import star.example.stargaze.utils.AppUtils;
import star.example.stargaze.view_presenter.OrderHistoryPresenter;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class FragmentOrders extends Fragment implements OrderHistoryPresenter.OrderHistoryView, OrderHistoryAdapter.OnOrderItemListener, View.OnClickListener{
    private FragmentOrdersBinding binding;
    private OrderHistoryPresenter presenter;
    private Dialog dialog;
    private View view;
    private Context context;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_orders,container,false);
        dialog = AppUtils.hideShowProgress(getContext());

        presenter = new OrderHistoryPresenter(this);
        presenter.getOrders(context);

//        binding.imgBackArrow.setOnClickListener(this);

        view = binding.getRoot();
        return binding.getRoot();
    }



    @Override
    public void onAttach(@NonNull Context context) {
        if (context instanceof MainActivity){
            this.context = context;
        }
        super.onAttach(context);
    }

    private List<PastData> getPastData(){
        List<PastData>data = new ArrayList<>();
        int [] images ={R.drawable.concert,R.drawable.concert2,R.drawable.concert3,R.drawable.concert4,R.drawable.concert,R.drawable.concert2,R.drawable.concert3,R.drawable.concert4,R.drawable.concert,R.drawable.concert2};
        String[]eventNames ={"comedy show","Shaha-Jan-Night-drama","Shane_E_Bihar","College Fun Festive","comedy show","Shaha-Jan-Night-drama","Shane_E_Bihar","College Fun Festive","comedy show","Shaha-Jan-Night-drama"};

        for(int i=0;i<images.length;i++){
            PastData pastData = new PastData(eventNames[i],"Sunday 09:00 PM","District 9- Vancouver,India",images[i],"District Presents");
            data.add(pastData);
        }
        return data;
    }


    @Override
    public void onClick(View view) {

    }

    @Override
    public void showHideProgress(boolean isShow) {
        if(isShow){
            dialog.show();
        }else {
            dialog.dismiss();
        }
    }

    @Override
    public void onError(String message) {
        if(message.equalsIgnoreCase("Token Expired")){
            AppUtils.alertMessage((AppCompatActivity) context,"Your account is logged in to new device or your session is expired!");
        }else {
            Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSuccess(List<OrderHistoryResp> historyResp) {
        if(historyResp!=null && historyResp.size()>0) {
            binding.recyclerOrderHistory.setLayoutManager(new GridLayoutManager(context, 1));
            binding.recyclerOrderHistory.setAdapter(new OrderHistoryAdapter(historyResp, context, this));
            binding.txtListIsEmpty.setVisibility(View.GONE);
            binding.recyclerOrderHistory.setVisibility(View.VISIBLE);
        }else {
            binding.txtListIsEmpty.setText(getResources().getString(R.string.No_Data_Found));
            binding.txtListIsEmpty.setVisibility(View.VISIBLE);
            binding.recyclerOrderHistory.setVisibility(View.GONE);
        }
    }

    @Override
    public void onFailure(Throwable t) {
        Snackbar.make(view,t.getMessage(),Snackbar.LENGTH_SHORT).show();

    }

    @Override
    public void onOrderItemClick() {

    }
}
