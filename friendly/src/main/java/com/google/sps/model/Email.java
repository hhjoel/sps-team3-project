package com.google.sps.model;

/**
 * Represents a User's email
 */
public class Email {
    private static final String MESSAGE_CONSTRAINTS = "Emails must be a valid gmail address";
    private static final String VALIDATION_REGEX = "[A-Za-z0-9.]+@gmail.com";
    private final String email;

    public Email(String email) throws InvalidNameException{
        if (!isValidEmail(email)) {
            throw new InvalidNameException(MESSAGE_CONSTRAINTS);
        }
        this.email = email;
    }

    public static boolean isValidEmail(String email) {
        return email.matches(VALIDATION_REGEX);
    }

    public String getEmail() {
        return this.email;
    }

    @Override
    public String toString() {
        return this.email;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
            || (other instanceof Email
            && this.email.equals(((Email) other).getEmail()));
    }

    @Override
    public int hashCode() {
        return this.email.hashCode();
    }
}
