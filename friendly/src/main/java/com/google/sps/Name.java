package com.google.sps;

/**
 * Represents a Restaurant's name
 */
public class Name {
    private static final String MESSAGE_CONSTRAINTS = "Names should only contain at least 1 alphanumeric character";
    private static final String VALIDATION_REGEX = "[A-Za-z0-9]+";
    private final String name;

    public Name(String name) throws InvalidNameException{
        if (!isValidName(name)) {
            throw new InvalidNameException(MESSAGE_CONSTRAINTS);
        }
        this.name = name;
    }

    public static boolean isValidName(String name) {
        return name.matches(VALIDATION_REGEX);
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
            || (other instanceof Name
            && this.name.equals(((Name) other).getName()));
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }
}
