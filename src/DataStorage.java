import java.util.HashMap;

public class DataStorage {
    private static HashMap<String, User> users = new HashMap<>(); // In-memory storage

    // Add a new user to the storage
    public static void addUser(User user) {
        if (user == null) {
            System.out.println("Attempted to add a null user!"); // Debugging
            return;
        }
        System.out.println("Adding user: " + user.getUsername()); // Debugging
        users.put(user.getUsername(), user);
    }

    // Find a user by username
    public static User findUser(String username) {
        System.out.println("Searching for user: " + username); // Debugging
        return users.get(username);
    }
}
