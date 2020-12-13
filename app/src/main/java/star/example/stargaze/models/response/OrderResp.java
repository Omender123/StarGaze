package star.example.stargaze.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderResp {
    @SerializedName("result")
    @Expose
    private Result result;
    @SerializedName("message")
    @Expose
    private String message;

    public Result getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }

    public static  class Result{
        @SerializedName("_id")
        @Expose
        private String id;
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

        public String getId() {
            return id;
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
    }
}
