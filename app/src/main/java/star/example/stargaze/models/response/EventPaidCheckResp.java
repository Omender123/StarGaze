package star.example.stargaze.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EventPaidCheckResp {
    @SerializedName("result")
    @Expose
    private Boolean result;
    @SerializedName("msg")
    @Expose
    private String msg;

    public Boolean getResult() {
        return result;
    }

    public String getMsg() {
        return msg;
    }
}
