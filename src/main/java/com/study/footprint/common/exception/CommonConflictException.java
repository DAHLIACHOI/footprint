package com.study.footprint.common.exception;

public class CommonConflictException extends RuntimeException {

    public CommonConflictException() {
        super();
    }

    public CommonConflictException(String message) {
        super(message);
    }

    public CommonConflictException(String message, Throwable t) {
        super(message, t);
    }
}
