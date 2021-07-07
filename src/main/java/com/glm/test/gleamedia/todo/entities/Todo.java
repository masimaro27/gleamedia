package com.glm.test.gleamedia.todo.entities;

import com.glm.test.gleamedia.exception.ExceptionCode;
import com.glm.test.gleamedia.exception.GlemRuntimeException;
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
            List<TodoDto> ref = new ArrayList<>();
            for(TodoRefMapping mapping: todo.getRef()) {
                ref.add(
                        TodoDto.builder()
                        .idx(mapping.getTodoRef().getIdx())
                        .content(mapping.getTodoRef().getContent())
                        .status(mapping.getTodoRef().getStatus())
                        .updatedAt(mapping.getTodoRef().getUpdatedAt())
                        .createdAt(mapping.getTodoRef().getCreatedAt())
                        .build()
                );
            }
            builder.ref(ref);
        }
        return builder.build();
    }

    /**
     * 완료 업데이트 처리 시 모든 참조태그가 완료상태여야 요청처리
     * @param status
     */
    public void updateStatus(TodoStatus status) {
        if (status.equals(TodoStatus.COMPLETE) && !isAllCompletedRefTodo()) {
            throw new GlemRuntimeException(ExceptionCode.FAIL_UPDATE_COMPLETE);
        }
        this.status = status;
    }

    public void updateContent(String contents) {
        this.content = content;
    }

    public void delete() {
        this.deleteYn = true;
    }

    private boolean isAllCompletedRefTodo() {
        if (this.ref == null || this.ref.isEmpty()) {
            return true;
        }

        for (int i = 0; i < this.ref.size(); i++) {
            Todo refTodo = this.ref.get(i).getTodo();
            if (!refTodo.isCompleted()) {
                return false;
            }
        }
        return true;
    }

    private boolean isCompleted() {
        return this.status.equals(TodoStatus.COMPLETE);
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

//    public void addOrigin(TodoRefMapping todoRefMapping) {
//        this.origin.add(todoRefMapping);
//        if (todoRefMapping.getTodo() == null) {
//            todoRefMapping.setTodo(this);
//        }
//    }
}
