package star.example.stargaze.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProfileUpdateResp {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("accountLocked")
    @Expose
    private AccountLocked accountLocked;
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
    @SerializedName("orderNo")
    @Expose
    private Integer orderNo;
    @SerializedName("isWalletSelected")
    @Expose
    private Boolean isWalletSelected;
    @SerializedName("email")
    @Expose
    private String email;
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
    @SerializedName("walletId")
    @Expose
    private String walletId;

    public String getId() {
        return id;
    }

    public AccountLocked getAccountLocked() {
        return accountLocked;
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

    public Integer getOrderNo() {
        return orderNo;
    }

    public Boolean getWalletSelected() {
        return isWalletSelected;
    }

    public String getEmail() {
        return email;
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

    public String getWalletId() {
        return walletId;
    }

    public static class AccountLocked{
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
