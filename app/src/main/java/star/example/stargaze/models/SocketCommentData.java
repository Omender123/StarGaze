package star.example.stargaze.models;

public class SocketCommentData {
    private String userId;
    private String comment;
    private String eventId;

    public SocketCommentData(String userId, String comment, String eventId) {
        this.userId = userId;
        this.comment = comment;
        this.eventId = eventId;
    }
}
