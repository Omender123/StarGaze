package star.example.stargaze.fragments;

import android.app.Dialog;
import android.content.Context;
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
import star.example.stargaze.databinding.FragmentAboutUsBinding;
import star.example.stargaze.utils.AppUtils;

public class FragmentAboutUs extends Fragment {
    private FragmentAboutUsBinding binding;
    private Dialog dialog;
    private View view;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_about_us, container, false);
        dialog = AppUtils.hideShowProgress(context);
        view = binding.getRoot();
        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        if (context instanceof MainActivity)
            this.context = context;
        super.onAttach(context);
    }



//    @Override
//    public void onSuccess(ModelAboutUs aboutUs) {
//        Spanned text = Html.fromHtml("<b>"+"Terms & Condition : "+"</b>"+aboutUs.getTermCondition()+"<br/>"
//                +"<b>"+"Basic Information : "+"</b>"+aboutUs.getBasicInformation()+"<br/>"+"<b>"+"Privacy Policy : "+"</b>"+aboutUs.getPrivacyPolicy()+"<br/>");
//        binding.txtAboutUs.setText(text);
//    }


}
