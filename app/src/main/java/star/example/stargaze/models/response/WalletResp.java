package star.example.stargaze.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WalletResp {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("userId")
    @Expose
    private UserId userId;
    @SerializedName("closingBalance")
    @Expose
    private Integer closingBalance;
    @SerializedName("transactions")
    @Expose
    private List<Transaction> transactions = null;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("allPaymentDetails")
    @Expose
    private AllPaymentDetails allPaymentDetails;
    @SerializedName("paymentStatus")
    @Expose
    private String paymentStatus;

    public String getId() {
        return id;
    }

    public UserId getUserId() {
        return userId;
    }

    public Integer getClosingBalance() {
        return closingBalance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public AllPaymentDetails getAllPaymentDetails() {
        return allPaymentDetails;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public static class UserId{
        @SerializedName("_id")
        @Expose
        private String id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("phone")
        @Expose
        private String phone;
        @SerializedName("email")
        @Expose
        private String email;

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getPhone() {
            return phone;
        }

        public String getEmail() {
            return email;
        }
    }

    public static class Transaction{
        @SerializedName("_id")
        @Expose
        private String id;
        @SerializedName("IncDec")
        @Expose
        private String incDec;
        @SerializedName("amount")
        @Expose
        private Integer amount;
        @SerializedName("date")
        @Expose
        private String date;
        @SerializedName("description")
        @Expose
        private String description;

        public String getId() {
            return id;
        }

        public String getIncDec() {
            return incDec;
        }

        public Integer getAmount() {
            return amount;
        }

        public String getDate() {
            return date;
        }

        public String getDescription() {
            return description;
        }
    }

    public static class AllPaymentDetails{
        @SerializedName("razorpay_order_id")
        @Expose
        private String razorpayOrderId;
        @SerializedName("transactionId")
        @Expose
        private String transactionId;
        @SerializedName("razorpay_signature")
        @Expose
        private String razorpaySignature;
        @SerializedName("amount")
        @Expose
        private Integer amount;
        @SerializedName("date")
        @Expose
        private Long date;
        @SerializedName("paymentType")
        @Expose
        private String paymentType;

        public String getRazorpayOrderId() {
            return razorpayOrderId;
        }

        public String getTransactionId() {
            return transactionId;
        }

        public String getRazorpaySignature() {
            return razorpaySignature;
        }

        public Integer getAmount() {
            return amount;
        }

        public Long getDate() {
            return date;
        }

        public String getPaymentType() {
            return paymentType;
        }
    }
}
