
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdateProfileFrame extends JFrame {
    public UpdateProfileFrame() {
        setTitle("Update Profile");
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        JLabel phoneLabel = new JLabel("Phone:");
        phoneLabel.setBounds(20, 30, 80, 25);
        add(phoneLabel);

        JTextField phoneField = new JTextField();
        phoneField.setBounds(100, 30, 150, 25);
        add(phoneField);

        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setBounds(20, 70, 80, 25);
        add(addressLabel);

        JTextField addressField = new JTextField();
        addressField.setBounds(100, 70, 150, 25);
        add(addressField);

        JButton updateButton = new JButton("Update");
        updateButton.setBounds(100, 120, 100, 30);
        add(updateButton);

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String phone = phoneField.getText();
                String address = addressField.getText();

                // Update profile logic here
                JOptionPane.showMessageDialog(null, "Profile updated successfully!");
                dispose();
            }
        });
    }
}

