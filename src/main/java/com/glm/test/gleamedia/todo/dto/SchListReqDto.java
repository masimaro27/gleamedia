package com.glm.test.gleamedia.todo.dto;

import com.glm.test.gleamedia.todo.enums.TodoStatus;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class SchListReqDto {
    private int size = 10;
    private int page = 1;
    private String content;
    private TodoStatus status;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate completedStartDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate completedEndDate;


    public Pageable getPageable() {
        return PageRequest.of(this.page - 1, this.size);
    }
}
