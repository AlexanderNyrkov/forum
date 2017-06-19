-- auto-generated definition
create table post
(
  id VARCHAR(255) not null
    constraint post_pkey
    primary key,
  created_at timestamp default now() not null,
  text VARCHAR(255) not null,
  like_count bigint default 0 not null,
  updated_at timestamp default now() not null,
  user_id VARCHAR(255) not null
    constraint post_user_id_fk
    references "user",
  is_deleted boolean default false not null
)
;

create unique index post_id_uindex
  on post (id)
;

