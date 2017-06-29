create table "user"
(
  id VARCHAR(255) DEFAULT gen_random_uuid() not null
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

create unique index user_email_uindex
  on "user" (email)
;

INSERT INTO "user" (id, login, name, password, email, is_admin) VALUES (
  gen_random_uuid(),
  'admin',
  'admin',
  '$2a$10$67g7uTNrdAHcE9clx1Dmg.aS9UuUke85gtUaxQQlKJF7SJkUibY82', -- admin
  'admin@gmail',
  TRUE
);

COMMIT;