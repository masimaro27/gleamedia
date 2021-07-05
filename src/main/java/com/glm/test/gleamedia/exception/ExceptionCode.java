package com.glm.test.gleamedia.exception;

public enum ExceptionCode {
    SERVER_ERRER("서버에러", 9999);

    private String description;
    private int code;

    ExceptionCode(String description, int code) {
        this.description = description;
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public int getCode() {
        return code;
    }
}
