import java.io.*;
import java.util.*;

public class Bank {
    private final List<Account> accounts = new ArrayList<>();
    private final String fileName = "accounts.txt";

    public Bank() {
        loadAccounts();
    }

    public void createAccount() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        System.out.print("Enter initial deposit: ");
        double initialDeposit = scanner.nextDouble();

        Account account = new Account(UUID.randomUUID().toString(), name, initialDeposit);
        accounts.add(account);
        saveAccounts();
        System.out.println("Account created successfully! Your Account Number: " + account.getAccountNumber());
    }

    public void deposit() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();
        System.out.print("Enter deposit amount: ");
        double amount = scanner.nextDouble();

        Account account = findAccount(accountNumber);
        if (account != null) {
            account.deposit(amount);
            saveAccounts();
            System.out.println("Deposit successful! New Balance: " + account.getBalance());
        } else {
            System.out.println("Account not found.");
        }
    }

    public void withdraw() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();
        System.out.print("Enter withdrawal amount: ");
        double amount = scanner.nextDouble();

        Account account = findAccount(accountNumber);
        if (account != null) {
            if (account.withdraw(amount)) {
                saveAccounts();
                System.out.println("Withdrawal successful! New Balance: " + account.getBalance());
            } else {
                System.out.println("Insufficient balance.");
            }
        } else {
            System.out.println("Account not found.");
        }
    }

    public void transferFunds() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your account number: ");
        String fromAccountNumber = scanner.nextLine();
        System.out.print("Enter recipient's account number: ");
        String toAccountNumber = scanner.nextLine();
        System.out.print("Enter transfer amount: ");
        double amount = scanner.nextDouble();

        Account fromAccount = findAccount(fromAccountNumber);
        Account toAccount = findAccount(toAccountNumber);

        if (fromAccount != null && toAccount != null) {
            if (fromAccount.withdraw(amount)) {
                toAccount.deposit(amount);
                saveAccounts();
                System.out.println("Transfer successful!");
            } else {
                System.out.println("Insufficient balance.");
            }
        } else {
            System.out.println("One or both accounts not found.");
        }
    }

    public void viewAccountDetails() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();

        Account account = findAccount(accountNumber);
        if (account != null) {
            System.out.println("Account Details:");
            System.out.println("Name: " + account.getName());
            System.out.println("Account Number: " + account.getAccountNumber());
            System.out.println("Balance: " + account.getBalance());
        } else {
            System.out.println("Account not found.");
        }
    }

    private Account findAccount(String accountNumber) {
        return accounts.stream()
                .filter(account -> account.getAccountNumber().equals(accountNumber))
                .findFirst()
                .orElse(null);
    }

    private void saveAccounts() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(accounts);
        } catch (IOException e) {
            System.err.println("Error saving accounts: " + e.getMessage());
        }
    }

    private void loadAccounts() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            Object obj = ois.readObject();
            if (obj instanceof List) {
                accounts.addAll((List<Account>) obj);
            }
        } catch (IOException | ClassNotFoundException e) {
            // File may not exist initially; ignore.
        }
    }
}
