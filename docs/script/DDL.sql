create database db_neptune_blog default character set utf8mb4 collate utf8mb4_general_ci;
use db_neptune_blog;

create table tb_friendship
(
    source_id   bigint      not null,
    target_id   bigint      not null,
    follow_date timestamp   null,
    follow_from varchar(50) null,
    primary key (source_id, target_id)
);

create table tb_like
(
    tweet_id  bigint      not null,
    user_id   bigint      not null,
    create_at timestamp   null,
    source    varchar(50) null,
    primary key (tweet_id, user_id)
);

create table tb_tweet
(
    id                  bigint auto_increment
        primary key,
    author_id           bigint       null,
    conversation_id     bigint       null,
    create_at           timestamp    null,
    in_reply_to_user_id bigint       null,
    source              varchar(50)  null,
    text                varchar(500) null,
    type                varchar(10)  null,
    referenced_tweet_id bigint       null
);

create table tb_tweet_public_metrics
(
    tweet_id      bigint not null
        primary key,
    like_count    int    null,
    quote_count   int    null,
    reply_count   int    null,
    retweet_count int    null
);

create table tb_user
(
    id                bigint auto_increment
        primary key,
    birthday          date         null,
    create_at         timestamp    null,
    description       varchar(300) null,
    email             varchar(60)  null,
    gender            varchar(10)  null,
    lang              varchar(6)   null,
    name              varchar(30)  null,
    password          varchar(80)  null,
    pinned_tweet_id   bigint       null,
    profile_image_url varchar(300) null,
    large_avatar_url  varchar(255) null,
    medium_avatar_url varchar(255) null,
    small_avatar_url  varchar(255) null,
    username          varchar(30)  null
);

create table tb_user_public_metrics
(
    user_id     bigint not null
        primary key,
    liked_count int    null,
    tweet_count int    null
);
