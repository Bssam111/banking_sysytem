import javax.swing.*;
import java.util.regex.Pattern;

public class SignupFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField emailField;
    private JTextField phoneField;
    private JTextField fullNameField;
    private JTextField birthDateField;
    private JTextField nationalIdField;
    private JTextField addressField;
    private AuthManager authManager; // Shared AuthManager instance

    public SignupFrame(AuthManager authManager) {
        this.authManager = authManager;

        setTitle("Bank Sign-Up");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(20, 30, 120, 25);
        add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(150, 30, 200, 25);
        add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(20, 70, 120, 25);
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(150, 70, 200, 25);
        add(passwordField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(20, 110, 120, 25);
        add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(150, 110, 200, 25);
        add(emailField);

        JLabel phoneLabel = new JLabel("Phone:");
        phoneLabel.setBounds(20, 150, 120, 25);
        add(phoneLabel);

        phoneField = new JTextField();
        phoneField.setBounds(150, 150, 200, 25);
        add(phoneField);

        JLabel fullNameLabel = new JLabel("Full Name:");
        fullNameLabel.setBounds(20, 190, 120, 25);
        add(fullNameLabel);

        fullNameField = new JTextField();
        fullNameField.setBounds(150, 190, 200, 25);
        add(fullNameField);

        JLabel birthDateLabel = new JLabel("Birth Date (YYYY-MM-DD):");
        birthDateLabel.setBounds(20, 230, 200, 25);
        add(birthDateLabel);

        birthDateField = new JTextField();
        birthDateField.setBounds(220, 230, 130, 25);
        add(birthDateField);

        JLabel nationalIdLabel = new JLabel("National ID:");
        nationalIdLabel.setBounds(20, 270, 120, 25);
        add(nationalIdLabel);

        nationalIdField = new JTextField();
        nationalIdField.setBounds(150, 270, 200, 25);
        add(nationalIdField);

        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setBounds(20, 310, 120, 25);
        add(addressLabel);

        addressField = new JTextField();
        addressField.setBounds(150, 310, 200, 25);
        add(addressField);

        JButton signupButton = new JButton("Sign Up");
        signupButton.setBounds(150, 360, 100, 30);
        add(signupButton);

        signupButton.addActionListener(e -> performSignup());
    }

    private void performSignup() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String email = emailField.getText();
        String phone = phoneField.getText();
        String fullName = fullNameField.getText();
        String birthDate = birthDateField.getText();
        String nationalId = nationalIdField.getText();
        String address = addressField.getText();

        // Validation
        if (username.isEmpty() || password.isEmpty() || email.isEmpty() || phone.isEmpty() ||
            fullName.isEmpty() || birthDate.isEmpty() || nationalId.isEmpty() || address.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!birthDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
            JOptionPane.showMessageDialog(this, "Invalid birth date format. Use YYYY-MM-DD.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!nationalId.matches("\\d{10,12}")) {
            JOptionPane.showMessageDialog(this, "Invalid National ID. Must be 10-12 digits.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
            JOptionPane.showMessageDialog(this, "Invalid email format.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!phone.matches("\\d{10}")) {
            JOptionPane.showMessageDialog(this, "Invalid phone number. Must be 10 digits.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!isStrongPassword(password)) {
            JOptionPane.showMessageDialog(this, "Password must be at least 8 characters long and include at least one uppercase letter, one lowercase letter, one number, and one special character.", "Weak Password", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            boolean isSignedUp = authManager.signUp(username, password, email, phone, fullName, birthDate, nationalId, address);

            if (isSignedUp) {
                JOptionPane.showMessageDialog(this, "Sign-Up Successful! You can now log in.");
                dispose();
                new LoginFrame(authManager).setVisible(true); // Pass the shared AuthManager instance
            } else {
                JOptionPane.showMessageDialog(this, "Username already exists!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "An error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private boolean isStrongPassword(String password) {
        // Regex to validate password strength
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";
        return Pattern.matches(regex, password);
    }
}
