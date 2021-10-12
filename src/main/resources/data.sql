drop table USERS if exists;
create table USERS (
    id integer,
    user_name varchar,
    user_password varchar,
    user_token varchar
);

insert into USERS (USER_NAME, USER_PASSWORD, USER_TOKEN) values (
    'gab', '$2a$12$XEA30Qpw5I/2.2KVsGEhwOc33L32MK/cGDFKTfYwmwLrY2j5QUWVO', 'test'
);