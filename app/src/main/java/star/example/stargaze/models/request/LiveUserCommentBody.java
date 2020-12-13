package star.example.stargaze.models.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LiveUserCommentBody {
    @SerializedName("user")
    @Expose
    String userId;
    @SerializedName("comment")
    @Expose
    String comment;

    public LiveUserCommentBody(String userId, String comment) {
        this.userId = userId;
        this.comment = comment;
    }
}
