
ALTER TABLE `smsdb`.`T_USER` ADD(`CREATE_DATE` DATETIME NOT NULL COMMENT '注册时间');
alter table smsdb.t_message add column NUMBER varchar(50) not null comment "单个手机号码,或者电话号码";
alter table smsdb.t_message_sent add column NUMBER varchar(50) not null comment "单个手机号码,或者电话号码";
alter table smsdb.t_message add column SUBJECT varchar(50) comment "短信的主题";
alter table smsdb.t_message_sent add column SUBJECT varchar(50) comment "短信的主题";
