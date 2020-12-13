package star.example.stargaze.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import star.example.stargaze.R;
import star.example.stargaze.databinding.ActivityQueryBinding;
import star.example.stargaze.models.request.ContactBody;
import star.example.stargaze.utils.AppUtils;
import star.example.stargaze.utils.Validation;
import star.example.stargaze.view_presenter.QueryPresenter;
import com.google.android.material.snackbar.Snackbar;

public class QueryActivity extends AppCompatActivity implements QueryPresenter.QueryView, View.OnClickListener {
    private ActivityQueryBinding binding;
    private Context context;
    private View view;
    private Dialog dialog;
    private QueryPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_query);
        context = QueryActivity.this;
        AppUtils.setUpToolbar(this,binding.tool.toolbar,true,false,"HELP/FAQ");
        view = binding.getRoot();
        dialog =AppUtils.hideShowProgress(context);
        presenter = new QueryPresenter(this);
        binding.tvSendQueryBtn.setOnClickListener(this);
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
    public void onSuccess(String success) {
        Snackbar.make(view,"Your Query Has been sent.",Snackbar.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onFailure(Throwable t) {
        Snackbar.make(view,t.getMessage(),Snackbar.LENGTH_SHORT).show();

    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.tv_send_query_btn){
            setData();
        }
    }

    private void setData(){
        String name = binding.edUserName.getText().toString().trim();
        String email = binding.edEmail.getText().toString().trim();
        String subject = binding.edSubject.getText().toString().trim();
        String query = binding.edQuery.getText().toString().trim();

        if(name.isEmpty()){
            binding.edUserName.setError("Name is Empty!");
        }else if(email.isEmpty()){
            binding.edEmail.setError("Email is Empty!");
        }else if(!Validation.isValidEmail(email)){
            binding.edEmail.setError("Not a Valid Email!");
        }else if(subject.isEmpty()){
            binding.edSubject.setError("Subject is Empty!");
        }else if(query.isEmpty()){
            binding.edQuery.setError("Query is Empty!");
        }else {
            ContactBody contactBody = new ContactBody(name,email,subject,query);
            presenter.contactUs(contactBody,context);
        }
    }
}
