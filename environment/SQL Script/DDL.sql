create schema db_neptune_blog collate utf8mb4_0900_ai_ci;

create table t_friend_relation
(
    follow_date datetime    null,
    follow_from varchar(32) null,
    from_id     varchar(32) not null,
    to_id       varchar(32) not null,
    primary key (from_id, to_id)
);

create table t_post
(
    id          varchar(32)   not null
        primary key,
    content     varchar(1024) null,
    create_date datetime      null,
    device      varchar(32)   null,
    update_date datetime      null,
    user_id     varchar(255)  null
);

create table t_user
(
    id            varchar(32)  not null
        primary key,
    email         varchar(50)  null,
    lang_key      varchar(6)   null,
    login_date    datetime     null,
    password      varchar(64)  null,
    real_name     varchar(32)  null,
    register_date datetime     null,
    sex           tinyint      not null,
    token         varchar(256) null,
    username      varchar(32)  null,
    birthday      datetime     null,
    constraint UK_i6qjjoe560mee5ajdg7v1o6mi
        unique (email),
    constraint UK_jhib4legehrm4yscx9t3lirqi
        unique (username)
);

