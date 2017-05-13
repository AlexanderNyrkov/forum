CREATE TABLE public."user"
(
  id UUID DEFAULT gen_random_uuid() PRIMARY KEY NOT NULL,
  login TEXT NOT NULL,
  name TEXT NOT NULL,
  password TEXT NOT NULL,
  email TEXT NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  is_deleted BOOLEAN DEFAULT FALSE  NOT NULL
);
CREATE UNIQUE INDEX user_id_uindex ON public."user" (id);
CREATE UNIQUE INDEX user_login_uindex ON public."user" (login);
CREATE UNIQUE INDEX user_email_uindex ON public."user" (email);

INSERT INTO "user" (login, name, password, email) VALUES (
    'shurik',
  'alex',
  'shurik3',
  'shurik@kek'
)