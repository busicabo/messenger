create extension if not exists pgcrypto;

create table if not exists users (
    id uuid primary key default gen_random_uuid(),
    username varchar(60) not null unique,
    password text not null,
    blocked boolean not null default false,
    avatar_url TEXT not null DEFAULT 'https://90995c79f2f34c065a0d26c1400cc671.bckt.ru/default-avatar/ChatGPT%20Image%2015%20мар.%202026%20г.%2C%2019_46_55.png',
    created_at timestamptz not null default now(),
    online boolean not null default true
);


create table if not exists user_settings (
    user_id uuid primary key,
    auto_delete_message timestamptz,
    allow_writing boolean not null default true,
    allow_add_chat boolean not null default true,
    constraint fk_user_settings_user
        foreign key (user_id)
        references users(id)
        on delete cascade
);