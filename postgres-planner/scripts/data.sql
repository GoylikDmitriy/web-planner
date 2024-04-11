CREATE TABLE employees (
   id bigserial primary key,
   last_name varchar,
   first_name varchar,
   middle_name varchar
);

CREATE TABLE tasks (
   id bigserial primary key,
   task varchar,
   start_date date,
   end_date date,
   completion_date date,
   completed boolean,
   employee_id bigint REFERENCES employees (id)
);

INSERT INTO employees (last_name, first_name, middle_name)
VALUES ('Иванов', 'Иван', 'Иванович'),
       ('Петров', 'Петр', 'Петрович');

INSERT INTO tasks (task, start_date, end_date, completion_date, completed, employee_id)
VALUES ('Задача 1', '2024-04-04', '2024-04-06', '2024-04-05', true, 1),
       ('Задача 2', '2024-04-09', '2024-04-30', NULL, false, 1),
       ('Задача 3', '2024-04-01', '2024-04-16', NULL, false, 1);

INSERT INTO tasks (task, start_date, end_date, completion_date, completed, employee_id)
VALUES ('Задача 4', '2024-04-03', '2024-05-04', NULL, false, 2),
       ('Задача 5', '2024-04-05', '2024-04-07', '2024-04-06', true, 2);