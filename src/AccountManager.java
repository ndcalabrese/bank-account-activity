import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class AccountManager {
    public static void main(String[] args) {

        // Create list of accounts
        ArrayList<BankAccount> accountList = new ArrayList<>();

        // Instantiate new bank accounts
        BankAccount account1 = new BankAccount("Nick", 500);
        BankAccount account2 = new BankAccount("Larry", 5000);
        BankAccount account3 = new BankAccount("Mary", 300);

        // Add bank accounts to list of accounts
        Collections.addAll(accountList, account1, account2, account3);

        manageAccount(accountList, selectAccount(accountList, false));

    }

    public static BankAccount selectAccount(ArrayList<BankAccount> accountList, boolean isTransfer) {

        String selectedAccount;
        String messageString = (isTransfer) ? "\nPlease select a recipient: " : "\nPlease select an account: ";
        System.out.println(messageString);

        for (int i = 1; i <= accountList.size(); i++) {
            System.out.println(i + " - " + (accountList.get(i - 1)).getAcctHolderName() + " - " + (accountList.get(i-1)).toString());
        }
        System.out.print(messageString);

        while (true) {
            selectedAccount = getUserInput();

            if (!selectedAccount.equals("1")
                    && !selectedAccount.equals("2")
                    && !selectedAccount.equals("3")) {
                System.out.println("Invalid entry. What would you like to do?");
            } else {
                break;
            }
        }

        // Returns account object
        return accountList.get(Integer.parseInt(selectedAccount) - 1);
    }
    public static void manageAccount(ArrayList<BankAccount> accountList, BankAccount selectedAccount) {

        String action;
        String amountString = "0";

        System.out.println("""
                
                What would you like to do?
                
                1 - Show my account balance
                2 - Deposit an amount
                3 - Withdraw an amount
                4 - Transfer an amount
                0 - Exit
                """);
        System.out.print("Choose an option: ");

        while (true) {
            action = getUserInput();

            if (!action.equals("1")
                    && !action.equals("2")
                    && !action.equals("3")
                    && !action.equals("4")
                    && !action.equals("0")) {
                System.out.println("Invalid entry.");
            } else {
                break;
            }
        }

        // If user chooses to deposit or withdraw money, request
        // an amount
        if (action.equals("2") || action.equals("3") || action.equals("4")) {
            String actionString = action.equals("2")
                    ? "deposit"
                    : (action.equals("3")
                    ? "withdraw"
                    : "transfer"
            );
            while (true) {
                System.out.print("\nPlease enter an amount to " + actionString + ": $" );
                amountString = getUserInput();

                if (!isNumeric(amountString)) {
                    System.out.println("Invalid entry. Please enter an amount.");
                } else {
                    break;
                }
            }
        }

        doAcctAction(action, Double.parseDouble(amountString), accountList, selectedAccount);

    }

    public static String getUserInput() {
        Scanner scanner = new Scanner(System.in);

        return scanner.nextLine();
    }

    public static void doAcctAction(String action, double amount,
                                    ArrayList<BankAccount> accountList,
                                    BankAccount selectedAccount) {
        switch (action) {
            case "1" -> {
                selectedAccount.showBalance();
                manageAccount(accountList, selectedAccount);
            }
            case "2" -> {
                selectedAccount.deposit(amount);
                selectedAccount.showBalance();
                manageAccount(accountList, selectedAccount);
            }
            case "3" -> {
                if (!selectedAccount.sufficientFunds(amount)) {
                    System.out.println("Insufficient funds.");
                } else {
                    selectedAccount.withdrawal(amount);
                }
                selectedAccount.showBalance();
                manageAccount(accountList, selectedAccount);
            }
            case "4" -> {
                if (!selectedAccount.sufficientFunds(amount)) {
                    System.out.println("Insufficient funds.");
                    selectedAccount.showBalance();
                    manageAccount(accountList, selectedAccount);;
                } else {
                    selectedAccount.transfer(amount, selectAccount(accountList, true));
                    manageAccount(accountList, selectedAccount);
                }
            }
            case "0" -> {System.out.println("Goodbye!\n");
                System.exit(0);
            }
        }
    }

    // Checks to see if an input string is a valid double
    // and returns a boolean
    public static boolean isNumeric(String amountInput) {
        try {
            Double.parseDouble(amountInput);
            return true;
        } catch(NumberFormatException e) {
            return false;
        }
    }

}
