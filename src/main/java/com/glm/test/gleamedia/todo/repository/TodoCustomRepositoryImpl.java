package com.glm.test.gleamedia.todo.repository;

import com.glm.test.gleamedia.todo.dto.SchListReqDto;
import com.glm.test.gleamedia.todo.entities.QTodo;
import com.glm.test.gleamedia.todo.entities.QTodoRefMapping;
import com.glm.test.gleamedia.todo.entities.Todo;
import com.glm.test.gleamedia.todo.enums.TodoStatus;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalTime;

@RequiredArgsConstructor
public class TodoCustomRepositoryImpl implements TodoCustomRepository{
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Todo> fetchAllByCondition(Pageable pageable, SchListReqDto condition) {

        QTodo qTodo = QTodo.todo;
        QTodo qTodo_2 = QTodo.todo;
        QTodoRefMapping qTodoRefMapping = QTodoRefMapping.todoRefMapping;

        BooleanBuilder predicate = new BooleanBuilder();
        predicate.and(qTodo.deleteYn.eq(false));
        if (StringUtils.hasText(condition.getContent())) {
            predicate.and(qTodo.content.like("%"+condition.getContent()+"%"));
        }
        if (condition.getStatus() != null) {
            predicate.and(qTodo.status.eq(condition.getStatus()));
        }
        if (condition.getCompletedStartDate() != null && condition.getCompletedEndDate() != null) {

            predicate
                    .and(qTodo.status.eq(TodoStatus.COMPLETE))
                    .and(
                    this.genDateTimePeriodSchExp(
                            condition.getCompletedStartDate(),
                            condition.getCompletedEndDate(),
                            qTodo.updatedAt));
        }
        QueryResults<Todo> result = queryFactory.selectFrom(qTodo)
                .leftJoin(qTodo.ref, qTodoRefMapping)
                .fetchJoin()
//                .leftJoin(qTodoRefMapping.todoRef, qTodo_2)
//                .fetchJoin()
                .where(predicate)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        return new PageImpl<> (result.getResults(), pageable, result.getTotal());
    }

    private BooleanExpression genDateTimePeriodSchExp(LocalDate startDate, LocalDate endDate, DateTimePath qEntity) {
        return qEntity.between(startDate.atTime(LocalTime.MIN), endDate.atTime(LocalTime.MAX));
    }
}
