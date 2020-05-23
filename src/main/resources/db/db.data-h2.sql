truncate table t_user;
-- 12345678
insert into t_user (id, username, name, phone, code, gender, active, password, user_type)
values (1, 'admin', '管理员', '13800138000', 'admin', 'U', 1,
        'ef797c8118f02dfb649607dd5d3f8c7623048c9c063d532cc95c5ed7a898a64f', 1);
insert into t_user (id, username, name, phone, code, gender, active, password, user_type, last_access_time)
values (2, 'user2', '用户2', '13800138000', 'code2', 'F', 0, '12345678', 1, now()),
       (3, 'user3', '用户3', '13800138000', 'code3', 'M', 1, '12345678', 0, now()),
       (4, 'user4', '用户4', '13800138000', 'code4', 'U', 0, '12345678', 1, now()),
       (5, 'user5', '用户5', '13800138000', 'code5', 'F', 1, '12345678', 1, now()),
       (6, 'user6', '用户6', '13800138000', 'code6', 'M', 1, '12345678', 1, now()),
       (7, 'user7', '用户7', '13800138000', 'code7', 'U', 1, '12345678', 0, now()),
       (8, 'user8', '用户8', '13800138000', 'code8', 'F', 1, '12345678', 1, now()),
       (9, 'user9', '用户9', '13800138000', 'code9', 'M', 1, '12345678', 1, now()),
       (10, 'user10', '用户10', '13800138000', 'code10', 'U', 1, '12345678', 1, now());

truncate table t_demo;
insert into t_demo (id, username, name, phone, code, gender, active, password, user_type, last_access_time)
values (1, 'demo1', '演示1', '13800138000', 'code1', 'U', 1, '12345678', 1, now()),
       (2, 'demo2', '演示2', '13800138000', 'code2', 'F', 0, '12345678', 1, now()),
       (3, 'demo3', '演示3', '13800138000', 'code3', 'M', 1, '12345678', 0, now()),
       (4, 'demo4', '演示4', '13800138000', 'code4', 'U', 0, '12345678', 1, now()),
       (5, 'demo5', '演示5', '13800138000', 'code5', 'F', 1, '12345678', 1, now()),
       (6, 'demo6', '演示6', '13800138000', 'code6', 'M', 1, '12345678', 1, now()),
       (7, 'demo7', '演示7', '13800138000', 'code7', 'U', 1, '12345678', 0, now()),
       (8, 'demo8', '演示8', '13800138000', 'code8', 'F', 1, '12345678', 1, now()),
       (9, 'demo9', '演示9', '13800138000', 'code9', 'M', 1, '12345678', 1, now()),
       (10, 'demo10', '演示10', '13800138000', 'code10', 'U', 1, '12345678', 1, now());





