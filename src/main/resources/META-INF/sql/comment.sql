CREATE TABLE public.comment
(
  id UUID DEFAULT gen_random_uuid() PRIMARY KEY NOT NULL,
  user_id UUID NOT NULL REFERENCES "user" (id),
  like_count BIGINT DEFAULT 0,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  text TEXT NOT NULL,
  post_id UUID NOT NULL REFERENCES post (id),
  is_deleted BOOLEAN DEFAULT FALSE
);
CREATE UNIQUE INDEX comment_id_uindex ON public.comment (id);