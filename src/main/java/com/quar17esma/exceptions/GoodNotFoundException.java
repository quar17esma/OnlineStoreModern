package com.quar17esma.exceptions;

public class GoodNotFoundException extends RuntimeException {
    private long goodId;

    public GoodNotFoundException(long goodId) {
        this.goodId = goodId;
    }

    public long getGoodId() {
        return goodId;
    }
}
