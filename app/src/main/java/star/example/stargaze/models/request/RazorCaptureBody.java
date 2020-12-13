package star.example.stargaze.models.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RazorCaptureBody {
    @SerializedName("amount")
    @Expose
    private Integer amount;
    @SerializedName("razorpay_order_id")
    @Expose
    private String razorpayOrderId;
    @SerializedName("razorpay_payment_id")
    @Expose
    private String razorpayPaymentId;
    @SerializedName("razorpay_signature")
    @Expose
    private String razorpaySignature;

    public RazorCaptureBody(Integer amount, String razorpayOrderId, String razorpayPaymentId, String razorpaySignature) {
        this.amount = amount;
        this.razorpayOrderId = razorpayOrderId;
        this.razorpayPaymentId = razorpayPaymentId;
        this.razorpaySignature = razorpaySignature;
    }
}
