package star.example.stargaze.models.request;

public class Password {
    private String password;
    private String newPassword;

    public Password(String password, String newPassword) {
        this.password = password;
        this.newPassword = newPassword;
    }
}
