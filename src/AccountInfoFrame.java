import javax.swing.*;

public class AccountInfoFrame extends JFrame {
    public AccountInfoFrame(User user) {
        setTitle("Account Information");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        // Fetch the dynamic balance and IBAN
        String iban = user.getAccount().getIban();
        double balance = user.getAccount().getBalance();

        JLabel accountInfoLabel = new JLabel("<html>IBAN: " + iban + "<br/>Balance: $" + balance + "</html>");
        accountInfoLabel.setBounds(20, 20, 250, 100);
        add(accountInfoLabel);
    }
}
