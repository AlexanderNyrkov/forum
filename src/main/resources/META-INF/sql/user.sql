create table "user"
(
  id text default gen_random_uuid() not null
    constraint user_pkey
    primary key,
  login text not null,
  name text not null,
  password text not null,
  email text not null,
  created_at timestamp default now() not null,
  is_deleted boolean default false not null,
  updated_at timestamp default now() not null
)
;

create unique index user_login_uindex
  on "user" (login)
;