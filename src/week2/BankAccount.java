package week2;

public class BankAccount
{
    private double balance;

    public BankAccount(double balance)
    {
        this.balance = balance;
    }
    /**
     * Deposit an amount into the bank account
     * @param amt amount to deposit
     */
    public void deposit(double amt)
    {
        this.balance += amt;
    }
    /**
     * Withdraw an amount into the bank account
     * @param amt amount to withdraw
     */
    public void withdraw(double amt)
    {
        this.balance -= amt;
    }

    /**
     * This method is used to transfer money to another account
     * @param act another account
     * @param amt amount to be transferred
     */
    public void transfer(BankAccount act, double amt)
    {
        act.balance += amt;
        this.balance -= amt;
    }

    public double getBalance() {
        return balance;
    }
}
