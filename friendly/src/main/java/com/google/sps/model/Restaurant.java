package com.google.sps.model;

import java.util.Objects;

/**
 * Represents a Restaurant
 */
public class Restaurant {
    private final Name name;
    private final Location location;

    public Restaurant(Name name, Location location) {
        this.name = name;
        this.location = location;
    }

    public Name getName() {
        return this.name;
    }

    public Location getLocation() {
        return this.location;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Name: ")
                .append(this.getName())
                .append("\nLocation: ")
                .append(this.getLocation());
        
        return builder.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Restaurant)) {
            return false;
        }

        Restaurant otherRestaurant = (Restaurant) other;
        return otherRestaurant.getName().equals(this.getName())
            && otherRestaurant.getLocation().equals(this.getLocation());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, location);
    }
}