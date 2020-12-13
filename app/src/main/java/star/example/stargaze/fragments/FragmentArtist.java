package star.example.stargaze.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import star.example.stargaze.R;
import star.example.stargaze.activities.ArtistDetailActivity;
import star.example.stargaze.activities.MainActivity;
import star.example.stargaze.activities.SearchActivity;
import star.example.stargaze.adapters.ArtistAdapter;

import star.example.stargaze.databinding.FragmentArtistBinding;

import star.example.stargaze.models.request.EventFilterBody;
import star.example.stargaze.models.request.Filters;
import star.example.stargaze.models.request.Sort;
import star.example.stargaze.models.response.CelebrityResp;
import star.example.stargaze.sharedPref.MyPreferences;
import star.example.stargaze.sharedPref.PrefConf;
import star.example.stargaze.utils.AppUtils;
import star.example.stargaze.view_presenter.ArtistPresenter;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.List;

public class FragmentArtist extends Fragment implements ArtistPresenter.ArtistView, ArtistAdapter.ArtistListener, View.OnClickListener {

    private FragmentArtistBinding binding;
    private Dialog dialog;
    private View view;
    private Context context;
    private AlertDialog alertDialog;
    private ArtistPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_artist,container,false);
        dialog = AppUtils.hideShowProgress(getContext());
        presenter  = new ArtistPresenter(this);
        view = binding.getRoot();
        dialog = AppUtils.hideShowProgress(context);
        Filters filters = new Filters("");
        EventFilterBody filterBody = new EventFilterBody(filters,new Sort());
        presenter.getArtist(1,20,filterBody,context);
        binding.txtSearch.setOnClickListener(this);
        return binding.getRoot();
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity)
        {
            this.context =context;
        }

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
    public void onSuccess(CelebrityResp celebrityResp) {
            binding.recyclerArtist.setLayoutManager(new GridLayoutManager(context,1));
            binding.recyclerArtist.setAdapter(new ArtistAdapter(context,celebrityResp.getCelebrity(),this));
    }

    @Override
    public void onFailure(Throwable t) {
        Snackbar.make(view,t.getMessage(),Snackbar.LENGTH_SHORT).show();

    }

    @Override
    public void onArtistClick(List<CelebrityResp.Celebrity> celebrities, int position) {
        Gson gson = new Gson();
        String artistListStr = gson.toJson(celebrities);
        System.out.println("json str "+artistListStr);
        MyPreferences.getInstance(context).putString(PrefConf.ARTISTSTR,artistListStr);

        Intent intent = new Intent(context, ArtistDetailActivity.class);
        intent.putExtra("pos",position);
        startActivity(intent);

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.txt_search){
            Intent intent  = new Intent(context, SearchActivity.class);
            startActivity(intent);
        }
    }
}
