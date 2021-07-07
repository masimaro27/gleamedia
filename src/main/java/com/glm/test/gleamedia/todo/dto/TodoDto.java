package com.glm.test.gleamedia.todo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.glm.test.gleamedia.todo.entities.Todo;
import com.glm.test.gleamedia.todo.enums.TodoStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class TodoDto {

    private Long idx;
    private String content;
    private TodoStatus status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
    private List<TodoDto> ref = new ArrayList<>();
//    private List<TodoDto> origin  = new ArrayList<>();

    public boolean isEmptyRef() {
        if (ref == null) {
            return true;
        }
        return ref.isEmpty();
    }

    public boolean isCompleted() {
        return this.status.equals(TodoStatus.COMPLETE);
    }



}
