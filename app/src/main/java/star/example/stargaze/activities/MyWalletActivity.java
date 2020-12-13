package star.example.stargaze.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import star.example.stargaze.R;
import star.example.stargaze.databinding.FragmentWalletBinding;
import star.example.stargaze.models.request.RazorCaptureBody;
import star.example.stargaze.models.request.WalletAmountBody;
import star.example.stargaze.models.request.WalletSaveBody;
import star.example.stargaze.models.response.AddWalletDataResp;
import star.example.stargaze.models.response.CapturePaymentSuccessResp;
import star.example.stargaze.models.response.WalletCreateResp;
import star.example.stargaze.utils.AppUtils;
import star.example.stargaze.view_presenter.WalletPresenter;
import com.google.android.material.snackbar.Snackbar;
import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;

import org.json.JSONObject;

public class MyWalletActivity extends AppCompatActivity implements View.OnClickListener, WalletPresenter.WalletView,
        PaymentResultWithDataListener {
    private FragmentWalletBinding binding;
    private Context context;
    private Dialog dialog;
    private View view;
   private WalletPresenter presenter;
   private PaymentData paymentData1;
   private int payableAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding = DataBindingUtil.setContentView(this,R.layout.fragment_wallet);
       context = MyWalletActivity.this;
       view = binding.getRoot();
       presenter = new WalletPresenter(this);
       dialog = AppUtils.hideShowProgress(context);

       /*binding.tvAddMoney.setOnClickListener(this);
*/
//       presenter.getWallet(context);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            /*case R.id.tv_add_money:
                createRazorWallet();
                break;*/
        }

    }

    private void createRazorWallet(){
        WalletAmountBody amountBody = new WalletAmountBody(500);
        presenter.walletRazorOrder(amountBody,context);
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
            AppUtils.alertMessage(this,"Your account is logged in to new device or your session is expired!");
        }else {
            Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
        }
        System.out.println("errrr "+message);
    }

    @Override
    public void onRazorWalletOrderSuccess(WalletCreateResp resp) {
        startPayment(resp.getId());
    }

    @Override
    public void onRazorPaymentCaptureSuccess(CapturePaymentSuccessResp successResp) {

        WalletSaveBody dataBody = new WalletSaveBody(paymentData1.getOrderId(), paymentData1.getPaymentId()
                ,paymentData1.getSignature(),successResp.getAmount(),successResp.getMethod(),successResp.getStatus());

        presenter.savePaymentData(dataBody,context);
    }

    @Override
    public void onDataSaved(AddWalletDataResp savedData) {
        int balance = savedData.getClosingBalance()/100;
            binding.tvWalletAmount.setText(""+balance);
    }

//    @Override
//    public void onWalletResult(WalletResp walletResp) {
//        double balance = walletResp.getClosingBalance()/100;
//        binding.tvWalletAmount.setText(""+balance);
//        binding.walletHistoryRecycler.setLayoutManager(new GridLayoutManager(context,1));
//        binding.walletHistoryRecycler.setAdapter(new WalletHistoryAdapter(walletResp.getTransactions(),context));
//    }


    @Override
    public void onFailure(Throwable t) {
        Snackbar.make(view,t.getMessage(),Snackbar.LENGTH_SHORT).show();
        System.out.println("errrr "+t.getMessage());
    }

    public void startPayment(String razorPayOrderId) {

        /**
         * Instantiate Checkout
         */
        Checkout checkout = new Checkout();
        checkout.setKeyID(getResources().getString(R.string.razor_api_key));

        /**
         * Set your logo here
         */
//        checkout.setImage(R.drawable.logo);

        /**
         * Reference to current activity
         */
        final Activity activity = this;

        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {
            JSONObject options = new JSONObject();

            /**
             * Merchant Name
             * eg: ACME Corp || HasGeek etc.
             */
            options.put("name", "Merchant Name");

            /**
             * Description can be anything
             * eg: Reference No. #123123 - This order number is passed by you for your internal reference. This is not the `razorpay_order_id`.
             *     Invoice Payment
             *     etc.
             */
            options.put("description", "Test order");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("order_id", razorPayOrderId);
            options.put("currency", "INR");

            /**
             * Amount is always passed in currency subunits
             * Eg: "500" = INR 5.00
             */
//            options.put("amount", "500");

            checkout.open(activity, options);
        } catch (Exception e) {
            Log.e("TAG", "Error in starting Razorpay Checkout", e);
        }
    }

    @Override
    public void onPaymentSuccess(String s, PaymentData paymentData) {
        System.out.println("jsonobj " + paymentData.getData().toString());
        this.paymentData1 = paymentData;
        System.out.println("suus " + s + " " + paymentData.getExternalWallet() + " " + paymentData.getOrderId() + " pid " +
                paymentData.getPaymentId() + " sig " + paymentData.getSignature() + " " + paymentData.getUserContact() + " " + paymentData.getUserEmail());
        int amount =(int) payableAmount;
        RazorCaptureBody razorCaptureBody =  new RazorCaptureBody(amount,paymentData.getOrderId(),
                paymentData.getPaymentId(),paymentData.getSignature());
        presenter.razorCapture(razorCaptureBody,context);
    }

    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {
        Snackbar.make(view,s,Snackbar.LENGTH_SHORT).show();
    }
}
