CREATE TABLE public."user"
(
  id UUID DEFAULT gen_random_uuid() PRIMARY KEY NOT NULL,
  login TEXT NOT NULL,
  name TEXT NOT NULL,
  password TEXT NOT NULL,
  email TEXT NOT NULL,
  registry_date DATE DEFAULT current_date NOT NULL
);
CREATE UNIQUE INDEX user_id_uindex ON public."user" (id);
CREATE UNIQUE INDEX user_login_uindex ON public."user" (login);
CREATE UNIQUE INDEX user_email_uindex ON public."user" (email);