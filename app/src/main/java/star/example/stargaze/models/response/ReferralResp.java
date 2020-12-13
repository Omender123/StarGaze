package star.example.stargaze.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReferralResp {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("childReferalcode")
    @Expose
    private List<Object> childReferalcode = null;
    @SerializedName("amount")
    @Expose
    private Integer amount;
    @SerializedName("user")
    @Expose
    private String user;
    @SerializedName("myReferalcode")
    @Expose
    private String myReferalcode;
    @SerializedName("parentReferalcode")
    @Expose
    private String parentReferalcode;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;


    public String getId() {
        return id;
    }

    public List<Object> getChildReferalcode() {
        return childReferalcode;
    }

    public Integer getAmount() {
        return amount;
    }

    public String getUser() {
        return user;
    }

    public String getMyReferalcode() {
        return myReferalcode;
    }

    public String getParentReferalcode() {
        return parentReferalcode;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }
}
