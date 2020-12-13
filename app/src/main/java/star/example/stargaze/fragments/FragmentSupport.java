package star.example.stargaze.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import star.example.stargaze.R;
import star.example.stargaze.adapters.ChatAdapter;
import star.example.stargaze.databinding.FragmentSupportBinding;
import star.example.stargaze.models.ModelMessage;

import java.util.ArrayList;
import java.util.List;

public class FragmentSupport extends Fragment implements View.OnClickListener {
    private FragmentSupportBinding binding;
    private String textMsg;
    private List<ModelMessage>messageList;
    private ChatAdapter chatAdapter;
    private LinearLayoutManager layoutManager;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_support,container,false);
        messageList = new ArrayList<>();
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, true);
        layoutManager.setSmoothScrollbarEnabled(true);
        binding.recyclerMsgList.setLayoutManager(layoutManager);
        chatAdapter = new ChatAdapter(messageList,getContext());
        binding.recyclerMsgList.setAdapter(chatAdapter);
//        setMessage();
       binding.sendMsgBtn.setOnClickListener(this);
        return binding.getRoot();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.send_msg_btn){
            String msg = binding.editChatBox.getText().toString();
            ModelMessage message = new ModelMessage(msg);
            chatAdapter.updateData(message);
            binding.editChatBox.getText().clear();
        }
    }
}
