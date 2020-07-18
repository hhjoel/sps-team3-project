package com.google.sps.model;

import java.io.IOException;

/**
 * Represents an invalid name exception
 */
public class InvalidNameException extends IOException {
    public InvalidNameException(String message) {
        super(message);
    }
}