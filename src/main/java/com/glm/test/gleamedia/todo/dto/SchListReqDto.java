package com.glm.test.gleamedia.todo.dto;

import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Data
public class SchListReqDto {
    private int size = 10;
    private int page = 1;

    public Pageable getPageable() {
        return PageRequest.of(this.page - 1, this.size);
    }
}
