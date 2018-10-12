package com.quar17esma.exceptions;

public class NotEnoughGoodException extends RuntimeException {
    private final Long goodId;

    public NotEnoughGoodException(Long goodId) {
        this.goodId = goodId;
    }

    public NotEnoughGoodException(String message, Long goodId) {
        super(message);
        this.goodId = goodId;
    }

    public NotEnoughGoodException(String message, Throwable cause, Long goodId) {
        super(message, cause);
        this.goodId = goodId;
    }

    public NotEnoughGoodException(Throwable cause, Long goodId) {
        super(cause);
        this.goodId = goodId;
    }

    public NotEnoughGoodException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Long goodId) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.goodId = goodId;
    }

    public Long getGoodId() {
        return goodId;
    }
}
