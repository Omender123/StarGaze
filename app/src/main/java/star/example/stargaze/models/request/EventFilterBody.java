package star.example.stargaze.models.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EventFilterBody {
    @SerializedName("filters")
    @Expose
    private Filters filters;
    @SerializedName("sort")
    @Expose
    private Sort sort;

    public EventFilterBody(Filters filters, Sort sort) {
        this.filters = filters;
        this.sort = sort;
    }
}
