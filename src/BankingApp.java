import javax.swing.*;

public class BankingApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                AuthManager authManager = new AuthManager(); // Create the shared AuthManager instance
                LoginFrame loginFrame = new LoginFrame(authManager); // Pass the instance to LoginFrame
                loginFrame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "An error occurred while initializing the application: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
