package star.example.stargaze.models.request;

public class Update_Password {
    String newPassword;
    String ConfirmPassword;
    String PhoneNon;

    public Update_Password(String newPassword, String confirmPassword, String phoneNon) {
        this.newPassword = newPassword;
        ConfirmPassword = confirmPassword;
        PhoneNon = phoneNon;
    }
}
