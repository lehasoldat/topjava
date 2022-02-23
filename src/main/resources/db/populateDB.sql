DELETE
FROM user_roles;
DELETE
FROM meals;
DELETE
FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin'),
       ('Guest', 'guest@gmail.com', 'guest');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals(date_time, description, calories, user_id)
VALUES (TIMESTAMP '2022-02-20 6:20:00', 'Завтрак', 500, 100000),
       (TIMESTAMP '2022-02-20 11:50:00', 'Обед', 1100, 100000),
       (TIMESTAMP '2022-02-20 19:00:00', 'Ужин', 400, 100000),
       (TIMESTAMP '2022-02-20 23:00:00', 'Полуночный перекус', 100, 100000),
       (TIMESTAMP '2022-02-21 6:30:00', 'Завтрак', 400, 100000),
       (TIMESTAMP '2022-02-21 12:00:00', 'Обед', 1200, 100000),
       (TIMESTAMP '2022-02-21 19:30:00', 'Ужин', 400, 100000),
       (TIMESTAMP '2022-02-22 6:20:00', 'Завтрак', 300, 100000),
       (TIMESTAMP '2022-02-22 11:40:00', 'Обед', 1400, 100000),
       (TIMESTAMP '2022-02-22 20:00:00', 'Ужин', 800, 100000),
       (TIMESTAMP '2022-02-21 9:00:00', 'Завтрак', 200, 100001),
       (TIMESTAMP '2022-02-21 14:00:00', 'Обед', 1300, 100001),
       (TIMESTAMP '2022-02-21 20:30:00', 'Ужин', 400, 100001);