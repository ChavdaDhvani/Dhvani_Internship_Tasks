import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class User {
    private String userId;
    private String userPin;
    private double balance;
    private List<Transaction> transactions;

    // User class nu constructor
    public User(String userId, String userPin, double initialBalance) {
        this.userId = userId;
        this.userPin = userPin;
        this.balance = initialBalance;
        this.transactions = new ArrayList<>();
    }

    // User ID return karvanu function
    public String getUserId() {
        return userId;
    }

    // User PIN return karvanu function
    public String getUserPin() {
        return userPin;
    }

    // User balance return karvanu function
    public double getBalance() {
        return balance;
    }

    // User balance set karvanu function
    public void setBalance(double balance) {
        this.balance = balance;
    }

    // Transactions list return karvanu function
    public List<Transaction> getTransactions() {
        return transactions;
    }

    // Transaction add karvanu function
    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }
}

class Transaction {
    private String date;
    private String type;
    private double amount;
    private double balance;

    // Transaction class nu constructor
    public Transaction(String type, double amount, double balance) {
        this.date = java.time.LocalDateTime.now().toString();
        this.type = type;
        this.amount = amount;
        this.balance = balance;
    }

    // Transaction string ma convert karvanu function
    @Override
    public String toString() {
        return "Date: " + date +   ", Type: " + type + ", Amount: " + amount + ", Balance: " + balance;
    }
}

class ATM {
    private Map<String, User> users;
    private User currentUser;

    // ATM class nu constructor
    public ATM() {
        users = new HashMap<>();
        // Default user add karva mate
        users.put("user", new User("user", "1234", 5000.0));
    }

    
    // ATM start karvanu function
    public void start() {
        Scanner scanner = new Scanner(System.in);

        // User ID ane PIN puchvanu
        System.out.print("Enter user ID: ");
        String userId = scanner.nextLine();
        System.out.print("Enter user PIN: ");
        String userPin = scanner.nextLine();

        currentUser = authenticate(userId, userPin);

        if (currentUser != null) {
            boolean running = true;
            while (running) {
                // Menu display karvanu
                System.out.println("1. Transactions History");
                System.out.println("2. Withdraw");
                System.out.println("3. Deposit");
                System.out.println("4. Transfer");
                System.out.println("5. Quit");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        showTransactionHistory();
                        break;
                    case 2:
                        withdraw();
                        break;
                    case 3:
                        deposit();
                        break;
                    case 4:
                        transfer();
                        break;
                    case 5:
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid option. Try again.");
                }
            }
        } else {
            System.out.println("Authentication failed. Exiting.");
        }
    }

    // User authenticate karvanu function
    private User authenticate(String userId, String userPin) {
        User user = users.get(userId);
        if (user != null && user.getUserPin().equals(userPin)) {
            return user;
        }
        return null;
    }
// Transaction history display karvanu function
    private void showTransactionHistory() {
        System.out.println("Transaction History:");
        for (Transaction transaction : currentUser.getTransactions()) {
            System.out.println(transaction);
        }
    }

    // Withdraw karvanu function
    private void withdraw() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter amount to withdraw: ");
        double amount = scanner.nextDouble();
        if (amount > 0 && amount <= currentUser.getBalance()) {
            currentUser.setBalance(currentUser.getBalance() - amount);
            Transaction transaction = new Transaction("Withdraw", amount, currentUser.getBalance());
            currentUser.addTransaction(transaction);
            System.out.println("Withdrawal successful.");
            System.out.println("Current balance: " + currentUser.getBalance());
           
        } else {
            System.out.println("Insufficient balance or invalid amount.");
        }
    }

    // Deposit karvanu function
    private void deposit() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter amount to deposit: ");
        double amount = scanner.nextDouble();
        if (amount > 0) {
            currentUser.setBalance(currentUser.getBalance() + amount);
            Transaction transaction = new Transaction("Deposit", amount, currentUser.getBalance());
            currentUser.addTransaction(transaction);
            System.out.println("Deposit successful. Current balance: " + currentUser.getBalance());
        } else {
            System.out.println("Invalid amount.");
        }
    }

    // Transfer karvanu function
    private void transfer() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter recipient user ID: ");
        String recipientId = scanner.next();
        System.out.print("Enter amount to transfer: ");
        double amount = scanner.nextDouble();

        User recipient = users.get(recipientId);

        if (recipient != null && amount > 0 && amount <= currentUser.getBalance()) {
            currentUser.setBalance(currentUser.getBalance() - amount);
            recipient.setBalance(recipient.getBalance() + amount);

            Transaction transactionOut = new Transaction("Transfer to " + recipientId, amount, currentUser.getBalance());
            currentUser.addTransaction(transactionOut);

            Transaction transactionIn = new Transaction("Transfer from " + currentUser.getUserId(), amount, recipient.getBalance());
            recipient.addTransaction(transactionIn);

            System.out.println("Transfer successful. Current balance: " + currentUser.getBalance());
        } else {
            System.out.println("Transfer failed. Check recipient ID, amount, and balance.");
        }
    }
}

public class ATMInterface {
    public static void main(String[] args) {
        ATM atm = new ATM();
        atm.start();
    }
}

    

