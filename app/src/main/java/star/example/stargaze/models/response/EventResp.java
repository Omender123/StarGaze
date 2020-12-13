package star.example.stargaze.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EventResp {
    @SerializedName("events")
    @Expose
    private List<Event> events = null;
    @SerializedName("count")
    @Expose
    private Integer count;

    public List<Event> getEvents() {
        return events;
    }

    public Integer getCount() {
        return count;
    }

    public static class Event{
        @SerializedName("_id")
        @Expose
        private String id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("artist")
        @Expose
        private String artist;
        @SerializedName("artistSocialAccount")
        @Expose
        private String artistSocialAccount;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("introUrl")
        @Expose
        private String introUrl;
        @SerializedName("category")
        @Expose
        private String category;
        @SerializedName("url")
        @Expose
        private String url;
        @SerializedName("eventRating")
        @Expose
        private Integer eventRating;
        @SerializedName("averageRating")
        @Expose
        private Integer averageRating;
        @SerializedName("eventStreamInfo")
        @Expose
        private List<Object> eventStreamInfo = null;
        @SerializedName("price")
        @Expose
        private double price;
        @SerializedName("eventType")
        @Expose
        private String eventType;
        @SerializedName("startDate")
        @Expose
        private String startDate;
        @SerializedName("endDate")
        @Expose
        private String endDate;
        @SerializedName("images")
        @Expose
        private List<Image> images = null;
        @SerializedName("comments")
        @Expose
        private List<Comment> comments = null;
        @SerializedName("eventUserPaymentStatus")
        @Expose
        private List<Object> eventUserPaymentStatus = null;
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

        public String getArtist() {
            return artist;
        }

        public String getArtistSocialAccount() {
            return artistSocialAccount;
        }

        public String getDescription() {
            return description;
        }

        public String getIntroUrl() {
            return introUrl;
        }

        public String getCategory() {
            return category;
        }

        public String getUrl() {
            return url;
        }

        public Integer getEventRating() {
            return eventRating;
        }

        public Integer getAverageRating() {
            return averageRating;
        }

        public List<Object> getEventStreamInfo() {
            return eventStreamInfo;
        }

        public double getPrice() {
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

        public List<Image> getImages() {
            return images;
        }

        public List<Comment> getComments() {
            return comments;
        }

        public List<Object> getEventUserPaymentStatus() {
            return eventUserPaymentStatus;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public static class Comment{
            @SerializedName("like")
            @Expose
            private Integer like;
            @SerializedName("dislike")
            @Expose
            private Integer dislike;
            @SerializedName("angry")
            @Expose
            private Integer angry;
            @SerializedName("happy")
            @Expose
            private Integer happy;
            @SerializedName("_id")
            @Expose
            private String id;
            @SerializedName("comment")
            @Expose
            private String comment;
            @SerializedName("updatedAt")
            @Expose
            private String updatedAt;
            @SerializedName("createdAt")
            @Expose
            private String createdAt;
            @SerializedName("childComments")
            @Expose
            private List<Object> childComments = null;

            public Integer getLike() {
                return like;
            }

            public Integer getDislike() {
                return dislike;
            }

            public Integer getAngry() {
                return angry;
            }

            public Integer getHappy() {
                return happy;
            }

            public String getId() {
                return id;
            }

            public String getComment() {
                return comment;
            }

            public String getUpdatedAt() {
                return updatedAt;
            }

            public String getCreatedAt() {
                return createdAt;
            }

            public List<Object> getChildComments() {
                return childComments;
            }

            public static class ChildComment{

            }

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
