package star.example.stargaze.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CapturePaymentSuccessResp {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("entity")
    @Expose
    private String entity;
    @SerializedName("amount")
    @Expose
    private Integer amount;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("invoice_id")
    @Expose
    private Object invoiceId;
    @SerializedName("international")
    @Expose
    private Boolean international;
    @SerializedName("method")
    @Expose
    private String method;
    @SerializedName("amount_refunded")
    @Expose
    private Integer amountRefunded;
    @SerializedName("refund_status")
    @Expose
    private Object refundStatus;
    @SerializedName("captured")
    @Expose
    private Boolean captured;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("card_id")
    @Expose
    private String cardId;
    @SerializedName("card")
    @Expose
    private Card card;
    @SerializedName("bank")
    @Expose
    private Object bank;
    @SerializedName("wallet")
    @Expose
    private Object wallet;
    @SerializedName("vpa")
    @Expose
    private Object vpa;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("contact")
    @Expose
    private String contact;
    @SerializedName("notes")
    @Expose
    private List<Object> notes = null;
    @SerializedName("fee")
    @Expose
    private Integer fee;
    @SerializedName("tax")
    @Expose
    private Integer tax;
    @SerializedName("error_code")
    @Expose
    private Object errorCode;
    @SerializedName("error_description")
    @Expose
    private Object errorDescription;
    @SerializedName("error_source")
    @Expose
    private Object errorSource;
    @SerializedName("error_step")
    @Expose
    private Object errorStep;
    @SerializedName("error_reason")
    @Expose
    private Object errorReason;
    @SerializedName("acquirer_data")
    @Expose
    private AcquirerData acquirerData;
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

    public String getCurrency() {
        return currency;
    }

    public String getStatus() {
        return status;
    }

    public String getOrderId() {
        return orderId;
    }

    public Object getInvoiceId() {
        return invoiceId;
    }

    public Boolean getInternational() {
        return international;
    }

    public String getMethod() {
        return method;
    }

    public Integer getAmountRefunded() {
        return amountRefunded;
    }

    public Object getRefundStatus() {
        return refundStatus;
    }

    public Boolean getCaptured() {
        return captured;
    }

    public String getDescription() {
        return description;
    }

    public String getCardId() {
        return cardId;
    }

    public Card getCard() {
        return card;
    }

    public Object getBank() {
        return bank;
    }

    public Object getWallet() {
        return wallet;
    }

    public Object getVpa() {
        return vpa;
    }

    public String getEmail() {
        return email;
    }

    public String getContact() {
        return contact;
    }

    public List<Object> getNotes() {
        return notes;
    }

    public Integer getFee() {
        return fee;
    }

    public Integer getTax() {
        return tax;
    }

    public Object getErrorCode() {
        return errorCode;
    }

    public Object getErrorDescription() {
        return errorDescription;
    }

    public Object getErrorSource() {
        return errorSource;
    }

    public Object getErrorStep() {
        return errorStep;
    }

    public Object getErrorReason() {
        return errorReason;
    }

    public AcquirerData getAcquirerData() {
        return acquirerData;
    }

    public Integer getCreatedAt() {
        return createdAt;
    }

    public static class Card{
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("entity")
        @Expose
        private String entity;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("last4")
        @Expose
        private String last4;
        @SerializedName("network")
        @Expose
        private String network;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("issuer")
        @Expose
        private Object issuer;
        @SerializedName("international")
        @Expose
        private Boolean international;
        @SerializedName("emi")
        @Expose
        private Boolean emi;
        @SerializedName("global_fingerprint")
        @Expose
        private String globalFingerprint;
        @SerializedName("sub_type")
        @Expose
        private String subType;

        public String getId() {
            return id;
        }

        public String getEntity() {
            return entity;
        }

        public String getName() {
            return name;
        }

        public String getLast4() {
            return last4;
        }

        public String getNetwork() {
            return network;
        }

        public String getType() {
            return type;
        }

        public Object getIssuer() {
            return issuer;
        }

        public Boolean getInternational() {
            return international;
        }

        public Boolean getEmi() {
            return emi;
        }

        public String getGlobalFingerprint() {
            return globalFingerprint;
        }

        public String getSubType() {
            return subType;
        }
    }

    public static class AcquirerData{
        @SerializedName("auth_code")
        @Expose
        private String authCode;

        public String getAuthCode() {
            return authCode;
        }
    }
}
