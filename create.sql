DROP DATABASE IF EXISTS temp2648i;
CREATE DATABASE temp2648i;
USE temp2648i;

DROP TABLE IF EXISTS user;
CREATE TABLE user
(
    user_id bigint(15) unsigned not null auto_increment primary key,
    username varchar(50) not null,
    password varchar(255) not null,
    sex varchar(20) not null,
    email varchar(100) not null,
    status tinyint(2) not null default '0',
    created timestamp not null default current_timestamp
);

DROP TABLE IF EXISTS user_role;
CREATE TABLE user_role
(
    user_role_id bigint(15) unsigned not null auto_increment primary key,
    user_id bigint(15) unsigned not null,
    role varchar(50) not null
);

DROP TABLE IF EXISTS topic;
CREATE TABLE topic
(
    topic_id bigint(15) unsigned not null auto_increment primary key,
    title varchar(100) not null,
    description varchar(255) not null default '',
    is_private tinyint(2) not null default '0',
    creator_id bigint(15) unsigned not null,
    last_modify_id bigint(15) unsigned not null,
    reply_account bigint(15) not null default '0',
    last_time timestamp not null default current_timestamp,
    created timestamp not null default current_timestamp
);

DROP TABLE IF EXISTS reply;
CREATE TABLE reply
(
    reply_id bigint(15) unsigned not null auto_increment primary key,
    topic_id bigint(15) unsigned not null,
    user_id bigint(15) unsigned not null,
    content varchar(255) not null default '',
    content_type tinyint(2) not null,
    created timestamp not null default current_timestamp
);

DROP TABLE IF EXISTS usergroup;
CREATE TABLE usergroup
(
    usergroup_id bigint(15) unsigned not null auto_increment primary key,
    name varchar(50) not null,
    description varchar(100) not null default '',
    population int(8) unsigned not null default '0',
    created timestamp not null default current_timestamp
);

DROP TABLE IF EXISTS user_usergroup;
CREATE TABLE user_usergroup
(
    user_usergroup_id bigint(15) unsigned not null auto_increment primary key,
    user_id bigint(15) unsigned not null,
    usergroup_id bigint(15) unsigned not null
);

DROP TABLE IF EXISTS topic_usergroup;
CREATE TABLE topic_usergroup
(
    topic_usergroup_id bigint(15) unsigned not null auto_increment primary key,
    topic_id bigint(15) unsigned not null,
    usergroup_id bigint(15) unsigned not null
);

DROP TABLE IF EXISTS upload_file;
CREATE TABLE upload_file
(
    upload_file_id bigint(15) unsigned not null auto_increment primary key,
    apply tinyint(2) not null,
    user_id bigint(15) unsigned not null,
    topic_id bigint(15) unsigned not null,
    relative_path varchar(255) not null,
    created timestamp not null default current_timestamp
);

DROP TABLE IF EXISTS report;
CREATE TABLE report
(
    report_id bigint(15) unsigned not null auto_increment primary key,
    user_id bigint(15) unsigned not null,
    content_id bigint(15) unsigned not null,
    content_type tinyint(2) not null,
    reason varchar(100) not null default '',
    created timestamp not null default current_timestamp
);

DROP TABLE IF EXISTS remember;
CREATE TABLE remember
(
    remember_id varchar(100) not null primary key,
    username varchar(50) not null,
    created timestamp not null default current_timestamp
);

INSERT INTO user (username, password, sex, email, status) VALUES ('root', '10000:0479eb458ffb8583e409bc11b72205aa5da8b861b40b2fe2:14ccb638c48cc04f3da6b218a5c10f5faf0a2eecb368d393', 'male', 'zkyyo@outlook.com', 1);
INSERT INTO user_role (user_id, role) VALUES (1, 'user');
INSERT INTO user_role (user_id, role) VALUES (1, 'admin');
INSERT INTO user_role (user_id, role) VALUES (1, 'root');

INSERT INTO user (username, password, sex, email, status) VALUES ('admin', '10000:089fe438a7132cacaf9457951f42bf4ccac6b2f725469242:4adce36a484d6e09b196e16bd18ca3bb2a6f20ad5e7bfe12', 'male', 'admin@admin.com', 1);
INSERT INTO user_role (user_id, role) VALUES (2, 'user');
INSERT INTO user_role (user_id, role) VALUES (2, 'admin');

INSERT INTO user (username, password, sex, email, status) VALUES ('user', '10000:80c0c46774ce2371f65251ce1aa260b625a7d2dbe1b6a836:d1affff4111120a5a913be7cf772a612005de3bb778b7174', 'male', 'user@user.com', 1);
INSERT INTO user_role (user_id, role) VALUES (3, 'user');

