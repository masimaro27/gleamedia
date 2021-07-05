package com.glm.test.gleamedia.todo;

import com.glm.test.gleamedia.todo.entities.Todo;
import com.glm.test.gleamedia.todo.entities.TodoRefMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRefMappingRepository extends JpaRepository<TodoRefMapping, Long> {
    void deleteByTodoIdx(long todoIdx);
}
