import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class AccountManager {
    public static void main(String[] args) {

        // Create list of accounts using an ArrayList.
        ArrayList<BankAccount> accountList = new ArrayList<>();

        // Instantiate new bank accounts.
        BankAccount account1 = new BankAccount("Nick", 5_00);
        BankAccount account2 = new BankAccount("Larry", 5_000);
        BankAccount account3 = new BankAccount("Mary", 300);
        BankAccount account4 = new BankAccount("Bill", 99_999_999);

        // Add bank accounts to list of accounts.
        Collections.addAll(accountList, account1, account2, account3, account4);

        // Display "main menu" after selecting an account.
        manageAccount(accountList, selectAccount(accountList, false));

    }

    // Provide user with the list of accounts and allow them to select
    // an account based on their input.
    public static BankAccount selectAccount(ArrayList<BankAccount> accountList, boolean isTransfer) {

        String selectedAccount;

        System.out.println("\nList of accounts:");

        // Iterate through the ArrayList of BankAccount objects and print
        // the name of the account holder and the balance of each account.
        for (int i = 1; i <= accountList.size(); i++) {
            System.out.println(i + " - " + (accountList.get(i - 1)).getAcctHolderName() + " - " + (accountList.get(i-1)).toString());
        }
        System.out.println("\n");

        // Show a different message depending on if account is being selected
        // for the first time, or as a recipient for a transfer of funds.
        String messageString = (isTransfer) ? "Please select a recipient: " : "Please select an account: ";
        System.out.print(messageString);

        // Obtain user input for account number.
        // If user input does not correspond to an existing account
        // tell user that entry was invalid.
        while (true) {
            selectedAccount = getUserInput();

            if (!selectedAccount.equals("1")
                    && !selectedAccount.equals("2")
                    && !selectedAccount.equals("3")
                    && !selectedAccount.equals("4")) {
                System.out.println("Invalid entry.");
            } else {
                break;
            }
        }

        // Returns account object from list of accounts that corresponds to
        // the user input
        return accountList.get(Integer.parseInt(selectedAccount) - 1);
    }
    public static void manageAccount(ArrayList<BankAccount> accountList, BankAccount selectedAccount) {

        String action;
        String amountString;
        double amount = 0;

        System.out.println("Welcome back, " + selectedAccount.getAcctHolderName() + "!");
        System.out.println("""
                
                What would you like to do?
                
                1 - Show my account balance
                2 - Deposit an amount
                3 - Withdraw an amount
                4 - Transfer an amount
                5 - Select another account
                0 - Exit
                """);
        System.out.print("Choose an option: ");

        while (true) {
            action = getUserInput();

            if (!action.equals("1")
                    && !action.equals("2")
                    && !action.equals("3")
                    && !action.equals("4")
                    && !action.equals("5")
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
                    amount = Double.parseDouble(amountString);
                    if (amount < 0 ) {
                        System.out.println("Invalid entry. Please enter an amount.");
                    } else {
                        break;
                    }
                }
            }
        }

        doAcctAction(action, amount, accountList, selectedAccount);

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
                if (selectedAccount.hasInsufficientFunds(amount)) {
                    System.out.println("\nInsufficient funds.");
                    selectedAccount.showBalance();
                    System.out.println("\n");
                    manageAccount(accountList, selectedAccount);
                } else {
                    selectedAccount.withdrawal(amount);
                }
                selectedAccount.showBalance();
                manageAccount(accountList, selectedAccount);
            }
            case "4" -> {
                if (selectedAccount.hasInsufficientFunds(amount)) {
                    System.out.println("\nInsufficient funds.");
                    selectedAccount.showBalance();
                    System.out.println("\n");
                    manageAccount(accountList, selectedAccount);
                } else {
                    selectedAccount.transfer(amount, selectAccount(accountList, true));
                    manageAccount(accountList, selectedAccount);
                }
            }
            case "5" -> manageAccount(accountList, selectAccount(accountList, false));
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
