create database db_neptune_blog default character set utf8mb4 collate utf8mb4_unicode_ci;

use db_neptune_blog;

create table t_friendship
(
    source_id   bigint       not null,
    target_id   bigint       not null,
    follow_date datetime(6)  null,
    follow_from varchar(255) null,
    primary key (source_id, target_id)
);

create table t_tweet
(
    id                  bigint auto_increment
        primary key,
    author_id           bigint       null,
    conversation_id     bigint       null,
    create_at           datetime(6)  null,
    in_reply_to_user_id bigint       null,
    like_count          bigint       null,
    quote_count         bigint       null,
    reply_count         bigint       null,
    retweet_count       bigint       null,
    source              varchar(255) null,
    text                varchar(255) null,
    type                varchar(255) null,
    referenced_tweet_id bigint       null
);

create table t_user
(
    id                bigint auto_increment
        primary key,
    birthday          datetime(6)  null,
    create_at         datetime(6)  null,
    description       varchar(255) null,
    email             varchar(255) null,
    gender            varchar(255) null,
    lang              varchar(255) null,
    name              varchar(255) null,
    password          varchar(255) null,
    pinned_tweet_id   bigint       null,
    profile_image_url varchar(255) null,
    liked_count       int          null,
    tweet_count       int          null,
    large_avatar_url  varchar(255) null,
    medium_avatar_url varchar(255) null,
    small_avatar_url  varchar(255) null,
    username          varchar(255) null
);