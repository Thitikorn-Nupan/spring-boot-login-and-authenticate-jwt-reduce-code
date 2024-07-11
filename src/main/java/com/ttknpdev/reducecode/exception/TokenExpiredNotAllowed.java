package com.ttknpdev.reducecode.exception;

public class TokenExpiredNotAllowed extends RuntimeException {

    public TokenExpiredNotAllowed(String message) {
        super(message);
    }

}
