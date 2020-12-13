package star.example.stargaze.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CommentResp {
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

    @SerializedName("eventRating")
    @Expose
    private Integer eventRating;
    @SerializedName("averageRating")
    @Expose
    private Integer averageRating;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("comments")
    @Expose
    private List<Comment> comments = null;

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

    public Integer getEventRating() {
        return eventRating;
    }

    public Integer getAverageRating() {
        return averageRating;
    }

    public String getId() {
        return id;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public static  class Comment{
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
        @SerializedName("updatedAt")
        @Expose
        private String updatedAt;
        @SerializedName("createdAt")
        @Expose
        private String createdAt;
        @SerializedName("childComments")
        @Expose
        private List<Object> childComments = null;
        @SerializedName("_id")
        @Expose
        private String id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("comment")
        @Expose
        private String comment;
        @SerializedName("user")
        @Expose
        private String user;

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

        public String getUpdatedAt() {
            return updatedAt;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public List<Object> getChildComments() {
            return childComments;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getComment() {
            return comment;
        }

        public String getUser() {
            return user;
        }
    }

}
