package com.glm.test.gleamedia.todo.entities;

import com.glm.test.gleamedia.todo.entities.converters.TodoStatusConverter;
import com.glm.test.gleamedia.todo.enums.TodoStatus;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Data
@Entity
public class TodoRefMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
//    private Long todoIdx;
//    private Long todoIdxRef;
    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "todoIdx")
    private Todo todo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "todoIdxRef")
    private Todo todoRef;

    public static TodoRefMapping of(Todo todo, Todo todoRef) {
        TodoRefMapping mapping = new TodoRefMapping();
        mapping.setTodo(todo);
        mapping.setTodoRef(todoRef);
        return mapping;
    }

    public void setTodoRef(Todo todoRef) {
        this.todoRef = todoRef;
        if (!todoRef.getRef().contains(this)) {
            todoRef.addRef(this);
        }
    }
}
