package com.dburyak.exercise.game.bowling.io;

import lombok.Getter;
import lombok.Setter;

/**
 * Indicates that input has wrong format and can't be parsed.
 */
@Setter
@Getter
public class FormatException extends RuntimeException {
    public FormatException() {
        super();
    }

    public FormatException(String message) {
        super(message);
    }

    public FormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public FormatException(Throwable cause) {
        super(cause);
    }

    protected FormatException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}