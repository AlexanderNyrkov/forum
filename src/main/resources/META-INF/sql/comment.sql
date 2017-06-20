-- auto-generated definition
create table comment
(
  id VARCHAR(255) not null
    constraint comment_pkey
    primary key,
  user_id VARCHAR(255) not null
    constraint comment_user_id_fk
    references "user",
  like_count bigint default 0 not null,
  created_at timestamp default now() not null,
  updated_at timestamp default now(),
  text VARCHAR(255) not null,
  post_id VARCHAR(255) not null
    constraint comment_post_id_fk
    references post,
  is_deleted boolean default false
)
;

create unique index comment_id_uindex
  on comment (id)
;

