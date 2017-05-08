CREATE TABLE public.comment
(
  id UUID DEFAULT gen_random_uuid() PRIMARY KEY NOT NULL,
  author UUID NOT NULL,
  create_date DATE DEFAULT current_date NOT NULL,
  text TEXT NOT NULL,
  like_count BIGINT DEFAULT 0,
  update_date DATE,
  post UUID NOT NULL,
  CONSTRAINT comment_post_id_fk FOREIGN KEY (post) REFERENCES post (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT comment_user_id_fk FOREIGN KEY (author) REFERENCES "user" (id) ON DELETE CASCADE ON UPDATE CASCADE
);
CREATE UNIQUE INDEX comment_id_uindex ON public.comment (id);