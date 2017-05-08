CREATE TABLE public.post
(
  id UUID DEFAULT gen_random_uuid() PRIMARY KEY NOT NULL,
  create_date DATE DEFAULT current_date NOT NULL,
  text TEXT,
  image_link TEXT,
  like_count BIGINT DEFAULT 0,
  update_date DATE,
  user_id UUID NOT NULL,
  CONSTRAINT post_user_id_fk FOREIGN KEY (user_id) REFERENCES "user" (id) ON DELETE CASCADE ON UPDATE CASCADE
);
CREATE UNIQUE INDEX post_id_uindex ON public.post (id);