package star.example.stargaze.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApplyCouponResp {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("isWalletSelected")
    @Expose
    private Boolean isWalletSelected;
    @SerializedName("user")
    @Expose
    private String user;
    @SerializedName("event")
    @Expose
    private String event;
    @SerializedName("totalPayableAmount")
    @Expose
    private Integer totalPayableAmount;
    @SerializedName("statusDetails")
    @Expose
    private List<StatusDetail> statusDetails = null;
    @SerializedName("orderNumber")
    @Expose
    private Integer orderNumber;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("discountCode")
    @Expose
    private DiscountCode discountCode;

    public String getId() {
        return id;
    }

    public Boolean getWalletSelected() {
        return isWalletSelected;
    }

    public String getUser() {
        return user;
    }

    public String getEvent() {
        return event;
    }

    public Integer getTotalPayableAmount() {
        return totalPayableAmount;
    }

    public List<StatusDetail> getStatusDetails() {
        return statusDetails;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public DiscountCode getDiscountCode() {
        return discountCode;
    }

    public static class StatusDetail {
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("date")
        @Expose
        private String date;

        public String getStatus() {
            return status;
        }

        public String getDate() {
            return date;
        }
    }

    public static class DiscountCode{
        @SerializedName("_id")
        @Expose
        private String id;
        @SerializedName("discountApplied")
        @Expose
        private DiscountApplied discountApplied;
        @SerializedName("userType")
        @Expose
        private String userType;
        @SerializedName("code")
        @Expose
        private String code;
        @SerializedName("validFrom")
        @Expose
        private String validFrom;
        @SerializedName("validTo")
        @Expose
        private String validTo;
        @SerializedName("discount")
        @Expose
        private Integer discount;

        public String getId() {
            return id;
        }

        public DiscountApplied getDiscountApplied() {
            return discountApplied;
        }

        public String getUserType() {
            return userType;
        }

        public String getCode() {
            return code;
        }

        public String getValidFrom() {
            return validFrom;
        }

        public String getValidTo() {
            return validTo;
        }

        public Integer getDiscount() {
            return discount;
        }

        public static class DiscountApplied{
            @SerializedName("code")
            @Expose
            private Boolean code;

            public Boolean getCode() {
                return code;
            }
        }
    }
}
