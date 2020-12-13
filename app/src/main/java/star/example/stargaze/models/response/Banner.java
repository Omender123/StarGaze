package star.example.stargaze.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Banner {
    @SerializedName("active")
    @Expose
    private Boolean active;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("bannerFor")
    @Expose
    private String bannerFor;
    @SerializedName("images")
    @Expose
    private List<Image> images = null;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;

    public Boolean getActive() {
        return active;
    }

    public String getId() {
        return id;
    }

    public String getBannerFor() {
        return bannerFor;
    }

    public List<Image> getImages() {
        return images;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public static class Image{
        @SerializedName("_id")
        @Expose
        private String id;
        @SerializedName("level")
        @Expose
        private String level;
        @SerializedName("url")
        @Expose
        private String url;

        public String getId() {
            return id;
        }

        public String getLevel() {
            return level;
        }

        public String getUrl() {
            return url;
        }
    }
}
