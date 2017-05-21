create table comment
(
  id text default gen_random_uuid() not null
    constraint comment_pkey
    primary key,
  user_id text not null
    constraint comment_user_id_fk
    references "user"
    on update cascade on delete cascade
    constraint fkc4guf2g3mhm8dd6rvogse4wb0
    references "user",
  like_count bigint default 0 not null,
  created_at timestamp default now() not null,
  updated_at timestamp default now(),
  text text not null,
  post_id text not null
    constraint comment_post_id_fk
    references post
    on update cascade on delete cascade
    constraint fkmaitm7cs4poauhptay7ehmge3
    references post,
  is_deleted boolean default false
)
;

create unique index comment_id_uindex
  on comment (id)
;