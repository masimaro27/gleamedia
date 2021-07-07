package com.glm.test.gleamedia.todo.repository;

import com.glm.test.gleamedia.todo.dto.SchListReqDto;
import com.glm.test.gleamedia.todo.entities.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoCustomRepository {
    Page<Todo> fetchAllByCondition(Pageable pageable, SchListReqDto condition);
}
