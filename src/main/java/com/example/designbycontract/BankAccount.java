package com.example.designbycontract;

import java.math.BigDecimal;

/**
 * A simple bank account that supports deposit and withdrawal operations.<br>
 * Invariant: balance must always be >= 0
 */
public class BankAccount {

    private BigDecimal balance; // Yes, this should be a domain primitive in a real-world example, but this is not a real-world example.

    private void checkInvariant() {
        assert balance != null : "Invariant violated: balance is null";
        assert balance.compareTo(BigDecimal.ZERO) >= 0 : "Invariant violated: balance is negative";
    }

    /**
     * Creates a new <code>BankAccount</code> with the given initial balance.
     *
     * @param initialBalance initial balance (must be >= 0)
     * @throws IllegalArgumentException if initial balance is < 0
     */
    public BankAccount(BigDecimal initialBalance) {
        if (initialBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("initialBalance must be greater than or equal to zero");
        }
        this.balance = initialBalance;
        checkInvariant();
    }

    /**
     * Deposits the specified amount into the account.<br>
     * Post-condition: balance will be increased by amount
     *
     * @param amount the amount to deposit (must be >= 0)
     * @throws IllegalArgumentException if amount is < 0
     */
    public void deposit(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        balance = balance.add(amount);
        checkInvariant();
    }

    /**
     * Withdraws the specified amount from the account.
     * <br>
     * Pre-condition: balance must be >= amount<br>
     * Post-condition: balance will be decreased by amount
     *
     * @param amount the amount to withdraw (must be >= 0)
     * @throws IllegalArgumentException if amount is < 0 or if amount exceeds the current balance
     */
    public void withdraw(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        if (amount.compareTo(balance) > 0) {
            throw new IllegalArgumentException("Insufficient funds");
        }
        balance = balance.subtract(amount);
        checkInvariant();
    }

    public BigDecimal balance() {
        return balance;
    }
}
