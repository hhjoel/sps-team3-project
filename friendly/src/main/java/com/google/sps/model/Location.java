package com.google.sps.model;

/**
 * Represents a Restaurant's location
 */
public class Location {
    private static final String MESSAGE_CONSTRAINTS = "Location should only either be NORTH, SOUTH, EAST, WEST, CENTRAL";
    private final LocationEnum location;

    public Location(String location) throws InvalidNameException{
        location = location.toUpperCase();
        if (!isValidLocation(location)) {
            throw new InvalidNameException(MESSAGE_CONSTRAINTS);
        }
        this.location = LocationEnum.valueOf(location);
    }

    public static boolean isValidLocation(String location) {
        for (LocationEnum loc : LocationEnum.values()) {
            if (loc.name().equals(location)) {
                return true;
            }
        }

        return false;
    }

    public String getLocation() {
        return this.location.toString();
    }

    @Override
    public String toString() {
        return this.location.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this
            || (other instanceof Location
            && this.location.equals(((Location) other).getLocation()));
    }

    @Override
    public int hashCode() {
        return this.location.hashCode();
    }
}
