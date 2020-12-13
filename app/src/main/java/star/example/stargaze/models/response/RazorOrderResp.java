package star.example.stargaze.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RazorOrderResp {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("entity")
    @Expose
    private String entity;
    @SerializedName("amount")
    @Expose
    private Integer amount;
    @SerializedName("amount_paid")
    @Expose
    private Integer amountPaid;
    @SerializedName("amount_due")
    @Expose
    private Integer amountDue;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("receipt")
    @Expose
    private String receipt;
    @SerializedName("offer_id")
    @Expose
    private Object offerId;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("attempts")
    @Expose
    private Integer attempts;
    @SerializedName("notes")
    @Expose
    private List<Object> notes = null;
    @SerializedName("created_at")
    @Expose
    private Integer createdAt;

    public String getId() {
        return id;
    }

    public String getEntity() {
        return entity;
    }

    public Integer getAmount() {
        return amount;
    }

    public Integer getAmountPaid() {
        return amountPaid;
    }

    public Integer getAmountDue() {
        return amountDue;
    }

    public String getCurrency() {
        return currency;
    }

    public String getReceipt() {
        return receipt;
    }

    public Object getOfferId() {
        return offerId;
    }

    public String getStatus() {
        return status;
    }

    public Integer getAttempts() {
        return attempts;
    }

    public List<Object> getNotes() {
        return notes;
    }

    public Integer getCreatedAt() {
        return createdAt;
    }
}
