import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            AuthManager authManager = new AuthManager();
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("\n1. Sign Up\n2. Login\n3. Exit");
                int choice = scanner.nextInt();
                scanner.nextLine();

                if (choice == 1) {
                    // Sign Up
                    System.out.println("Enter username:");
                    String username = scanner.nextLine();
                    System.out.println("Enter password:");
                    String password = scanner.nextLine();
                    System.out.println("Enter email:");
                    String email = scanner.nextLine();
                    System.out.println("Enter phone number:");
                    String phoneNumber = scanner.nextLine();

                    try {
                        authManager.signUp(username, password, email, phoneNumber);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                } else if (choice == 2) {
                    // Login
                    System.out.println("Enter username:");
                    String username = scanner.nextLine();
                    System.out.println("Enter password:");
                    String password = scanner.nextLine();

                    User user = authManager.login(username, password);
                    if (user != null) {
                        while (true) {
                            System.out.println("\n1. Add Account\n2. Add Transaction\n3. View Statement\n4. View Account Info\n5. Logout");
                            int userChoice = scanner.nextInt();
                            scanner.nextLine();

                            if (userChoice == 1) {
                                if (user.addAccount()) {
                                    System.out.println("New account created successfully!");
                                    // Save data after adding an account
                                    authManager.saveToFile();
                                }
                            } else if (userChoice == 2) {
                                if (user.getAccounts().isEmpty()) {
                                    System.out.println("No accounts available. Please create an account first.");
                                    continue;
                                }
                                System.out.println("Choose an account (1-3):");
                                int accountIndex = scanner.nextInt() - 1;
                                scanner.nextLine();

                                if (accountIndex >= 0 && accountIndex < user.getAccounts().size()) {
                                    Account account = user.getAccounts().get(accountIndex);
                                    System.out.println("Enter transaction description:");
                                    String description = scanner.nextLine();
                                    System.out.println("Enter transaction type (Credit/Debit):");
                                    String type = scanner.nextLine();
                                    System.out.println("Enter transaction amount:");
                                    double amount = scanner.nextDouble();
                                    scanner.nextLine();

                                    account.addTransaction(description, type, amount);
                                    System.out.println("Transaction added successfully!");
                                    // Save data after adding a transaction
                                    authManager.saveToFile();
                                } else {
                                    System.out.println("Invalid account selection.");
                                }
                            } else if (userChoice == 3) {
                                if (user.getAccounts().isEmpty()) {
                                    System.out.println("No accounts available. Please create an account first.");
                                    continue;
                                }
                                System.out.println("Choose an account (1-3):");
                                int accountIndex = scanner.nextInt() - 1;
                                scanner.nextLine();

                                if (accountIndex >= 0 && accountIndex < user.getAccounts().size()) {
                                    Account account = user.getAccounts().get(accountIndex);
                                    System.out.println("Enter start date (YYYY-MM-DD):");
                                    LocalDate startDate = LocalDate.parse(scanner.nextLine());
                                    System.out.println("Enter end date (YYYY-MM-DD):");
                                    LocalDate endDate = LocalDate.parse(scanner.nextLine());

                                    account.printStatement(startDate, endDate);
                                } else {
                                    System.out.println("Invalid account selection.");
                                }
                            } else if (userChoice == 4) {
                                // View Account Info
                                if (user.getAccounts().isEmpty()) {
                                    System.out.println("No accounts available. Please create an account first.");
                                    continue;
                                }
                                System.out.println("Choose an account (1-3):");
                                int accountIndex = scanner.nextInt() - 1;
                                scanner.nextLine();

                                if (accountIndex >= 0 && accountIndex < user.getAccounts().size()) {
                                    Account account = user.getAccounts().get(accountIndex);
                                    System.out.println("Account Information:");
                                    System.out.println(account.getAccountInfo());
                                } else {
                                    System.out.println("Invalid account selection.");
                                }
                            } else if (userChoice == 5) {
                                System.out.println("Logged out successfully!");
                                break;
                            } else {
                                System.out.println("Invalid choice. Try again.");
                            }
                        }
                    }
                } else if (choice == 3) {
                    System.out.println("Goodbye!");
                    break;
                } else {
                    System.out.println("Invalid choice. Try again.");
                }
            }

            scanner.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
