package star.example.stargaze.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import star.example.stargaze.R;
import star.example.stargaze.models.response.WalletResp;
import star.example.stargaze.utils.AppUtils;
import star.example.stargaze.utils.Constants;

import java.text.DecimalFormat;
import java.util.List;

public class WalletHistoryAdapter extends RecyclerView.Adapter<WalletHistoryAdapter.TransactionViewHolder> {
    private List<WalletResp.Transaction>transactions;
    private Context context;
    private DecimalFormat precesion;

    public WalletHistoryAdapter(List<WalletResp.Transaction> transactions, Context context) {
        this.transactions = transactions;
        this.context = context;
        precesion = new DecimalFormat("0.00");
    }

    @NonNull
    @Override
    public WalletHistoryAdapter.TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wallet_history_row,parent,false);
        return new TransactionViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull WalletHistoryAdapter.TransactionViewHolder holder, int position) {
            double amount =transactions.get(position).getAmount();
            holder.tv_transaction_amount.setText(Constants.RUPEE+precesion.format(amount));
            holder.tv_transaction_type.setText(transactions.get(position).getDescription());
            holder.tv_transaction_date.setText(AppUtils.getDate(transactions.get(position).getDate()));
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public class TransactionViewHolder extends RecyclerView.ViewHolder {
        TextView tv_transaction_type,tv_transaction_date,tv_transaction_amount;
        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_transaction_type = itemView.findViewById(R.id.tv_transaction_type);
            tv_transaction_date = itemView.findViewById(R.id.tv_transaction_date);
            tv_transaction_amount = itemView.findViewById(R.id.tv_transaction_amount);
        }
    }
}
