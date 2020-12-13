package star.example.stargaze.models.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WalletSaveBody {
    @SerializedName("razorpay_order_id")
    @Expose
    private String razorpayOrderId;
    @SerializedName("razorpay_payment_id")
    @Expose
    private String razorpayPaymentId;
    @SerializedName("razorpay_signature")
    @Expose
    private String razorpaySignature;
    @SerializedName("payedAmount")
    @Expose
    private Integer payedAmount;
    @SerializedName("paymentType")
    @Expose
    private String paymentType;
    @SerializedName("status")
    @Expose
    private String status;

    public WalletSaveBody(String razorpayOrderId, String razorpayPaymentId, String razorpaySignature, Integer payedAmount, String paymentType, String status) {
        this.razorpayOrderId = razorpayOrderId;
        this.razorpayPaymentId = razorpayPaymentId;
        this.razorpaySignature = razorpaySignature;
        this.payedAmount = payedAmount;
        this.paymentType = paymentType;
        this.status = status;
    }
}
