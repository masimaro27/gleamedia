package com.glm.test.gleamedia.todo;

import com.glm.test.gleamedia.todo.dto.SchListReqDto;
import com.glm.test.gleamedia.todo.dto.SchListResDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;

@Transactional
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TodoTest {

    @Autowired
    private TodoController todoController;

    @Test
    void fetchAllTest() {
        SchListReqDto dto = new SchListReqDto();
        ResponseEntity<SchListResDto> res = todoController.fetchTodoList(dto);
        Assertions.assertTrue(res.getBody().getCount() > 0);
    }


}
