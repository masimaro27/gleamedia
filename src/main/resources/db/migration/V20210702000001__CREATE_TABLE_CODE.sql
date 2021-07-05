create table CODE
(
    CODE_GROUP VARCHAR(20) not null comment '코드그룹',
    CODE VARCHAR(6) not null comment '코드',
    DESCRIPTION VARCHAR(300) comment '코드 설명',
    CREATED_AT DATETIME    not null comment '생성날짜 yyyy-MM-dd HH:mm:ss',
    UPDATED_AT DATETIME    not null comment '수정날짜 yyyy-MM-dd HH:mm:ss',
    constraint CODE_PK
        primary key (CODE_GROUP, CODE)
);



