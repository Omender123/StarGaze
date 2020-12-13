package star.example.stargaze.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;

import star.example.stargaze.R;
import star.example.stargaze.databinding.ActivityPartialPaymentBinding;
import star.example.stargaze.models.PartialPayResp;
import star.example.stargaze.models.request.RazorCaptureBody;
import star.example.stargaze.models.request.RazorOrderBody;
import star.example.stargaze.models.request.RazorSaveDataBody;
import star.example.stargaze.models.response.CapturePaymentSuccessResp;
import star.example.stargaze.models.response.PaymentDataSaveResp;
import star.example.stargaze.models.response.RazorOrderResp;
import star.example.stargaze.models.response.WalletResp;
import star.example.stargaze.utils.AppUtils;
import star.example.stargaze.utils.Constants;
import star.example.stargaze.view_presenter.PartialPaymentPresenter;
import com.google.android.material.snackbar.Snackbar;
import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;

import org.json.JSONObject;

import java.text.DecimalFormat;

public class PartialPaymentActivity extends AppCompatActivity implements PartialPaymentPresenter.PartialPaymentView, View.OnClickListener, PaymentResultWithDataListener {
    private ActivityPartialPaymentBinding binding;
    private Context context;
    private View view;
    private Dialog dialog;
    private PartialPaymentPresenter presenter;
    private String orderId;
    private PaymentData paymentData1 =null;
    private int payableAmount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_partial_payment);
        context = PartialPaymentActivity.this;
        view = binding.getRoot();
        orderId = getIntent().getStringExtra("orderId");
        dialog = AppUtils.hideShowProgress(context);
        presenter = new PartialPaymentPresenter(this);
        double amount = getIntent().getDoubleExtra("payables",0.0);
        binding.tvPayableAmount.setText(Constants.RUPEE+""+amount);
        presenter.getWallet(context);
        binding.tvContinueBtn.setOnClickListener(this);
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
    }

    @Override
    public void onPartialPaymentSuccess(PartialPayResp payResp) {
        payableAmount = payResp.getTotalPayableAmount();
        System.out.println("partial amt"+payableAmount);
        if(payResp.getTotalPayableAmount()>0) {
            RazorOrderBody orderBody = new RazorOrderBody(payResp.getTotalPayableAmount(), orderId);
            presenter.razorOrder(orderBody, context);
        }
    }

    @Override
    public void onRazorOrderSuccess(RazorOrderResp razorOrderResp) {
        startPayment(razorOrderResp.getId());
    }

    @Override
    public void onRazorCaptureSuccess(CapturePaymentSuccessResp successResp) {
//        Snackbar.make(view,"Success",Snackbar.LENGTH_SHORT).show();

        if(paymentData1!=null) {
            RazorSaveDataBody dataBody = new RazorSaveDataBody(paymentData1.getOrderId(), paymentData1.getPaymentId()
                    , paymentData1.getSignature(), successResp.getAmount(), successResp.getMethod(), successResp.getStatus());
            System.out.println("order id " + orderId);
            presenter.savePaymentData(orderId, dataBody, context);
        }else {
            String str =" null";
            System.out.println("paymentData1  "+str);
        }
    }

    @Override
    public void onRazorDataSaved(PaymentDataSaveResp responseBody) {

        AppUtils.showMessageOKCancel("Payment Success \n" + "Event :" + responseBody.getEvent().getName() + "\nOrder No :" + responseBody.getOrderNumber(), this, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public void onGetWalletBalance(WalletResp walletResp) {
        DecimalFormat precision = new DecimalFormat("0.00");
        System.out.println("uuuuuu "+walletResp.getClosingBalance()/100);
        double wallet_amount = (double) walletResp.getClosingBalance();
        Spanned amount = Html.fromHtml(  "Use Your "+"<b>"+Constants.RUPEE+""+precision.format(wallet_amount)+" Wallet Money"+"</b>");

        binding.checkApplyWalletMoney.setText(amount);
    }

    @Override
    public void onFailure(Throwable t) {
        Snackbar.make(view,t.getMessage(),Snackbar.LENGTH_SHORT).show();

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.tv_continue_btn && binding.checkApplyWalletMoney.isChecked()){
            presenter.partialPay(orderId,context);
        }else {
            Snackbar.make(view,"Please select Wallet Money!",Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPaymentSuccess(String s, PaymentData paymentData) {

        this.paymentData1 = paymentData;
//        System.out.println("suus " + s + " " + paymentData.getExternalWallet() + " " + paymentData.getOrderId() + " pid " +
//                paymentData.getPaymentId() + " sig " + paymentData.getSignature() + " " + paymentData.getUserContact() + " " + paymentData.getUserEmail());
//        Toast.makeText(context, "Payment successful " + s, Toast.LENGTH_SHORT).show();

        RazorCaptureBody razorCaptureBody =  new RazorCaptureBody(payableAmount,paymentData.getOrderId(),
                paymentData.getPaymentId(),paymentData.getSignature());
        presenter.razorCapture(razorCaptureBody,context);
    }

    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {
        Snackbar.make(view,s,Snackbar.LENGTH_SHORT).show();

    }
}
