-- show databases;
-- drop database if exists JEPOS;
-- show databases;
-- create database if not exists JEPOS;
-- use JEPOS;
-- create table if not exists Customer(
--     id      varchar(45) not null,
--     name    varchar(45) null,
--     address text        null,
--     salary  double      null,
--     constraint primary key (id)
--     );
-- desc customer;
-- select * from customer;
-- delete from Customer where id=?;
-- DELETE FROM customer WHERE id = ?;

DROP DATABASE IF EXISTS JEPOS;
CREATE DATABASE IF NOT EXISTS JEPOS;
SHOW DATABASES;
USE JEPOS;

DROP TABLE IF EXISTS Customer;
CREATE TABLE Customer
(
    id      VARCHAR(9)  NOT NULL,
    name    VARCHAR(45) NULL,
    address TEXT        NULL,
    salary  DOUBLE      NULL,
    CONSTRAINT PRIMARY KEY (id)
);

SHOW TABLES;
DESCRIBE Customer;

DROP TABLE IF EXISTS Item;
CREATE TABLE Item
(
    code        VARCHAR(9)  NOT NULL,
    description VARCHAR(45) NULL,
    qtyOnHand   INT         NULL,
    unitPrice   DOUBLE      NULL,
    CONSTRAINT PRIMARY KEY (code)
);

SHOW TABLES;
DESCRIBE Item;

DROP TABLE IF EXISTS Orders;
CREATE TABLE Orders
(
    oid   VARCHAR(9) NOT NULL,
    date  DATE,
    cusId VARCHAR(9) NOT NULL,
    CONSTRAINT PRIMARY KEY (oid),
    CONSTRAINT FOREIGN KEY (cusId) REFERENCES Customer(id) ON DELETE CASCADE ON UPDATE CASCADE
);

SHOW TABLES;
DESCRIBE Orders;

DROP TABLE IF EXISTS OrderDetails;
CREATE TABLE OrderDetails
(
    oid   VARCHAR(9) NOT NULL,
    code  VARCHAR(9) NOT NULL,
    qty INT,
    price DOUBLE,
    CONSTRAINT PRIMARY KEY (oid),
    CONSTRAINT FOREIGN KEY (oid) REFERENCES Orders (oid) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FOREIGN KEY (code) REFERENCES Item (code) ON DELETE CASCADE ON UPDATE CASCADE
);

SHOW TABLES;
DESCRIBE OrderDetails;


SELECT oid FROM Orders ORDER BY oid DESC LIMIT 1;