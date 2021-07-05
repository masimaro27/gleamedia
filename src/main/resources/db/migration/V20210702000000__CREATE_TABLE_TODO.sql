create table TODO
(
    IDX        BIGINT auto_increment comment 'index',
    CONTENT VARCHAR(300) not null comment '내용',
    STATUS CHAR(1) comment 'TODO 상태, CODE_GROUP: TODO_STATUS',
    DELETE_YN BOOLEAN default 0 comment '삭제여부, [0, 1]',
    CREATED_AT DATETIME    not null comment '생성날짜 yyyy-MM-dd HH:mm:ss',
    UPDATED_AT DATETIME    not null comment '수정날짜 yyyy-MM-dd HH:mm:ss',
    constraint TODO_PK
        primary key (IDX)
) ;



