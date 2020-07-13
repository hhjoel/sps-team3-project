package com.google.sps;

import java.util.Objects;

/**
 * Represents a User
 */
public class User {
    private final Email email;

    public User(Email email) {
        this.email = email;
    }

    public Email getEmail() {
        return this.email;
    }

    @Override
    public String toString() {
        return this.email.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof User)) {
            return false;
        }

        User otherUser = (User) other;
        return otherUser.getEmail().equals(this.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}