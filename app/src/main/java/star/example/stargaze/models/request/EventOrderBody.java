package star.example.stargaze.models.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EventOrderBody {
    @SerializedName("user")
    @Expose
    private String user;
    @SerializedName("event")
    @Expose
    private String event;


    public EventOrderBody(String user, String event) {
        this.user = user;
        this.event = event;
    }
}
