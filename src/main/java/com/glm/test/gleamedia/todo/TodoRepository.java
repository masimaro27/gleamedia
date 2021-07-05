package com.glm.test.gleamedia.todo;

import com.glm.test.gleamedia.todo.entities.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    @Query(value = "    select " +
            "                t" +
            "           from" +
            "                Todo t" +
            "           left join fetch t.ref r" +
            "            left join fetch r.todoRef" +
            "           where t.deleteYn = FALSE",
            countQuery = "select count(t) from Todo t"
    )
    Page<Todo> fetchAll(Pageable pageable);
    void deleteById(long todoIdx);


}
