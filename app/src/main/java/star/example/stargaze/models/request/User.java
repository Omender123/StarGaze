package star.example.stargaze.models.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("parentReferalcode")
    @Expose
    private String parentReferalcode;
    @SerializedName("DOB")
    @Expose
    private String dob;
    @SerializedName("fcmToken")
    @Expose
    private String fcmToken;
    @SerializedName("loginType")
    @Expose
    private String loginType;


    public User(String name, String phone, String email, String password,String parentReferralCode,String dob,String fcmToken,String loginType) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.parentReferalcode =parentReferralCode;
        this.dob = dob;
        this.fcmToken = fcmToken;
        this.loginType = loginType;
    }
}
