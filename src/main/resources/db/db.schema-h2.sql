DROP TABLE IF EXISTS t_user;
-- 用户表
CREATE TABLE t_user
(
    id               bigint                  NOT NULL AUTO_INCREMENT COMMENT 'id',
    username         varchar(30)  DEFAULT '' NOT NULL COMMENT '账号',
    name             varchar(50)  DEFAULT '' NOT NULL COMMENT '名称',
    phone            varchar(30)  DEFAULT '' COMMENT '手机号码',
    code             varchar(100) DEFAULT '' COMMENT '编码',
    gender           char(1)      default '' COMMENT '性别',
    active           tinyint      DEFAULT 1 COMMENT '是否启用,1-启用;0-不启用',
    password         varchar(200) DEFAULT '' COMMENT '密码',
    user_type        tinyint      default 0 comment '用户类型，0：普通用户， 1：管理员',
    last_access_time datetime COMMENT '最近访问时间',
    created_by       int          DEFAULT -1 NOT NULL COMMENT '创建人',
    created_time     datetime COMMENT '创建时间',
    updated_by       int          DEFAULT -1 NOT NULL COMMENT '更新人',
    updated_time     datetime COMMENT '更新时间',
    deleted_ind      tinyint      DEFAULT 0 COMMENT '是否已删除,1-是;0-否',
    deleted_by       int          DEFAULT -1 NOT NULL COMMENT '删除人',
    deleted_time     datetime COMMENT '删除时间',
    PRIMARY KEY (id)
);




