
package com.google.sps;

import java.io.IOException;

/**
 * Represents an invalid name exception
 */
public class InvalidNameException extends IOException {
    public InvalidNameException(String message) {
        super(message);
    }
}