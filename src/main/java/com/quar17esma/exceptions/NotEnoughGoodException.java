package com.quar17esma.exceptions;

public class NotEnoughGoodException extends RuntimeException {
    public NotEnoughGoodException() {
    }

    public NotEnoughGoodException(String message) {
        super(message);
    }

    public NotEnoughGoodException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotEnoughGoodException(Throwable cause) {
        super(cause);
    }

    public NotEnoughGoodException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
