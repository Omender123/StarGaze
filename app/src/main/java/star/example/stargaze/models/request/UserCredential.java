package star.example.stargaze.models.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserCredential {
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("fcmToken")
    @Expose
    private String fcmToken;

    @SerializedName("loginType")
    @Expose
    private String loginType;

    public UserCredential(String phone, String password,String fcmToken,String loginType) {
        this.phone = phone;
        this.password = password;
        this.fcmToken = fcmToken;
        this.loginType = loginType;
    }
}
