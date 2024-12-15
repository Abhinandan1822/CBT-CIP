import java.util.Scanner;

public class BankY {
    public static void main(String[] args) {
        Bank bank = new Bank();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- BankY Menu ---");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer Funds");
            System.out.println("5. View Account Details");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> bank.createAccount();
                case 2 -> bank.deposit();
                case 3 -> bank.withdraw();
                case 4 -> bank.transferFunds();
                case 5 -> bank.viewAccountDetails();
                case 6 -> {
                    System.out.println("Thank you for using BankY!");
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
