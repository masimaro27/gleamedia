create table TODO_REF_MAPPING
(
    IDX                 BIGINT auto_increment primary key,
    TODO_IDX            BIGINT NOT NULL comment 'todo index',
    TODO_IDX_REF        BIGINT NOT NULL comment 'reference todo index',
    CREATED_AT          DATETIME    not null comment '생성날짜 yyyy-MM-dd HH:mm:ss'
);



