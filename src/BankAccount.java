public class BankAccount {
    // Declare private variables
    private String accHolderName;
    private double accBalance;

    // Class constructor
    public BankAccount(String custName, double custBalance) {
        this.accHolderName = custName;
        this.accBalance = custBalance;
    }

     public void showBalance() {
         System.out.println("\n" + this.toString());
         //manageAccount();
     }

    public double deposit(double depositAmt) {
        this.accBalance += depositAmt;
        System.out.println("\n$" + roundAmt(depositAmt) + " deposited.");

        return depositAmt;
    }

    public double withdrawal(double withdrawalAmt) {
        this.accBalance -= withdrawalAmt;
        System.out.println("\n$" + roundAmt(withdrawalAmt) + " withdrawn.");

        return withdrawalAmt;
    }

    public void transfer(double transferAmt, BankAccount recipient) {
        recipient.deposit(this.withdrawal(transferAmt));
        showBalance();
        recipient.showBalance();
    }

    public String roundAmt(double amount) {
        return String.format("%.2f", amount);
    }

    public String getAcctHolderName() {
        return this.accHolderName;
    }

    public boolean sufficientFunds(double amount) {
        return amount < this.accBalance;
    }

    @Override
    public String toString () {
        return "The account balance for " + this.accHolderName + " is $" + roundAmt(this.accBalance);
    }

}
