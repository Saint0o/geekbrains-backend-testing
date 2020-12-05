create table products (id bigserial primary key, title varchar(255), price int);
insert into products (title, price)
values
('Milk', 123),
('Bread',3543),
('Cheese', 2341);