package star.example.stargaze.activities;

import androidx.appcompat.app.AlertDialog;
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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import star.example.stargaze.R;
import star.example.stargaze.databinding.ActivityTaxBinding;

import star.example.stargaze.models.request.EventOrderBody;
import star.example.stargaze.models.request.RazorCaptureBody;
import star.example.stargaze.models.request.RazorOrderBody;
import star.example.stargaze.models.request.RazorSaveDataBody;
import star.example.stargaze.models.response.ApplyCouponResp;
import star.example.stargaze.models.response.EventResp;
import star.example.stargaze.models.response.OrderResp;
import star.example.stargaze.models.response.CapturePaymentSuccessResp;
import star.example.stargaze.models.response.PaymentDataSaveResp;
import star.example.stargaze.models.response.PrivacyResp;
import star.example.stargaze.models.response.RazorOrderResp;
import star.example.stargaze.models.response.WalletResp;
import star.example.stargaze.sharedPref.MyPreferences;
import star.example.stargaze.sharedPref.PrefConf;
import star.example.stargaze.utils.AppUtils;
import star.example.stargaze.view_presenter.BookEventPresenter;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

import okhttp3.ResponseBody;

public class TaxActivity extends AppCompatActivity implements BookEventPresenter.BookEventView, PaymentResultWithDataListener, View.OnClickListener {
    private ActivityTaxBinding binding;
    private Context context;
    private int tax;
    private double price;
    private View view;
    private Dialog dialog;
    private List<EventResp.Event> events;
    private double payableAmount;
    private double gstAmount;
    private int position;
    private double discountAmount;
    private BookEventPresenter presenter;
    private PaymentData paymentData1 = null;
    private String eventOrderId;
    private String orderIdForWallet;
    private boolean isWalletSelected = false;
    private String discountCode;
    private OrderResp orderResp = null;
    private int finalPayableAmount;
    private boolean isCoupanApplied = false;
    private ApplyCouponResp applyCouponResp = null;
    private double finalAmount;
    private boolean isRazorpay = false;
    private PrivacyResp privacy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tax);
        context = TaxActivity.this;
        position = getIntent().getIntExtra("pos", 0);
        view = binding.getRoot();
        discountCode = getIntent().getStringExtra("code");
        dialog = AppUtils.hideShowProgress(context);
        presenter = new BookEventPresenter(this);
        presenter.getAboutUs(context);
        setData();
//        EventOrderBody eventOrderBody = new EventOrderBody(MyPreferences.getInstance(context).getString(PrefConf.KEY_USER_ID,""),events.get(position).getId());
//        presenter.orderEvent(eventOrderBody,context);
        binding.txtPayBtn.setOnClickListener(this);
        binding.imgBackArrow.setOnClickListener(this);
       /* binding.tvPayWithWallet.setOnClickListener(this);
    */}

    private void setData() {
        Gson gson = new Gson();
        Type type = new TypeToken<List<EventResp.Event>>() {
        }.getType();
        events = gson.fromJson(MyPreferences.getInstance(this).getString(PrefConf.EVENTLISTSTR, ""), type);
        gstAmount = (events.get(position).getPrice() * 10) / 100;
        price = events.get(position).getPrice() - ((events.get(position).getPrice() * 10) / 100);
        payableAmount = price + gstAmount;
        binding.tvDiscountedAmount.setText("" + discountAmount);
        binding.tvGstAmount.setText("" + gstAmount);
        binding.tvActualPrice.setText("" + price);
        binding.tvPayableAmount.setText("" + payableAmount);
//        System.out.println("event id "+events.get(position).getId());


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
        if (isShow) {
            dialog.show();
        } else {
            dialog.dismiss();
        }
    }

    @Override
    public void onError(String message) {
        if (message.equalsIgnoreCase("Token Expired")) {
            AppUtils.alertMessage(this, "Your account is logged in to new device or your session is expired!");
        } else {
            Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
        }
        System.out.println("errrr " + message);
    }

    @Override
    public void onEventOrderSuccess(OrderResp resp) {
        eventOrderId = resp.getResult().getId();
        this.orderResp = resp;

        Snackbar.make(view, "Order created", Snackbar.LENGTH_SHORT).show();
        if (!discountCode.isEmpty()) {
            presenter.applyDiscountCode(resp.getResult().getId(), discountCode, resp.getResult().getUser(), context);

        } else {
            if (isRazorpay) {
                if (isCoupanApplied) {
                    RazorOrderBody orderBody = new RazorOrderBody(finalPayableAmount, orderResp.getResult().getId());
                    presenter.razorOrder(orderBody, context);
                } else {
                    RazorOrderBody orderBody = new RazorOrderBody(orderResp.getResult().getTotalPayableAmount(), orderResp.getResult().getId());
                    presenter.razorOrder(orderBody, context);
                }
            } else if (isWalletSelected) {
                presenter.getWallet(context);
            }
        }
//       else if(isWalletSelected){
//            orderIdForWallet = resp.getResult().getId();
//            presenter.sendWalletOtp(resp.getResult().getId(),isWalletSelected,context);
//        }else {
//            RazorOrderBody orderBody = new RazorOrderBody(resp.getResult().getTotalPayableAmount(), resp.getResult().getId());
//            presenter.razorOrder(orderBody, context);
//        }
    }

    @Override
    public void onRazorOrderSuccess(RazorOrderResp razorOrderResp) {
        startPayment(razorOrderResp.getId());
    }

    @Override
    public void onRazorCaptureSuccess(CapturePaymentSuccessResp successResp) {

//        Snackbar.make(view,"Success",Snackbar.LENGTH_SHORT).show();


        if (paymentData1 != null) {
            RazorSaveDataBody dataBody = new RazorSaveDataBody(paymentData1.getOrderId(), paymentData1.getPaymentId()
                    , paymentData1.getSignature(), successResp.getAmount(), successResp.getMethod(), successResp.getStatus());

//            System.out.println("order id " +orderResp.getResult().getId()+" rzorder "+paymentData1.getOrderId()+" pid "+paymentData1.getPaymentId()+" sig "+paymentData1.getSignature()+" amt "+successResp.getAmount()+" met "+successResp.getMethod()+" sts "+successResp.getStatus());

            presenter.savePaymentData(orderResp.getResult().getId(), dataBody, context);
        } else {
            String str = " null";
            System.out.println("paymentData1  " + str);
        }

    }

    @Override
    public void onRazorDataSaved(PaymentDataSaveResp responseBody) {
        AppUtils.showMessageOKCancel("Payment Success\n" + "Event :" + responseBody.getEvent().getName() + "\nOrder No :" + responseBody.getOrderNumber(), this, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public void onWalletOtpSuccess(ResponseBody responseBody) {
        Intent intent = new Intent(context, WalletVerifyOTPActivity.class);
        intent.putExtra("orderId", eventOrderId);
        startActivity(intent);
    }

    @Override
    public void onCouponApplied(ApplyCouponResp applyCouponResp) {
        isCoupanApplied = true;
        this.applyCouponResp = applyCouponResp;
        finalPayableAmount = applyCouponResp.getTotalPayableAmount();
        gstAmount = (double) (applyCouponResp.getTotalPayableAmount() * 10) / 100;
        price = applyCouponResp.getTotalPayableAmount() - ((applyCouponResp.getTotalPayableAmount() * 10) / 100);
        payableAmount = price + gstAmount;
        binding.tvDiscountedAmount.setText(" - " + (double) applyCouponResp.getDiscountCode().getDiscount());
        binding.tvGstAmount.setText("+" + gstAmount);
        binding.tvActualPrice.setText("" + (double) orderResp.getResult().getTotalPayableAmount());
        binding.tvPayableAmount.setText("" + (double) applyCouponResp.getTotalPayableAmount());

//         if(isWalletSelected){
//            orderIdForWallet = orderResp.getResult().getId();
//            presenter.sendWalletOtp(orderResp.getResult().getId(),isWalletSelected,context);
//        }else {
//            RazorOrderBody orderBody = new RazorOrderBody(applyCouponResp.getTotalPayableAmount(), orderResp.getResult().getId());
//            presenter.razorOrder(orderBody, context);
//        }
        if (isRazorpay) {
            if (isCoupanApplied) {
                RazorOrderBody orderBody = new RazorOrderBody(finalPayableAmount, orderResp.getResult().getId());
                presenter.razorOrder(orderBody, context);
            } else {
                RazorOrderBody orderBody = new RazorOrderBody(orderResp.getResult().getTotalPayableAmount(), orderResp.getResult().getId());
                presenter.razorOrder(orderBody, context);
            }
        } else if (isWalletSelected) {
            presenter.getWallet(context);
        }
    }

    @Override
    public void onGetWalletBalance(WalletResp walletResp) {
        if (isCoupanApplied) {
            finalAmount = finalPayableAmount;
        } else {
            finalAmount = payableAmount;
        }

        if (walletResp.getClosingBalance() >= orderResp.getResult().getTotalPayableAmount()) {
            presenter.sendWalletOtp(orderResp.getResult().getId(), isWalletSelected, context);
        } else {
//            presenter.partialPay(orderResp.getResult().getId(),context);
            Intent intent = new Intent(context, PartialPaymentActivity.class);
            intent.putExtra("orderId", orderResp.getResult().getId());
            intent.putExtra("payables", finalAmount);
            startActivity(intent);
        }


    }

    @Override
    public void onPrivacyResult(List<PrivacyResp> privacyRespList) {
        for(int i =0;i<privacyRespList.size();i++){
            PrivacyResp privacyResp = privacyRespList.get(i);
            if(privacyResp.getActive()){
                this.privacy = privacyResp;
            }
        }
    }

    @Override
    public void onFailure(Throwable t) {
        Snackbar.make(view, t.getMessage(), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onPaymentSuccess(String s, PaymentData paymentData) {
//        System.out.println("jsonobj " + paymentData.getData().toString());
        this.paymentData1 = paymentData;
        System.out.println("suus order " + orderResp.getResult().getId());
        System.out.println("suus " + s + " " + paymentData.getExternalWallet() + " " + paymentData.getOrderId() + " pid " + paymentData.getPaymentId() + " sig " + paymentData.getSignature() + " " + paymentData.getUserContact() + " " + paymentData.getUserEmail());

        Toast.makeText(context, "Payment successful " + s, Toast.LENGTH_SHORT).show();
        int amount = (int) payableAmount;
        RazorCaptureBody razorCaptureBody = new RazorCaptureBody(amount, paymentData.getOrderId(),
                paymentData.getPaymentId(), paymentData.getSignature());
        presenter.razorCapture(razorCaptureBody, context);
    }

    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {
        Snackbar.make(view, s, Snackbar.LENGTH_SHORT).show();
//        System.out.println("jsonobj " + paymentData.getData().toString());

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back_arrow:
                onBackPressed();
                break;
            case R.id.txt_pay_btn:
                isRazorpay = true;
                privacyDialog(context, false, true);
                break;
/*

            case R.id.tv_pay_with_wallet:
                isWalletSelected = true;
                privacyDialog(context, isWalletSelected, false);
*/

//                EventOrderBody eventOrderBody1 = new EventOrderBody(MyPreferences.getInstance(context).getString(PrefConf.KEY_USER_ID,""),events.get(position).getId());
//                presenter.orderEvent(eventOrderBody1,context);

             //   break;
        }
    }

    private AlertDialog alertDialog;

    private void privacyDialog(final Context context, boolean isWallet, boolean isRazorpay) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context, R.style.CustomDialog);
        LayoutInflater inflater = ((AppCompatActivity) context).getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.privacy_policy_layout, null);

        dialogBuilder.setView(dialogView);
        CheckBox privacy_check = dialogView.findViewById(R.id.privacy_check);
        TextView payNow = dialogView.findViewById(R.id.txt_book_show_btn);
        TextView cancel_dialog_btn = dialogView.findViewById(R.id.txt_cancel_dialog_btn);
        TextView tv_terms_conditions = dialogView.findViewById(R.id.tv_terms_conditions);

        if(privacy!=null){
            Spanned termsCondition = Html.fromHtml(
                    "Terms And Conditions : " +privacy.getTermCondition()+ "<br/>" + "Basic Info : <font color=#657788>" +
                            privacy.getBasicInformation() + "</font><br/>" + "Privacy Policy : <font color=#657788>"+privacy.getPrivacyPolicy()+"</font>");
            tv_terms_conditions.setText(termsCondition);
        }
        payNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (privacy_check.isChecked()) {
                    alertDialog.dismiss();
                    EventOrderBody eventOrderBody = new EventOrderBody(MyPreferences.getInstance(context).getString(PrefConf.KEY_USER_ID, ""), events.get(position).getId());
                    presenter.orderEvent(eventOrderBody, context);

                } else {
                    Snackbar.make(dialogView, "Please Accept the Terms & Conditions !", Snackbar.LENGTH_LONG).show();
                }
            }
        });
        cancel_dialog_btn.setOnClickListener(view -> alertDialog.dismiss());

        alertDialog = dialogBuilder.create();

        alertDialog.show();

    }
}
