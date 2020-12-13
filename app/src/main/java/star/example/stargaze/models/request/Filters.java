package star.example.stargaze.models.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Filters {
    @SerializedName("search")
    @Expose
    private String search;

    public Filters(String search) {
        this.search = search;
    }
}
