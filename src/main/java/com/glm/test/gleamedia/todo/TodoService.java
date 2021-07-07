package com.glm.test.gleamedia.todo;

import com.glm.test.gleamedia.exception.ExceptionCode;
import com.glm.test.gleamedia.exception.GlemRuntimeException;
import com.glm.test.gleamedia.todo.entities.Todo;
import com.glm.test.gleamedia.todo.entities.TodoRefMapping;
import com.glm.test.gleamedia.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepo;
    private final TodoRefMappingRepository todoRefMappingRepo;

    @Transactional
    public void deleteTodo(long todoIdx) {
        Todo todo = todoRepo.findById(todoIdx).orElseThrow(() -> new GlemRuntimeException(ExceptionCode.SERVER_ERRER));
        todo.delete();
        if (todo.hasRef()) {
            todoRefMappingRepo.deleteByTodoIdx(todoIdx);
        }
    }

    @Transactional
    public void updateContent(long todoIdx, String content) {
        Todo todo = todoRepo.findById(todoIdx).orElseThrow(() -> new GlemRuntimeException(ExceptionCode.SERVER_ERRER));
        todo.updateContent(content);
    }

    // 수정 필요 - 쿼리가 많이 나감.
    @Transactional
    public Todo registRefTodo (long todoIdx, List<Long> todoRefIdxList) {
        List<Todo> todoRefData = todoRepo.findAllById(todoRefIdxList);
        Todo todo = todoRepo.findById(todoIdx).orElseThrow(() -> new GlemRuntimeException(ExceptionCode.SERVER_ERRER));

        List<TodoRefMapping> saveData = new ArrayList<>();

        for (Todo todoRef : todoRefData) {
            if (todo.isDuplRef(todoRef) || todo.isEqualsTodoRefIdx(todoRef.getIdx())) {
                continue;
            }
            TodoRefMapping mapping = TodoRefMapping.of(todo, todoRef);
            saveData.add(mapping);
            todo.addRef(mapping);
        }

        todoRefMappingRepo.saveAll(saveData);

        return todo;
    }

    public Todo deleteRefTodo(long todoIdx, List<Long> todoRefIdxList) {
        Todo todo = todoRepo.findById(todoIdx).orElseThrow(() -> new GlemRuntimeException(ExceptionCode.SERVER_ERRER));
        List<TodoRefMapping> delItems = todo.deleteAllRefTodo(todoRefIdxList);
        todoRefMappingRepo.deleteAll(delItems);
        return todo;
    }

}
