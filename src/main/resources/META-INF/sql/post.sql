create table post
(
  id text default gen_random_uuid() not null
    constraint post_pkey
    primary key,
  created_at timestamp default now() not null,
  text text not null,
  like_count bigint default 0 not null,
  updated_at timestamp default now() not null,
  user_id text not null
    constraint fkk57duahs09p84ac63gbqbvx9v
    references "user"
    constraint post_user_id_fk
    references "user"
    on update cascade on delete cascade,
  is_deleted boolean default false not null
)
;

create unique index post_id_uindex
  on post (id)
;