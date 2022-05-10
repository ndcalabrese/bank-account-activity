public class BankAccount {
    // Declare private variables.
    private String accHolderName;
    private double accBalance;

    // Class constructor
    public BankAccount(String custName, double custBalance) {
        this.accHolderName = custName;
        this.accBalance = custBalance;
    }

    // Prints balance of account by calling our custom toString() method.
     public void showBalance() {
         System.out.println("\n" + this.toString());
     }

     // Deposits specified amount into account.
    public void deposit(double depositAmt) {
        this.accBalance += depositAmt;
        System.out.println("\n$" + roundAmt(depositAmt) + " deposited.");

    }

    // Withdraws specified amount from account.
    // Returns withdrawal amount to be used as deposit amount during
    // a transfer of funds.
    public double withdrawal(double withdrawalAmt) {
        this.accBalance -= withdrawalAmt;
        System.out.println("\n$" + roundAmt(withdrawalAmt) + " withdrawn.");

        return withdrawalAmt;
    }

    // Transfer specified amount to specified recipient account.
    // Show balances of sender and recipients' accounts afterwards.
    public void transfer(double transferAmt, BankAccount recipient) {
        recipient.deposit(this.withdrawal(transferAmt));
        showBalance();
        recipient.showBalance();
    }

    // Round doubles to two decimal places, because money.
    public String roundAmt(double amount) {
        return String.format("%.2f", amount);
    }

    // Return name of account holder
    public String getAcctHolderName() {
        return this.accHolderName;
    }

    // Check if account has sufficient funds for withdrawals
    // and transfers.
    public boolean hasInsufficientFunds(double amount) {
        System.out.println("\nInsufficient funds.");
        this.showBalance();

        return amount > this.accBalance;
    }

    // Override the toString() method so that it returns a string
    // containing the account holder's name and balance.
    @Override
    public String toString () {
        return "The account balance for " + this.accHolderName + " is $" + roundAmt(this.accBalance);
    }

}
