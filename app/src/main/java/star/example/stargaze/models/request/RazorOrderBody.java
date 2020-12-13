package star.example.stargaze.models.request;

import com.google.gson.annotations.SerializedName;

public class RazorOrderBody {
    @SerializedName("amount")
    private Integer amount;
    @SerializedName("orderId")
    private String orderId;

    public RazorOrderBody(Integer amount, String orderId) {
        this.amount = amount;
        this.orderId = orderId;
    }
}
