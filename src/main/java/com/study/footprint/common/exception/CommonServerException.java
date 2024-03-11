package com.study.footprint.common.exception;

public class CommonServerException extends RuntimeException {

    public CommonServerException() {
        super();
    }

    public CommonServerException(String message) {
        super(message);
    }

    public CommonServerException(String message, Throwable t) {
        super(message, t);
    }

}
