package star.example.stargaze.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import star.example.stargaze.R;
import star.example.stargaze.activities.MainActivity;
import star.example.stargaze.activities.SearchActivity;
import star.example.stargaze.adapters.TabPagerAdapter;
import star.example.stargaze.databinding.FragmentHelpFaqBinding;

public class FragmentHelpFAQ extends Fragment  implements View.OnClickListener {
    private FragmentHelpFaqBinding binding;
    private Context context;
    private View view;
    private TabPagerAdapter pagerAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_help_faq, container, false);
        pagerAdapter = new TabPagerAdapter(getActivity().getSupportFragmentManager(),1);
        pagerAdapter.addFragment(new FragmentHelpAccount(),"Account");
        pagerAdapter.addFragment(new FragmentHelpPayment(),"Payment");
        pagerAdapter.addFragment(new FragmentHelpEvent(),"Event");
        pagerAdapter.addFragment(new FragmentHelpAbout(),"About");
        binding.viewPager.setAdapter(pagerAdapter);
        binding.tabLayout.setupWithViewPager( binding.viewPager);
        binding.txtSearch.setOnClickListener(this);
//        binding.tabLayout.addTab(binding.tabLayout.newTab(),"account");
        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof MainActivity){
            this.context = context;
        }
    }


    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.txt_search){
            Intent intent  = new Intent(context, SearchActivity.class);
            startActivity(intent);
        }
    }
}
