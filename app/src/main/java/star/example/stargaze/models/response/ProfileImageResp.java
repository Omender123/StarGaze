package star.example.stargaze.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfileImageResp {

    @SerializedName("profileImage")
    @Expose
    private String profileImage;

    public String getProfileImage() {
        return profileImage;
    }
}
