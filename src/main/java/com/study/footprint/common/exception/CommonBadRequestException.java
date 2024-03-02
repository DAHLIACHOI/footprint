package com.study.footprint.common.exception;

public class CommonBadRequestException extends RuntimeException {

        public CommonBadRequestException() {
            super();
        }

        public CommonBadRequestException(String message) {
            super(message);
        }

        public CommonBadRequestException(String message, Throwable t) {
            super(message, t);
        }
}
