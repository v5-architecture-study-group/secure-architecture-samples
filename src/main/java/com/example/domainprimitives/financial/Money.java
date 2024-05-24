package com.example.domainprimitives.financial;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

public final class Money {

    public static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_EVEN;
    private final Currency currency;
    private final BigDecimal value;

    private Money(Currency currency, BigDecimal value) {
        this.currency = requireNonNull(currency, "currency must not be null");
        this.value = requireNonNull(value, "value must not be null")
                .setScale(currency.getDefaultFractionDigits(), ROUNDING_MODE);
    }

    public static Money of(Currency currency, BigDecimal value) {
        return new Money(currency, value);
    }

    public static Money of(Currency currency, long value) {
        return of(currency, new BigDecimal(value));
    }

    public static Money of(Currency currency, double value) {
        return of(currency, new BigDecimal(value));
    }

    public static Money zero(Currency currency) {
        return of(currency, BigDecimal.ZERO);
    }

    public Currency currency() {
        return currency;
    }

    public BigDecimal value() {
        return value;
    }

    public Money add(Money addend) {
        if (!addend.currency.equals(currency)) {
            throw new IllegalArgumentException("Cannot add two amounts of different currencies");
        }
        return new Money(currency, value.add(addend.value));
    }

    @Override
    public String toString() {
        return "%s %s".formatted(currency.getCurrencyCode(), value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var other = (Money) o;
        return currency.equals(other.currency) && value.equals(other.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currency, value);
    }
}
