import java.io.Serializable;
import java.util.Random;

public class IBAN implements Serializable {
    private static final long serialVersionUID = 1L;

    protected String generateRandomIBAN() {
        String countryCode = "SA"; // Example: Saudi Arabia
        String checkDigits = String.format("%02d", new Random().nextInt(100));
        String bankIdentifier = generateRandomDigits(8);
        String accountNumber = generateRandomDigits(10);
        return countryCode + checkDigits + bankIdentifier + accountNumber;
    }

    private String generateRandomDigits(int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}
