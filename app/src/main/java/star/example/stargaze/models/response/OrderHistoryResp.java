package star.example.stargaze.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderHistoryResp {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("event")
    @Expose
    private Event event;
    @SerializedName("user")
    @Expose
    private User user;
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
    @SerializedName("allPaymentDetails")
    @Expose
    private AllPaymentDetails allPaymentDetails;
    @SerializedName("paymentStatus")
    @Expose
    private String paymentStatus;

    public String getId() {
        return id;
    }

    public Event getEvent() {
        return event;
    }

    public User getUser() {
        return user;
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

    public AllPaymentDetails getAllPaymentDetails() {
        return allPaymentDetails;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public  static class Event{
        @SerializedName("_id")
        @Expose
        private String id;
        @SerializedName("eventStreamInfo")
        @Expose
        private List<Object> eventStreamInfo = null;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("artist")
        @Expose
        private String artist;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("price")
        @Expose
        private Integer price;
        @SerializedName("eventType")
        @Expose
        private String eventType;
        @SerializedName("startDate")
        @Expose
        private String startDate;
        @SerializedName("endDate")
        @Expose
        private String endDate;
        @SerializedName("url")
        @Expose
        private String url;
        @SerializedName("images")
        @Expose
        private List<Object> images = null;
        @SerializedName("comments")
        @Expose
        private List<Object> comments = null;
        @SerializedName("createdAt")
        @Expose
        private String createdAt;
        @SerializedName("updatedAt")
        @Expose
        private String updatedAt;

        public String getId() {
            return id;
        }

        public List<Object> getEventStreamInfo() {
            return eventStreamInfo;
        }

        public String getName() {
            return name;
        }

        public String getArtist() {
            return artist;
        }

        public String getDescription() {
            return description;
        }

        public Integer getPrice() {
            return price;
        }

        public String getEventType() {
            return eventType;
        }

        public String getStartDate() {
            return startDate;
        }

        public String getEndDate() {
            return endDate;
        }

        public String getUrl() {
            return url;
        }

        public List<Object> getImages() {
            return images;
        }

        public List<Object> getComments() {
            return comments;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }
    }


    public static class User{
        @SerializedName("_id")
        @Expose
        private String id;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("phone")
        @Expose
        private String phone;

        public String getId() {
            return id;
        }

        public String getEmail() {
            return email;
        }

        public String getName() {
            return name;
        }

        public String getPhone() {
            return phone;
        }
    }


    public static class StatusDetail{
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
