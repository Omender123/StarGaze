package star.example.stargaze.models;

public class TempData {

    private String date;
    private String showType;
    private String eventName;
    private String location;
    private String price;
    private int image;

    public TempData(String date, String showType, String eventName, String location, String price, int image) {
        this.date = date;
        this.showType = showType;
        this.eventName = eventName;
        this.location = location;
        this.price = price;
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public String getShowType() {
        return showType;
    }

    public String getEventName() {
        return eventName;
    }

    public String getLocation() {
        return location;
    }

    public String getPrice() {
        return price;
    }

    public int getImage() {
        return image;
    }
}
