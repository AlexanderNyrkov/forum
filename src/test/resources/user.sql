create table "user"
(
  id VARCHAR(255) not null
    constraint user_pkey
    primary key,
  login VARCHAR(255) not null,
  name VARCHAR(255) not null,
  password VARCHAR(255) not null,
  email VARCHAR(255) not null,
  created_at timestamp default now() not null,
  is_deleted boolean default false not null,
  updated_at timestamp default now() not null,
  is_admin boolean default false not null
)
;

create unique index user_login_uindex
  on "user" (login)
;

