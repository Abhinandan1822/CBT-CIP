package bankY;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.Scanner;

public class bankYMain {

    private static SessionFactory factory;

    public static void main(String[] args) {
        // Initialize Hibernate SessionFactory for database interactions
        factory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        Scanner scanner = new Scanner(System.in);

        // Display welcome message to users
        System.out.println("Welcome to BankY - Your Trusted Banking System!");

        // Infinite loop for displaying the menu and performing actions
        while (true) {
            System.out.println("\n--- BankY System ---");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit Funds");
            System.out.println("3. Withdraw Funds");
            System.out.println("4. Transfer Funds");
            System.out.println("5. View Account Details");
            System.out.println("6. Exit");
            System.out.print("Please select an option (1-6): ");

            int choice;
            try {
                // Parse user input and validate
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number between 1 and 6.");
                continue;
            }

            // Process user choice and call corresponding functionality
            switch (choice) {
                case 1: createAccount(scanner); break;  // Option to create a new account
                case 2: depositFunds(scanner); break;   // Option to deposit funds into an account
                case 3: withdrawFunds(scanner); break;  // Option to withdraw funds
                case 4: transferFunds(scanner); break;  // Option to transfer funds between accounts
                case 5: viewAccountDetails(scanner); break; // Option to view account details
                case 6: {
                    // Exit the application gracefully
                    System.out.println("Thank you for using BankY. Goodbye!");
                    scanner.close();
                    factory.close();
                    return;
                }
                default: System.out.println("Invalid choice! Please select a valid option (1-6).\n");
            }
        }
    }

    // Function to create a new account
    private static void createAccount(Scanner scanner) {
        System.out.println("\n--- Create Account ---");
        System.out.print("Enter Account Holder Name: ");
        String holderName = scanner.nextLine();

        // Validate account holder's name input
        if (holderName.isEmpty()) {
            System.out.println("Account holder name cannot be empty.");
            return;
        }

        System.out.print("Enter Initial Balance (min. ₹500): ");
        double balance;
        try {
            balance = Double.parseDouble(scanner.nextLine());
            if (balance < 500) {
                System.out.println("Initial balance must be at least ₹500.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid balance amount! Please try again.");
            return;
        }

        // Use a database transaction to save account details
        executeTransaction(session -> {
            Account account = new Account();
            account.setHolderName(holderName);
            account.setAccountNumber("AC" + System.currentTimeMillis()); // Generate unique account number
            account.setBalance(balance);

            session.save(account); // Save account details in the database
            System.out.println("Account created successfully! Your Account Number: " + account.getAccountNumber());
        });
    }

    // Function to deposit funds into an account
    private static void depositFunds(Scanner scanner) {
        System.out.println("\n--- Deposit Funds ---");
        System.out.print("Enter Account Number: ");
        String accountNumber = scanner.nextLine();

        System.out.print("Enter Amount to Deposit: ");
        double amount;
        try {
            amount = Double.parseDouble(scanner.nextLine());
            if (amount <= 0) {
                System.out.println("Deposit amount must be positive.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount! Please try again.");
            return;
        }

        // Perform deposit operation in a transaction
        executeTransaction(session -> {
            Account account = getAccount(session, accountNumber);
            if (account == null) {
                System.out.println("Account not found! Please check the account number and try again.");
                return;
            }

            // Update account balance
            account.setBalance(account.getBalance() + amount);
            session.update(account);
            System.out.println("Successfully deposited ₹" + amount + "! New Balance: ₹" + account.getBalance());
        });
    }

    // Function to withdraw funds from an account
    private static void withdrawFunds(Scanner scanner) {
        System.out.println("\n--- Withdraw Funds ---");
        System.out.print("Enter Account Number: ");
        String accountNumber = scanner.nextLine();

        System.out.print("Enter Amount to Withdraw: ");
        double amount;
        try {
            amount = Double.parseDouble(scanner.nextLine());
            if (amount <= 0) {
                System.out.println("Withdrawal amount must be positive.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount! Please try again.");
            return;
        }

        // Perform withdrawal operation in a transaction
        executeTransaction(session -> {
            Account account = getAccount(session, accountNumber);
            if (account == null) {
                System.out.println("Account not found! Please check the account number and try again.");
                return;
            }

            // Check if the account has sufficient funds
            if (account.getBalance() < amount) {
                System.out.println("Insufficient funds! Your current balance is ₹" + account.getBalance());
                return;
            }

            // Update account balance
            account.setBalance(account.getBalance() - amount);
            session.update(account);
            System.out.println("Successfully withdrawn ₹" + amount + "! New Balance: ₹" + account.getBalance());
        });
    }

    // Function to transfer funds between accounts
    private static void transferFunds(Scanner scanner) {
        System.out.println("\n--- Transfer Funds ---");
        System.out.print("Enter Source Account Number: ");
        String sourceAccountNumber = scanner.nextLine();

        System.out.print("Enter Destination Account Number: ");
        String destinationAccountNumber = scanner.nextLine();

        System.out.print("Enter Transfer Amount: ");
        double amount;
        try {
            amount = Double.parseDouble(scanner.nextLine());
            if (amount <= 0) {
                System.out.println("Transfer amount must be positive.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount! Please try again.");
            return;
        }

        // Perform transfer operation in a transaction
        executeTransaction(session -> {
            Account sourceAccount = getAccount(session, sourceAccountNumber);
            Account destinationAccount = getAccount(session, destinationAccountNumber);

            if (sourceAccount == null || destinationAccount == null) {
                System.out.println("One or both account numbers are invalid.");
                return;
            }

            // Check if the source account has sufficient funds
            if (sourceAccount.getBalance() < amount) {
                System.out.println("Insufficient funds in source account! Current Balance: ₹" + sourceAccount.getBalance());
                return;
            }

            // Update balances for both accounts
            sourceAccount.setBalance(sourceAccount.getBalance() - amount);
            destinationAccount.setBalance(destinationAccount.getBalance() + amount);

            session.update(sourceAccount);
            session.update(destinationAccount);

            System.out.println("Successfully transferred ₹" + amount + " to " + destinationAccount.getHolderName() + ".");
        });
    }

    // Function to view account details
    private static void viewAccountDetails(Scanner scanner) {
        System.out.println("\n--- View Account Details ---");
        System.out.print("Enter Account Number: ");
        String accountNumber = scanner.nextLine();

        // Fetch account details from the database
        executeTransaction(session -> {
            Account account = getAccount(session, accountNumber);
            if (account == null) {
                System.out.println("Account not found!");
                return;
            }

            // Display account details
            System.out.println("\n--- Account Details ---");
            System.out.println("Account Number: " + account.getAccountNumber());
            System.out.println("Account Holder: " + account.getHolderName());
            System.out.println("Current Balance: ₹" + account.getBalance());
        });
    }

    // Helper function to fetch an account from the database
    private static Account getAccount(Session session, String accountNumber) {
        return (Account) session.createQuery("from Account where accountNumber = :accNum")
                .setParameter("accNum", accountNumber)
                .uniqueResult();
    }

    // Helper function to execute database operations with transaction management
    private static void executeTransaction(DatabaseOperation operation) {
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            operation.execute(session); // Execute the provided operation
            transaction.commit(); // Commit the transaction if successful
        } catch (Exception e) {
            if (transaction != null) transaction.rollback(); // Rollback the transaction on error
            e.printStackTrace(); // Print stack trace for debugging
        }
    }

    // Functional interface for database operations
    @FunctionalInterface
    private interface DatabaseOperation {
        void execute(Session session);
    }
}
