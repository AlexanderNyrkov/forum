create table post
(
  id VARCHAR(255) not null
    primary key DEFAULT gen_random_uuid(),
  created_at timestamp default now() not null,
  text VARCHAR(255) not null,
  like_count bigint default 0 not null,
  updated_at timestamp default now() not null,
  user_id VARCHAR(255) not null,
  is_deleted boolean default false not null,
  CONSTRAINT comment_user_id_fk FOREIGN KEY (user_id) REFERENCES "user" (id)
)
;

CREATE UNIQUE INDEX post_id_uindex ON post (id);
