package star.example.stargaze.models;

public class PastData {
    private String eventName;
    private String eventDate;
    private String eventLoc;
    private int thumbnailIcon;
    private String presents;

    public PastData(String eventName, String eventDate, String eventLoc, int thumbnailIcon, String presents) {
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.eventLoc = eventLoc;
        this.thumbnailIcon = thumbnailIcon;
        this.presents = presents;
    }

    public String getEventName() {
        return eventName;
    }

    public String getEventDate() {
        return eventDate;
    }

    public String getEventLoc() {
        return eventLoc;
    }

    public int getThumbnailIcon() {
        return thumbnailIcon;
    }

    public String getPresents() {
        return presents;
    }
}
