package com.glm.test.gleamedia.todo.enums;

import java.util.Arrays;

public enum TodoStatus {
    COMPLETE("1"), INCOMPLETE("2");

    private String code;

    TodoStatus(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static TodoStatus findByCode(String code) {
        return Arrays.stream(TodoStatus.values())
                .filter(status -> status.getCode().equals(code))
                .findAny()
                .orElseThrow(() -> new RuntimeException(String.format("[code: %d]존재하지 않는 코드입니다.", code)));
    }
}
