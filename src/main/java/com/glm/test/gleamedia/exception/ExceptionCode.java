package com.glm.test.gleamedia.exception;

public enum ExceptionCode {
    SERVER_ERRER("서버에러", 9999),
    FAIL_UPDATE_COMPLETE("태그로 등록된 TODO가 먼저 완료되어야 합니다.", 1000);

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
