import javax.crypto.SecretKey;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class AuthManager {
    private static final String USERS_FILE = "users_data.ser";
    private static final String ATTEMPTS_FILE = "attempts_data.ser";
    private static final String LOCKS_FILE = "locks_data.ser";
    private static final String KEY_FILE = "key.dat";
    private static final long LOCK_DURATION = 5 * 60 * 1000; // 5 minutes

    private Map<String, User> users;
    private Map<String, Integer> failedLoginAttempts;
    private Map<String, Long> accountLockTimestamps;
    private transient SecretKey secretKey;

    public AuthManager() throws Exception {
        this.secretKey = loadOrGenerateKey();
        this.users = loadFromFile(USERS_FILE);
        this.failedLoginAttempts = loadFromFile(ATTEMPTS_FILE);
        this.accountLockTimestamps = loadFromFile(LOCKS_FILE);
        if (this.failedLoginAttempts == null) this.failedLoginAttempts = new HashMap<>();
        if (this.accountLockTimestamps == null) this.accountLockTimestamps = new HashMap<>();
    }

    public boolean isAccountLocked(String username) {
        Long lockTimestamp = accountLockTimestamps.get(username);
        if (lockTimestamp == null) return false;
        boolean isLocked = System.currentTimeMillis() - lockTimestamp < LOCK_DURATION;
        if (!isLocked) {
            accountLockTimestamps.remove(username); // Unlock account if duration expired
            saveToFile(accountLockTimestamps, LOCKS_FILE);
        }
        return isLocked;
    }

    public User authenticate(String username, String password) throws Exception {
        if (isAccountLocked(username)) {
            System.out.println("Account is locked. Please try again later.");
            return null;
        }

        User user = users.get(username);
        if (user == null) {
            System.out.println("Authentication failed: User not found.");
            incrementFailedAttempts(username);
            return null;
        }

        String encryptedPassword = AESUtil.encrypt(password, secretKey);
        if (user.getEncryptedPassword().equals(encryptedPassword)) {
            failedLoginAttempts.remove(username); // Reset on successful login
            accountLockTimestamps.remove(username); // Clear lock if it exists
            saveToFile(failedLoginAttempts, ATTEMPTS_FILE);
            saveToFile(accountLockTimestamps, LOCKS_FILE);
            System.out.println("Authentication successful for user: " + username);
            return user;
        } else {
            incrementFailedAttempts(username);
            System.out.println("Authentication failed: Incorrect password.");
            return null;
        }
    }

    private void incrementFailedAttempts(String username) {
        int attempts = failedLoginAttempts.getOrDefault(username, 0) + 1;
        failedLoginAttempts.put(username, attempts);
        saveToFile(failedLoginAttempts, ATTEMPTS_FILE);
        System.out.println("Failed login attempts for " + username + ": " + attempts);

        if (attempts >= 3) {
            accountLockTimestamps.put(username, System.currentTimeMillis());
            failedLoginAttempts.remove(username); // Reset attempts after locking
            saveToFile(accountLockTimestamps, LOCKS_FILE);
            saveToFile(failedLoginAttempts, ATTEMPTS_FILE);
            System.out.println("Account locked for " + username);
        }
    }

    public boolean signUp(String username, String password, String email, String phoneNumber,
                          String fullName, String birthDate, String nationalId, String address) {
        if (users.containsKey(username)) {
            System.out.println("Username already exists!");
            return false;
        }
        try {
            String encryptedPassword = AESUtil.encrypt(password, secretKey);
            User user = new User(username, encryptedPassword, email, phoneNumber, fullName, birthDate, nationalId, address);
            users.put(username, user);
            saveToFile(users, USERS_FILE);
            System.out.println("Sign-up complete for: " + user.getUsername());
            return true;
        } catch (Exception e) {
            System.out.println("Error during sign-up: " + e.getMessage());
            return false;
        }
    }

    protected <T> void saveToFile(Map<String, T> map, String fileName) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(map);
            System.out.println("Data saved to " + fileName);
        } catch (IOException e) {
            System.out.println("Error saving data to " + fileName + ": " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private <T> Map<String, T> loadFromFile(String fileName) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            return (Map<String, T>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("No existing data found for " + fileName + ". Starting fresh.");
            return null;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading data from " + fileName + ": " + e.getMessage());
            return null;
        }
    }

    private SecretKey loadOrGenerateKey() throws Exception {
        File keyFile = new File(KEY_FILE);
        if (keyFile.exists()) {
            return AESUtil.loadKey(KEY_FILE);
        } else {
            SecretKey key = AESUtil.generateKey();
            AESUtil.saveKey(key, KEY_FILE);
            return key;
        }
    }
    public Map<String, User> getUsers() {
        return users;
    }
}
