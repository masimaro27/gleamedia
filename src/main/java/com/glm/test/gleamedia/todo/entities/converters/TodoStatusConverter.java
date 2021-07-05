package com.glm.test.gleamedia.todo.entities.converters;

import com.glm.test.gleamedia.todo.enums.TodoStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class TodoStatusConverter implements AttributeConverter<TodoStatus, String> {
    @Override
    public String convertToDatabaseColumn(TodoStatus attribute) {
        return attribute.getCode();
    }

    @Override
    public TodoStatus convertToEntityAttribute(String dbData) {
        return TodoStatus.findByCode(dbData);
    }
}
