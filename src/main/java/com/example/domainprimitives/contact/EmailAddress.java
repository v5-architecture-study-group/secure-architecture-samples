package com.example.domainprimitives.contact;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import java.io.Serializable;
import java.util.Objects;

public final class EmailAddress implements Serializable, Comparable<EmailAddress> {

    public static final int MAX_LENGTH = 320;
    private final String emailAddress;

    private EmailAddress(String emailAddress) {
        Validate.notEmpty(emailAddress, "EmailAddress cannot be empty");
        Validate.isTrue(emailAddress.length() <= MAX_LENGTH, "EmailAddress is too long");
        var atPos = emailAddress.indexOf('@');
        if (atPos == -1) {
            throw new IllegalArgumentException("EmailAddress has no @-character");
        }
        validateLocalPart(emailAddress.substring(0, atPos));
        validateDomainPart(emailAddress.substring(atPos + 1));

        this.emailAddress = emailAddress;
    }

    private static void validateLocalPart(String localPart) {
        Validate.notEmpty(localPart, "Local part cannot be empty");
        Validate.isTrue(localPart.length() <= 64, "Local part is too long");
        if (!StringUtils.containsOnly(localPart, "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!#$%&'*+-/=?^_`{|}~.")) {
            throw new IllegalArgumentException("Local part contains illegal characters");
        }
        if (localPart.charAt(0) == '.' || localPart.charAt(localPart.length() - 1) == '.') {
            throw new IllegalArgumentException("Local part cannot start or end with a dot");
        }
        if (localPart.contains("..")) {
            throw new IllegalArgumentException("Local part cannot contain two consecutive dots");
        }
        // Quoted e-mail addresses are also supported by the standard, but not included in this example
    }

    private static void validateDomainPart(String domainPart) {
        Validate.notEmpty(domainPart, "Domain part cannot be empty");
        if (!StringUtils.containsOnly(domainPart, "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-.")) {
            throw new IllegalArgumentException("Domain part contains illegal characters");
        }
        if (domainPart.charAt(0) == '-' || domainPart.charAt(domainPart.length() - 1) == '-') {
            throw new IllegalArgumentException("Domain part cannot start or end with a hyphen");
        }
        if (domainPart.contains("..")) {
            throw new IllegalArgumentException("Domain part cannot contain two consecutive dots");
        }
        // IP-addresses are also supported if quoted with [], but not included in this example
    }

    public static EmailAddress fromString(String emailAddress) {
        return new EmailAddress(emailAddress);
    }

    @Override
    public String toString() {
        return emailAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmailAddress that = (EmailAddress) o;
        return Objects.equals(emailAddress, that.emailAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(emailAddress);
    }

    @Override
    public int compareTo(EmailAddress o) {
        return emailAddress.compareTo(o.emailAddress);
    }
}
