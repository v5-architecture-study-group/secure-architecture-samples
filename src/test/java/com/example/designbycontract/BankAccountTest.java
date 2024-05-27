package com.example.designbycontract;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class BankAccountTest {

    @Test
    void initial_balance_must_be_greater_than_or_equal_to_zero() {
        assertThat(new BankAccount(new BigDecimal(0)).balance()).isEqualTo(new BigDecimal(0));
        assertThat(new BankAccount(new BigDecimal(1)).balance()).isEqualTo(new BigDecimal(1));
        assertThatThrownBy(() -> new BankAccount(new BigDecimal(-1))).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void deposit_amount_must_be_positive() {
        var bankAccount = new BankAccount(new BigDecimal(0));
        bankAccount.deposit(new BigDecimal(1));
        assertThat(bankAccount.balance()).isEqualTo(new BigDecimal(1));
        assertThatThrownBy(() -> bankAccount.deposit(new BigDecimal(0))).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> bankAccount.deposit(new BigDecimal(-1))).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void withdraw_amount_must_be_positive() {
        var bankAccount = new BankAccount(new BigDecimal(1));
        bankAccount.withdraw(new BigDecimal(1));
        assertThat(bankAccount.balance()).isEqualTo(new BigDecimal(0));
        assertThatThrownBy(() -> bankAccount.withdraw(new BigDecimal(0))).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> bankAccount.withdraw(new BigDecimal(-1))).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void withdraw_amount_must_not_exceed_balance() {
        var bankAccount = new BankAccount(new BigDecimal(1));
        bankAccount.withdraw(new BigDecimal(1));
        assertThat(bankAccount.balance()).isEqualTo(new BigDecimal(0));
        assertThatThrownBy(() -> bankAccount.withdraw(new BigDecimal(1))).isInstanceOf(IllegalArgumentException.class);
    }
}
