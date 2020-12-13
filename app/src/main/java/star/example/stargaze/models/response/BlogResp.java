package star.example.stargaze.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BlogResp {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("like")
    @Expose
    private Integer like;
    @SerializedName("dislike")
    @Expose
    private Integer dislike;
    @SerializedName("subject")
    @Expose
    private String subject;
    @SerializedName("article")
    @Expose
    private String article;
    @SerializedName("writer")
    @Expose
    private String writer;
    @SerializedName("comments")
    @Expose
    private List<Comment> comments = null;
    @SerializedName("images")
    @Expose
    private List<Image> images = null;

    public String getId() {
        return id;
    }

    public Integer getLike() {
        return like;
    }

    public Integer getDislike() {
        return dislike;
    }

    public String getSubject() {
        return subject;
    }

    public String getArticle() {
        return article;
    }

    public String getWriter() {
        return writer;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public List<Image> getImages() {
        return images;
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
        @SerializedName("user")
        @Expose
        private String user;
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
        private List<ChildComment> childComments = null;

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

        public String getUser() {
            return user;
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

        public List<ChildComment> getChildComments() {
            return childComments;
        }

        public static class ChildComment{
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
            @SerializedName("user")
            @Expose
            private String user;
            @SerializedName("comment")
            @Expose
            private String comment;

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

            public String getUser() {
                return user;
            }

            public String getComment() {
                return comment;
            }
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
