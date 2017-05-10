DROP DATABASE IF EXISTS book_shop;
CREATE DATABASE book_shop;
USE book_shop;

DROP TABLE IF EXISTS book;
CREATE TABLE book
(
    id bigint(15) unsigned not null auto_increment primary key,
    name varchar(50) not null,
    price double not null,
    account int(10) not null
);

