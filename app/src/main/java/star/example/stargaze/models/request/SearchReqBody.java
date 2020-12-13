package star.example.stargaze.models.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchReqBody {
    @SerializedName("filters")
    @Expose
    private Filters filters;

    public SearchReqBody(Filters filters) {
        this.filters = filters;
    }
}
