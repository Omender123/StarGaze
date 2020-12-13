package star.example.stargaze.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LoginRespBean {
    @SerializedName("result")
    @Expose
    private Result result;

    public Result getResult() {
        return result;
    }

    public static class Result{

        @SerializedName("accountLocked")
        @Expose
        private AccountLocked accountLocked;
        @SerializedName("profileImage")
        @Expose
        private String profileImage;
        @SerializedName("countryCode")
        @Expose
        private String countryCode;
        @SerializedName("isPhoneVerified")
        @Expose
        private Boolean isPhoneVerified;
        @SerializedName("active")
        @Expose
        private Boolean active;
        @SerializedName("role")
        @Expose
        private String role;
        @SerializedName("roleId")
        @Expose
        private Integer roleId;
        @SerializedName("category")
        @Expose
        private String category;
        @SerializedName("_id")
        @Expose
        private String id;
        @SerializedName("DOB")
        @Expose
        private String dOB;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("fcmToken")
        @Expose
        private String fcmToken;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("password")
        @Expose
        private String password;
        @SerializedName("phone")
        @Expose
        private String phone;
        @SerializedName("address")
        @Expose
        private List<Object> address = null;
        @SerializedName("referalCode")
        @Expose
        private String referalCode;
        @SerializedName("walletId")
        @Expose
        private String walletId;
        @SerializedName("createdAt")
        @Expose
        private String createdAt;
        @SerializedName("updatedAt")
        @Expose
        private String updatedAt;
        @SerializedName("jwtToken")
        @Expose
        private String jwtToken;

        public AccountLocked getAccountLocked() {
            return accountLocked;
        }

        public String getProfileImage() {
            return profileImage;
        }

        public String getCountryCode() {
            return countryCode;
        }

        public Boolean getPhoneVerified() {
            return isPhoneVerified;
        }

        public Boolean getActive() {
            return active;
        }

        public String getRole() {
            return role;
        }

        public Integer getRoleId() {
            return roleId;
        }

        public String getCategory() {
            return category;
        }

        public String getId() {
            return id;
        }

        public String getdOB() {
            return dOB;
        }

        public String getEmail() {
            return email;
        }

        public String getFcmToken() {
            return fcmToken;
        }

        public String getName() {
            return name;
        }

        public String getPassword() {
            return password;
        }

        public String getPhone() {
            return phone;
        }

        public List<Object> getAddress() {
            return address;
        }

        public String getReferalCode() {
            return referalCode;
        }

        public String getWalletId() {
            return walletId;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public String getJwtToken() {
            return jwtToken;
        }

        public  static class AccountLocked{
            @SerializedName("showReason")
            @Expose
            private Boolean showReason;
            @SerializedName("value")
            @Expose
            private Boolean value;

            public Boolean getShowReason() {
                return showReason;
            }

            public Boolean getValue() {
                return value;
            }
        }
    }
}
