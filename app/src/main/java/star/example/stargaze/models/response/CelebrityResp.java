package star.example.stargaze.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CelebrityResp {

    @SerializedName("celebrity")
    @Expose
    private List<Celebrity> celebrity = null;
    @SerializedName("count")
    @Expose
    private Integer count;

    public List<Celebrity> getCelebrity() {
        return celebrity;
    }

    public Integer getCount() {
        return count;
    }

    public static class Celebrity{
        @SerializedName("_id")
        @Expose
        private String id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("event")
        @Expose
        private String event;
        @SerializedName("images")
        @Expose
        private List<Image> images = null;
        @SerializedName("createdAt")
        @Expose
        private String createdAt;
        @SerializedName("updatedAt")
        @Expose
        private String updatedAt;

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public String getEvent() {
            return event;
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
}
