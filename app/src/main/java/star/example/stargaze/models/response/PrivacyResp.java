package star.example.stargaze.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PrivacyResp {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("termCondition")
    @Expose
    private String termCondition;
    @SerializedName("basicInformation")
    @Expose
    private String basicInformation;
    @SerializedName("privacyPolicy")
    @Expose
    private String privacyPolicy;
    @SerializedName("active")
    @Expose
    private Boolean active;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;

    public String getId() {
        return id;
    }

    public String getTermCondition() {
        return termCondition;
    }

    public String getBasicInformation() {
        return basicInformation;
    }

    public String getPrivacyPolicy() {
        return privacyPolicy;
    }

    public Boolean getActive() {
        return active;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }
}
