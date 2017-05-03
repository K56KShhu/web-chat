DROP DATABASE IF EXISTS temp;
CREATE DATABASE temp;
USE temp;

DROP TABLE IF EXISTS usergroup;
CREATE TABLE usergroup
(
    usergroup_id bigint(15) unsigned not null auto_increment primary key,
    name varchar(50) not null,
    content varchar(100) not null default '',
);


