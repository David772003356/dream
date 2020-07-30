/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2020/7/17 18:04:13                           */
/*==============================================================*/


drop table if exists dream_goods;

/*==============================================================*/
/* Table: dream_goods                                           */
/*==============================================================*/
create table dream_goods
(
    goods_id      varchar(32) not null comment '商品id',
    goods_name    varchar(64)    default NULL comment '商品名称',
    goods_price   decimal(15, 2) default NULL comment '商品价格',
    goods_status  tinyint        default NULL comment '商品状态',
    description   text           default NULL comment '商品描述',
    crt_user_name varchar(10)    default NULL comment '创建人名称',
    crt_user_id   varchar(32)    default NULL comment '创建人id',
    crt_time      datetime       default NULL comment '创建时间',
    upd_user_name varchar(10)    default NULL comment '更新人名称',
    upd_user_id   varchar(32)    default NULL comment '更新人id',
    upd_timie     datetime       default NULL comment '更新时间',
    primary key (goods_id)
);

alter table dream_goods
    comment '商品信息表';

CREATE TABLE `dream_user_info`
(
    `id`             varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
    `user_id`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户id',
    `user_name`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名称',
    `nick_name`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '昵称',
    `password`       varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
    `email`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
    `mobile`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电话',
    `account_status` tinyint(4)                                                   NULL DEFAULT NULL COMMENT '账号状态（0-未激活，1-激活）',
    `crt_time`       datetime(0)                                                  NULL DEFAULT NULL COMMENT '创建时间',
    `upd_time`       datetime(0)                                                  NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '用户信息'
  ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
