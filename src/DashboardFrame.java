import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class DashboardFrame extends JFrame {
    private User user;
    private AuthManager authManager;
    private JLabel userFullNameLabel;
    private JLabel ibanLabel;
    private JLabel balanceLabel;

    public DashboardFrame(User user, AuthManager authManager) {
        this.user = user;
        this.authManager = authManager;

        setTitle("Dashboard");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Display User's Full Name
        userFullNameLabel = new JLabel("Welcome, " + user.getFullName());
        userFullNameLabel.setBounds(20, 10, 500, 25);
        userFullNameLabel.setFont(userFullNameLabel.getFont().deriveFont(18f)); // Larger font for emphasis
        add(userFullNameLabel);

        // Display IBAN
        JLabel ibanTitleLabel = new JLabel("IBAN:");
        ibanTitleLabel.setBounds(20, 50, 100, 25);
        add(ibanTitleLabel);

        ibanLabel = new JLabel(user.getAccount().getIban());
        ibanLabel.setBounds(120, 50, 300, 25);
        add(ibanLabel);

        // Display Balance
        JLabel balanceTitleLabel = new JLabel("Balance:");
        balanceTitleLabel.setBounds(20, 90, 100, 25);
        add(balanceTitleLabel);

        balanceLabel = new JLabel("$" + user.getAccount().getBalance());
        balanceLabel.setBounds(120, 90, 300, 25);
        add(balanceLabel);

        // Button to View Profile
        JButton viewProfileButton = new JButton("View Profile");
        viewProfileButton.setBounds(20, 140, 200, 30);
        add(viewProfileButton);
        viewProfileButton.addActionListener(e -> viewProfile());

        // Button to Update Profile
        JButton updateProfileButton = new JButton("Update Profile");
        updateProfileButton.setBounds(250, 140, 200, 30);
        add(updateProfileButton);
        updateProfileButton.addActionListener(e -> updateProfile());

        // Button to View Transactions
        JButton viewTransactionsButton = new JButton("View Transactions");
        viewTransactionsButton.setBounds(20, 200, 200, 30);
        add(viewTransactionsButton);
        viewTransactionsButton.addActionListener(e -> viewTransactions());

        // Button to Add Transactions
        JButton addTransactionButton = new JButton("Add Transaction");
        addTransactionButton.setBounds(250, 200, 200, 30);
        add(addTransactionButton);
        addTransactionButton.addActionListener(e -> openTransactionDialog());

        // Log Out Button
        JButton logOutButton = new JButton("Log Out");
        logOutButton.setBounds(20, 300, 430, 30);
        logOutButton.addActionListener(e -> logOut());
        add(logOutButton);
    }

    private void viewProfile() {
        String profileInfo = String.format(
            "Full Name: %s\nBirth Date: %s\nNational ID: %s\nAddress: %s\nEmail: %s\nPhone: %s\nIBAN: %s\nBalance: $%.2f",
            user.getFullName(), user.getBirthDate(), user.getNationalId(), user.getAddress(),
            user.getEmail(), user.getPhoneNumber(), user.getAccount().getIban(), user.getAccount().getBalance()
        );

        JOptionPane.showMessageDialog(
            this,
            profileInfo,
            "Profile Information",
            JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void updateProfile() {
        JDialog dialog = new JDialog(this, "Update Profile", true);
        dialog.setSize(400, 400);
        dialog.setLayout(null);

        JLabel fullNameLabel = new JLabel("Full Name:");
        fullNameLabel.setBounds(20, 30, 120, 25);
        dialog.add(fullNameLabel);

        JTextField fullNameField = new JTextField(user.getFullName());
        fullNameField.setBounds(150, 30, 200, 25);
        dialog.add(fullNameField);

        JLabel birthDateLabel = new JLabel("Birth Date (YYYY-MM-DD):");
        birthDateLabel.setBounds(20, 70, 200, 25);
        dialog.add(birthDateLabel);

        JTextField birthDateField = new JTextField(user.getBirthDate());
        birthDateField.setBounds(220, 70, 130, 25);
        dialog.add(birthDateField);

        JLabel nationalIdLabel = new JLabel("National ID:");
        nationalIdLabel.setBounds(20, 110, 120, 25);
        dialog.add(nationalIdLabel);

        JTextField nationalIdField = new JTextField(user.getNationalId());
        nationalIdField.setBounds(150, 110, 200, 25);
        dialog.add(nationalIdField);

        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setBounds(20, 150, 120, 25);
        dialog.add(addressLabel);

        JTextField addressField = new JTextField(user.getAddress());
        addressField.setBounds(150, 150, 200, 25);
        dialog.add(addressField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(20, 190, 120, 25);
        dialog.add(emailLabel);

        JTextField emailField = new JTextField(user.getEmail());
        emailField.setBounds(150, 190, 200, 25);
        dialog.add(emailField);

        JLabel phoneLabel = new JLabel("Phone:");
        phoneLabel.setBounds(20, 230, 120, 25);
        dialog.add(phoneLabel);

        JTextField phoneField = new JTextField(user.getPhoneNumber());
        phoneField.setBounds(150, 230, 200, 25);
        dialog.add(phoneField);

        JButton saveButton = new JButton("Save");
        saveButton.setBounds(50, 300, 80, 30);
        dialog.add(saveButton);

        saveButton.addActionListener(e -> {
            user.setFullName(fullNameField.getText());
            user.setBirthDate(birthDateField.getText());
            user.setNationalId(nationalIdField.getText());
            user.setAddress(addressField.getText());
            user.setEmail(emailField.getText());
            user.setPhoneNumber(phoneField.getText());

            authManager.saveToFile(authManager.getUsers(), "users_data.ser");
            JOptionPane.showMessageDialog(dialog, "Profile updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            dialog.dispose();
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBounds(150, 300, 80, 30);
        dialog.add(cancelButton);
        cancelButton.addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);
    }

    private void viewTransactions() {
        List<Transaction> transactions = user.getAccount().getTransactions();
        if (transactions.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No transactions found.", "Transaction History", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        StringBuilder transactionHistory = new StringBuilder();
        for (Transaction transaction : transactions) {
            transactionHistory.append(transaction.toString()).append("\n");
        }

        JTextArea textArea = new JTextArea(transactionHistory.toString());
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(20, 20, 450, 300);

        JDialog dialog = new JDialog(this, "Transaction History", true);
        dialog.setSize(500, 400);
        dialog.setLayout(null);
        dialog.add(scrollPane);

        JButton closeButton = new JButton("Close");
        closeButton.setBounds(200, 330, 100, 30);
        closeButton.addActionListener(e -> dialog.dispose());
        dialog.add(closeButton);

        dialog.setVisible(true);
    }

    private void openTransactionDialog() {
        JDialog dialog = new JDialog(this, "Add Transaction", true);
        dialog.setSize(300, 200);
        dialog.setLayout(null);
    
        JLabel typeLabel = new JLabel("Type:");
        typeLabel.setBounds(20, 20, 80, 25);
        dialog.add(typeLabel);
    
        String[] types = {"Credit", "Debit"};
        JComboBox<String> typeComboBox = new JComboBox<>(types);
        typeComboBox.setBounds(100, 20, 150, 25);
        dialog.add(typeComboBox);
    
        JLabel amountLabel = new JLabel("Amount:");
        amountLabel.setBounds(20, 60, 80, 25);
        dialog.add(amountLabel);
    
        JTextField amountField = new JTextField();
        amountField.setBounds(100, 60, 150, 25);
        dialog.add(amountField);
    
        JButton saveButton = new JButton("Save");
        saveButton.setBounds(50, 120, 80, 30);
        dialog.add(saveButton);
    
        saveButton.addActionListener(e -> {
            String type = (String) typeComboBox.getSelectedItem();
            try {
                double amount = Double.parseDouble(amountField.getText());
                if (amount <= 0) {
                    JOptionPane.showMessageDialog(dialog, "Amount must be greater than zero.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                    return;
                }
    
                if (type.equals("Debit") && amount > user.getAccount().getBalance()) {
                    JOptionPane.showMessageDialog(dialog, "Insufficient balance for debit transaction.", "Transaction Failed", JOptionPane.ERROR_MESSAGE);
                    return;
                }
    
                user.getAccount().addTransaction(type, amount); // Add transaction
                authManager.saveToFile(authManager.getUsers(), "users_data.ser"); // Save user data // Save changes
                updateBalance(); // Update balance display
                JOptionPane.showMessageDialog(dialog, "Transaction saved successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                dialog.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Invalid amount. Please enter a number.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBounds(150, 120, 80, 30);
        dialog.add(cancelButton);
        cancelButton.addActionListener(e -> dialog.dispose());
    
        dialog.setVisible(true);
    }
    private void updateBalance() {
        balanceLabel.setText("$" + user.getAccount().getBalance()); // Update the balance label
    }
    
    

    private void logOut() {
        JOptionPane.showMessageDialog(this, "You have been logged out.", "Log Out", JOptionPane.INFORMATION_MESSAGE);
        dispose();
        new LoginFrame(authManager).setVisible(true); // Pass the shared AuthManager instance
    }
    
}
