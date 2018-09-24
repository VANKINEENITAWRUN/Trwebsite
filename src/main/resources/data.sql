-- REPLACE INTO role VALUES (1,'ADMIN');

insert into role  values (1,'ADMIN') on conflict (role_id) do nothing;