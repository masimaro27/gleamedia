package com.glm.test.gleamedia.todo;

import com.glm.test.gleamedia.exception.ExceptionCode;
import com.glm.test.gleamedia.exception.GlemRuntimeException;
import com.glm.test.gleamedia.todo.dto.*;
import com.glm.test.gleamedia.todo.entities.Todo;
import com.glm.test.gleamedia.todo.entities.TodoRefMapping;
import com.glm.test.gleamedia.todo.enums.TodoStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequestMapping(path = "/api/todo")
@RestController
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;
    private final TodoRepository todoRepo;
    private final TodoRefMappingRepository todoRefMappingRepo;
    private final ModelMapper globalModelMapper;

    @GetMapping
    public ResponseEntity<SchListResDto> fetchTodoList(SchListReqDto reqDto) {
        Page<Todo> data = todoRepo.fetchAll(reqDto.getPageable());

        List<TodoDto> todoDtoList = data.getContent().stream()
                .map(item -> Todo.toTodoDto(item))
                .collect(Collectors.toList());

        return ResponseEntity.ok(SchListResDto.of(data, todoDtoList));
    }

    @PostMapping
    public ResponseEntity<TodoDto> registTodo(@RequestBody TodoRegistDto reqDto) {
        Todo todo = todoRepo.save(Todo.of(reqDto));
        return ResponseEntity.ok(globalModelMapper.map(todo, TodoDto.class));
    }

    @DeleteMapping(path = "{todoIdx}")
    public ResponseEntity deleteTodo(@PathVariable("todoIdx") long todoIdx) {
        todoService.deleteTodo(todoIdx);
        return ResponseEntity.ok().build();
    }

    @PutMapping(path = "{todoIdx}")
    public ResponseEntity updateTodoConetents(@PathVariable("todoIdx") long todoIdx, @RequestBody TodoUpdateDto reqDto) {
        todoService.updateContent(todoIdx, reqDto.getContent());
        return ResponseEntity.ok().build();
    }

    @Transactional
    @PutMapping(path = "/ref/reg/{todoIdx}")
    public ResponseEntity registRefTodo(@PathVariable("todoIdx") long todoIdx, @RequestBody TodoUpdateRefDto reqDto) {
        todoService.registRefTodo(todoIdx, reqDto.getRefTodoIdxList());
        return ResponseEntity.ok().build();
    }

    @PutMapping(path = "/ref/del/{todoIdx}")
    public ResponseEntity deleteRefTodo(@PathVariable("todoIdx") long todoIdx,  @RequestBody TodoUpdateRefDto reqDto) {
        Todo todo = todoRepo.findById(todoIdx).orElseThrow(() -> new GlemRuntimeException(ExceptionCode.SERVER_ERRER));
        List<TodoRefMapping> delItems = todo.deleteAllRefTodo(reqDto.getRefTodoIdxList());
        todoRefMappingRepo.deleteAll(delItems);
        return ResponseEntity.ok().build();
    }

    @PutMapping(path = "/status/{todoIdx}")
    public ResponseEntity updateTodoStatus(@PathVariable("todoIdx") long todoIdx, @RequestBody TodoUpdateStatusDto reqDto) {
        Todo todo = todoRepo.findById(todoIdx).orElseThrow(() -> new GlemRuntimeException(ExceptionCode.SERVER_ERRER));
        todo.updateStatus(reqDto.getStatus());
        todoRepo.save(todo);
        return ResponseEntity.ok().build();
    }



}
