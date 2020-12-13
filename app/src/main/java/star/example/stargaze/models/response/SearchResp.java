package star.example.stargaze.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchResp {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("eventStreamInfo")
    @Expose
    private List<Object> eventStreamInfo = null;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("artist")
    @Expose
    private String artist;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("eventType")
    @Expose
    private String eventType;
    @SerializedName("startDate")
    @Expose
    private String startDate;
    @SerializedName("endDate")
    @Expose
    private String endDate;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("images")
    @Expose
    private List<Image> images = null;
    @SerializedName("comments")
    @Expose
    private List<Object> comments = null;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;

    public String getId() {
        return id;
    }

    public List<Object> getEventStreamInfo() {
        return eventStreamInfo;
    }

    public String getName() {
        return name;
    }

    public String getArtist() {
        return artist;
    }

    public String getDescription() {
        return description;
    }

    public Integer getPrice() {
        return price;
    }

    public String getEventType() {
        return eventType;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getUrl() {
        return url;
    }

    public List<Image> getImages() {
        return images;
    }

    public List<Object> getComments() {
        return comments;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public  static  class Image{
        @SerializedName("_id")
        @Expose
        private String id;
        @SerializedName("url")
        @Expose
        private String url;

        public String getId() {
            return id;
        }

        public String getUrl() {
            return url;
        }
    }
}
