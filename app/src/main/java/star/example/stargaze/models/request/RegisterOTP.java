package star.example.stargaze.models.request;

public class RegisterOTP {
    private String phone;
    private String code;

    public RegisterOTP(String phone, String code) {
        this.phone = phone;
        this.code = code;
    }
}
