import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Account implements Serializable {
    private static final long serialVersionUID = 1L;

    private String iban;
    private double balance; // New balance field to track the current balance explicitly
    private List<Transaction> transactions;

    // Constructor for Account
    public Account(String iban) {
        this.iban = iban;
        this.balance = 0.0; // Initial balance set to 0.0
        this.transactions = new ArrayList<>();
    }

    public String getIban() {
        return iban;
    }

    public double getBalance() {
        return balance;
    }

    public void addTransaction(String type, double amount) {
        transactions.add(new Transaction(type, amount));
        // Update balance directly
        if (type.equalsIgnoreCase("Credit")) {
            balance += amount;
        } else if (type.equalsIgnoreCase("Debit")) {
            balance -= amount;
        }
        System.out.println("Transaction added: " + type + " of amount $" + amount + ". Updated balance: $" + balance);
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    // For debugging purposes
    public void printTransactions() {
        System.out.println("Transactions for IBAN: " + iban);
        for (Transaction transaction : transactions) {
            System.out.println(transaction.getType() + ": $" + transaction.getAmount());
        }
    }
}
