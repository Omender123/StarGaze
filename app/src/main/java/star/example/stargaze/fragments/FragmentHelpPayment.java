package star.example.stargaze.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import star.example.stargaze.R;
import star.example.stargaze.activities.QueryActivity;
import star.example.stargaze.adapters.ExpandableListAdapter;

import star.example.stargaze.databinding.FragmentHelpPaymentBinding;
import star.example.stargaze.models.response.FAQResp;
import star.example.stargaze.utils.AppUtils;
import star.example.stargaze.view_presenter.FAQPresenter;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FragmentHelpPayment extends Fragment implements View.OnClickListener, FAQPresenter.FAQView {
    private FragmentHelpPaymentBinding binding;
    private HashMap<String, String> listDataChild;
    private List<String> questions =null;
    private List<String>answers = null;
    private ExpandableListAdapter listAdapter;
    private FAQPresenter presenter;
    private View view;
    private Dialog dialog;
    private int lastExpandedPosition =-1;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_help_payment, container, false);
        view = binding.getRoot();
        dialog = AppUtils.hideShowProgress(getContext());
        presenter = new FAQPresenter(this);
        presenter.getFaq(getContext());

        binding.tvSendQuery.setOnClickListener(this);
        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.tv_send_query){
            Intent intent = new Intent(getContext(), QueryActivity.class);
            startActivity(intent);
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
        Snackbar.make(view,message,Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess(List<FAQResp> faqResp) {

        questions = new ArrayList<>();
        listDataChild = new HashMap<>();
        answers = new ArrayList<>();
        for (int i = 0; i < faqResp.size(); i++) {
            if(faqResp.get(i).getCategory().equalsIgnoreCase("Payment")) {
                questions.add(faqResp.get(i).getQuestion());
                answers.add(faqResp.get(i).getAnswer());
            }
        }
        if(questions.size()>0){
            for(int i =0;i<questions.size();i++){
                listDataChild.put(questions.get(i),answers.get(i));
            }
        }

        listAdapter = new ExpandableListAdapter(getContext(), questions, listDataChild);
        binding.expandableListView.setAdapter(listAdapter);


        binding.expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                return false;
            }
        });

        binding.expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int i) {
                if (lastExpandedPosition != -1
                        && i != lastExpandedPosition) {
                    binding.expandableListView.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = i;
            }
        });
    }

    @Override
    public void onFailure(Throwable t) {
        Snackbar.make(view,t.getMessage(),Snackbar.LENGTH_SHORT).show();

    }
}
