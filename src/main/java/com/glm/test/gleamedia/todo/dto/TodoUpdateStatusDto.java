package com.glm.test.gleamedia.todo.dto;

import com.glm.test.gleamedia.todo.enums.TodoStatus;
import lombok.Data;

@Data
public class TodoUpdateStatusDto {
    private TodoStatus status;
}
