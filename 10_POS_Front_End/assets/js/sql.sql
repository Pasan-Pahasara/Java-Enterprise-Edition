show databases;
drop database if exists JEPOS;
show databases;
create database if not exists JEPOS;
use JEPOS;
create table if not exists Customer(
    id      varchar(45) not null,
    name    varchar(45) null,
    address text        null,
    salary  double      null,
    constraint primary key (id)
    );
desc customer;
select * from customer;
delete from Customer where id=?;
DELETE FROM customer WHERE id = ?;