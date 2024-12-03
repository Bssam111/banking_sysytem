import javax.swing.*;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private AuthManager authManager; // Shared instance

    public LoginFrame(AuthManager authManager) {
        this.authManager = authManager; // Use the shared instance
        setTitle("Bank Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(20, 30, 80, 25);
        add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(120, 30, 200, 25);
        add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(20, 80, 80, 25);
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(120, 80, 200, 25);
        add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(50, 150, 100, 30);
        add(loginButton);

        JButton signupButton = new JButton("Sign Up");
        signupButton.setBounds(200, 150, 100, 30);
        add(signupButton);

        loginButton.addActionListener(e -> performLogin());
        signupButton.addActionListener(e -> {
            dispose();
            new SignupFrame(authManager).setVisible(true); // Pass the shared instance
        });
    }

    private void performLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        try {
            System.out.println("Attempting login for username: " + username);

            if (authManager.isAccountLocked(username)) {
                System.out.println("Account is locked for username: " + username);
                JOptionPane.showMessageDialog(this, "Your account is locked. Please try again later.", "Account Locked", JOptionPane.WARNING_MESSAGE);
                return;
            }

            User authenticatedUser = authManager.authenticate(username, password);

            if (authenticatedUser != null) {
                JOptionPane.showMessageDialog(this, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose();
                new DashboardFrame(authenticatedUser, authManager).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "An error occurred during login: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
}
