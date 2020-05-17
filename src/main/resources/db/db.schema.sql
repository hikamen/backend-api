SET SESSION FOREIGN_KEY_CHECKS = 0;

DROP DATABASE IF EXISTS `backend-db`;

CREATE DATABASE IF NOT EXISTS `backend-db`
    DEFAULT CHARACTER SET `utf8mb4`
    DEFAULT COLLATE `utf8mb4_unicode_ci`;

USE `backend-db`;

/* Drop Tables */

DROP TABLE IF EXISTS t_user;

-- 用户表
CREATE TABLE t_user
(
    id               bigint unsigned         NOT NULL AUTO_INCREMENT COMMENT 'id',
    username         varchar(30)  DEFAULT '' NOT NULL COMMENT '账号',
    name             varchar(50)  DEFAULT '' NOT NULL COMMENT '名称',
    phone            varchar(30)  DEFAULT '' COMMENT '手机号码',
    code             varchar(100) DEFAULT '' COMMENT '编码',
    active           tinyint      DEFAULT 1 COMMENT '是否启用,1-启用;0-不启用',
    password         varchar(200) DEFAULT '' COMMENT '密码',
    admin_ind        tinyint      default 0 comment '是否管理员',
    last_access_time datetime COMMENT '最近访问时间',
    created_by       int          DEFAULT -1 NOT NULL COMMENT '创建人',
    created_time     datetime COMMENT '创建时间',
    updated_by       int          DEFAULT -1 NOT NULL COMMENT '更新人',
    updated_time     datetime COMMENT '更新时间',
    deleted_ind      tinyint      DEFAULT 0 COMMENT '是否已删除,1-是;0-否',
    deleted_by       int          DEFAULT -1 NOT NULL COMMENT '删除人',
    deleted_time     datetime COMMENT '删除时间',
    PRIMARY KEY (id),
    index idx_code (`code`)
) COMMENT = '用户表';

CREATE TABLE t_demo
(
    id    BIGINT(20)  NOT NULL COMMENT '主键ID',
    name  VARCHAR(30) NULL DEFAULT NULL COMMENT '姓名',
    age   INT(11)     NULL DEFAULT NULL COMMENT '年龄',
    email VARCHAR(50) NULL DEFAULT NULL COMMENT '邮箱',
    PRIMARY KEY (id)
);




