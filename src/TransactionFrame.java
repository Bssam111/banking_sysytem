import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class TransactionFrame extends JFrame {
    private Account account;

    public TransactionFrame(Account account) {
        this.account = account;

        setTitle("Perform Transaction");
        setSize(300, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        JLabel typeLabel = new JLabel("Type (Credit/Debit):");
        typeLabel.setBounds(20, 30, 120, 25);
        add(typeLabel);

        JTextField typeField = new JTextField();
        typeField.setBounds(150, 30, 100, 25);
        add(typeField);

        JLabel amountLabel = new JLabel("Amount:");
        amountLabel.setBounds(20, 70, 120, 25);
        add(amountLabel);

        JTextField amountField = new JTextField();
        amountField.setBounds(150, 70, 100, 25);
        add(amountField);

        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(100, 120, 100, 30);
        add(submitButton);

        submitButton.addActionListener(e -> {
            String type = typeField.getText();
            String amountText = amountField.getText();
        
            try {
                double amount = Double.parseDouble(amountText);
        
                if (!type.equalsIgnoreCase("Credit") && !type.equalsIgnoreCase("Debit")) {
                    JOptionPane.showMessageDialog(null, "Invalid transaction type. Use Credit or Debit.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
        
                System.out.println("Transaction: " + type + " Amount: " + amount); // Debugging
                account.addTransaction(type, amount);
        
                JOptionPane.showMessageDialog(null, "Transaction successful! Current balance: $" + account.getBalance(), "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid amount. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
    }
}

