public class InputValidator {
    public static boolean validateEmail(String email) {
        return email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");
    }

    public static boolean validatePhoneNumber(String phone) {
        return phone.matches("\\+?[0-9]{10,15}");
    }
}
