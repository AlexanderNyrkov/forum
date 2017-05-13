CREATE TABLE public.post
(
  id UUID DEFAULT gen_random_uuid() PRIMARY KEY NOT NULL,
  created_at TIMESTAMP DEFAULT current_timestamp NOT NULL,
  text TEXT NOT NULL,
  like_count BIGINT DEFAULT 0,
  updated_at TIMESTAMP DEFAULT current_timestamp NOT NULL,
  user_id UUID NOT NULL REFERENCES "user" (id) ON DELETE CASCADE ON UPDATE CASCADE,
  is_deleted BOOLEAN DEFAULT FALSE  NOT NULL
);
CREATE UNIQUE INDEX post_id_uindex ON public.post (id);
