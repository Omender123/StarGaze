package star.example.stargaze.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import star.example.stargaze.R;
import star.example.stargaze.models.ModelMessage;

import java.util.Collections;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ModelMessage>messages;
    private Context context;
    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;
    private int type=1;


    public ChatAdapter(List<ModelMessage> messages, Context context) {
        this.messages = messages;
        this.context = context;
    }
//    public int getItemViewType(int position) {
////        UserMessage message = (UserMessage) mMessageList.get(position);
//
//        if (type==1) {
//            // If the current user is the sender of the message
//            return VIEW_TYPE_MESSAGE_SENT;
//        } else {
//            // If some other user sent the message
//            return VIEW_TYPE_MESSAGE_RECEIVED;
//        }
//    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_msg_sent, parent, false);

//        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
//            view = LayoutInflater.from(parent.getContext())
//                    .inflate(R.layout.item_msg_sent, parent, false);
//            return new SentMessageHolder(view);
//        } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
//            view = LayoutInflater.from(parent.getContext())
//                    .inflate(R.layout.item_msg_recieved, parent, false);
//            return new ReceivedMessageHolder(view);
//        }

        return new SentMessageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ModelMessage message = messages.get(position);
        SentMessageHolder messageHolder = (SentMessageHolder)holder;
        messageHolder.sentText.setText(message.getMessage());
//        switch (holder.getItemViewType()) {
//            case VIEW_TYPE_MESSAGE_SENT:
//                ((SentMessageHolder) holder).bind(message);
//                break;
//            case VIEW_TYPE_MESSAGE_RECEIVED:
//                ((ReceivedMessageHolder) holder).bindReceivedMsg(message);
//        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

//    private class ReceivedMessageHolder extends RecyclerView.ViewHolder {
//        TextView receivedText, timeText, nameText;
//        ImageView profileImage;
//
//        ReceivedMessageHolder(View itemView) {
//            super(itemView);
//            receivedText = (TextView) itemView.findViewById(R.id.txt_msg_received);
//            timeText = (TextView) itemView.findViewById(R.id.text_message_time);
//        }
//        void bindReceivedMsg(ModelMessage msg){
//            receivedText.setText(msg.getMessage());
//        }
//    }

        private class SentMessageHolder extends RecyclerView.ViewHolder {
            TextView sentText, timeText;

            SentMessageHolder(View itemView) {
                super(itemView);

                sentText = (TextView) itemView.findViewById(R.id.txt_sent_msg);
                timeText = (TextView) itemView.findViewById(R.id.text_message_time);
            }

//            void bind(ModelMessage message) {
//                sentText.setText(message.getMessage());
//                // Format the stored timestamp into a readable String using method.
////                timeText.setText(Util.formatDateTime(message.getCreatedAt()));
//            }
        }
        public void updateData(ModelMessage modelMessage){
            messages.add(modelMessage);
            Collections.reverse(messages);
            notifyDataSetChanged();
//
        }
}
