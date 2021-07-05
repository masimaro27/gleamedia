package com.glm.test.gleamedia.exception;

public class GlemRuntimeException extends RuntimeException {

    private ExceptionCode code;

    public GlemRuntimeException(ExceptionCode code) {
        super(code.getDescription());
        this.code = code;
    }

    public ExceptionCode getCode() {
        return code;
    }
}
