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
   goods_id             varchar(32) not null comment '商品id',
   goods_name           varchar(64) default NULL comment '商品名称',
   goods_price          decimal(15,2) default NULL comment '商品价格',
   goods_status         tinyint default NULL comment '商品状态',
   description          text default NULL comment '商品描述',
   crt_user_name        varchar(10) default NULL comment '创建人名称',
   crt_user_id          varchar(32) default NULL comment '创建人id',
   crt_time             datetime default NULL comment '创建时间',
   upd_user_name        varchar(10) default NULL comment '更新人名称',
   upd_user_id          varchar(32) default NULL comment '更新人id',
   upd_timie            datetime default NULL comment '更新时间',
   primary key (goods_id)
);

alter table dream_goods comment '商品信息表';

