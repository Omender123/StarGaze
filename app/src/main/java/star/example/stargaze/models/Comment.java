package star.example.stargaze.models;

public class Comment {
   private String name ;
   private String profile ;
   private String liveComment;

    public Comment(String name, String profile, String liveComment) {
        this.name = name;
        this.profile = profile;
        this.liveComment = liveComment;
    }

    public String getName() {
        return name;
    }

    public String getProfile() {
        return profile;
    }

    public String getLiveComment() {
        return liveComment;
    }
}
