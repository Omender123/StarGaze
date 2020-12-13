package star.example.stargaze.models.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ContactBody {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("subject")
    @Expose
    private String subject;
    @SerializedName("description")
    @Expose
    private String description;

    public ContactBody(String name, String email, String subject, String description) {
        this.name = name;
        this.email = email;
        this.subject = subject;
        this.description = description;
    }
}
