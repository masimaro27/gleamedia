package com.glm.test.gleamedia.todo.entities;

import com.glm.test.gleamedia.todo.dto.TodoDto;
import com.glm.test.gleamedia.todo.dto.TodoRegistDto;
import com.glm.test.gleamedia.todo.entities.converters.TodoStatusConverter;
import com.glm.test.gleamedia.todo.enums.TodoStatus;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Data
@Entity
@DynamicUpdate
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private String content;

    @Column(columnDefinition = "CHAR(1)")
    @Convert(converter = TodoStatusConverter.class)
    private TodoStatus status;

    private boolean deleteYn;
//    @Column(columnDefinition = "TIMESTAMP")
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "todo", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<TodoRefMapping> ref = new ArrayList<>();

    @OneToMany(mappedBy = "todoRef", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<TodoRefMapping> origin = new ArrayList<>();

    public boolean isDuplRef(Todo todoRef) {
        for (TodoRefMapping refMapping : this.ref) {
            if (refMapping.getTodoRef().getIdx() == todoRef.getIdx()) {
                log.debug("이미 등록된 참조입니다.");
                return true;
            }
        }
        return false;
    }

    public boolean isEqualsTodoRefIdx(long todoRefIdx) {
        if (this.idx == todoRefIdx) {
            log.debug("셀프참조는 불가합니다.");
            return true;
        }
        return false;
    }

    public boolean hasRef() {
        return !this.ref.isEmpty();
    }

    public static Todo of(TodoRegistDto regDto) {
        Todo todo = new Todo();
        todo.setContent(regDto.getContent());
        todo.setStatus(TodoStatus.INCOMPLETE);

        return todo;
    }

    public static TodoDto toTodoDto (Todo todo) {
        TodoDto.TodoDtoBuilder builder = TodoDto.builder()
                .idx(todo.getIdx())
                .content(todo.getContent())
                .status(todo.getStatus())
                .updatedAt(todo.getUpdatedAt())
                .createdAt(todo.getCreatedAt());

        if (todo.getRef() != null) {
            List<TodoDto> ref = todo.getRef()
                    .stream()
                    .map(item -> Todo.toTodoDto(item.getTodoRef()))
                    .collect(Collectors.toList());
            builder.ref(ref);
        }
        return builder.build();
    }

    public void updateStatus(TodoStatus status) {
        this.status = status;
    }

    public void updateContent(String contents) {
        this.content = content;
    }

    public void delete() {
        this.deleteYn = true;
    }

    public List<TodoRefMapping> deleteAllRefTodo(List<Long> todoRefIdx) {
        List<TodoRefMapping> refTodoList = ref.stream()
                .filter(item -> todoRefIdx.contains(item.getTodoRef().getIdx()))
                .collect(Collectors.toList());
        for (int i = 0; i < refTodoList.size(); i++) {
            ref.remove(refTodoList.get(i));
        }
        return refTodoList;
    }

    public void addRef(TodoRefMapping todoRefMapping) {
        if (this.ref.contains(todoRefMapping)) {
            log.debug("이미 등록된 참조입니다.");
            return;
        }
        this.ref.add(todoRefMapping);
        if (todoRefMapping.getTodoRef() == null) {
            todoRefMapping.setTodoRef(this);
        }
    }

    public void addOrigin(TodoRefMapping todoRefMapping) {
        this.origin.add(todoRefMapping);
        if (todoRefMapping.getTodo() == null) {
            todoRefMapping.setTodo(this);
        }
    }
}
