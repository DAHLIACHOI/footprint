package com.study.footprint.common.exception;

public class CommonNotFoundException extends RuntimeException {

        public CommonNotFoundException() {
            super();
        }

        public CommonNotFoundException(String message) {
            super(message);
        }

        public CommonNotFoundException(String message, Throwable t) {
            super(message, t);
        }
}
