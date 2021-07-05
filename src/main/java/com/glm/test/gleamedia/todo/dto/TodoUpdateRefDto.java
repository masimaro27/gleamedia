package com.glm.test.gleamedia.todo.dto;

import com.glm.test.gleamedia.todo.enums.TodoStatus;
import lombok.Data;

import java.util.List;

@Data
public class TodoUpdateRefDto {
    private List<Long> refTodoIdxList;
}
