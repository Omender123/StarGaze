package star.example.stargaze.utils;

public class Validation {

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null || target.equals("")) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    public static final boolean isValidPhoneNumber(CharSequence target) {
        if (target.length()!=10 || target.equals("")) {
            return false;
        } else {
            return android.util.Patterns.PHONE.matcher(target).matches();
        }
    }

}
