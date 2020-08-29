/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2020/8/8 16:35:42                            */
/*==============================================================*/


alter table comment_info
   drop primary key;

drop table if exists comment_info;

alter table dish_info
   drop primary key;

drop table if exists dish_info;

drop index menu_dish_index on menu_dish;

alter table menu_dish
   drop primary key;

drop table if exists menu_dish;

drop index menu_index on menu_info;

alter table menu_info
   drop primary key;

drop table if exists menu_info;

drop index menu_pop on menu_popular;

alter table menu_popular
   drop primary key;

drop table if exists menu_popular;

alter table picture_info
   drop primary key;

drop table if exists picture_info;

drop index tag_index on tag_info;

alter table tag_info
   drop primary key;

drop table if exists tag_info;

alter table user_info
   drop primary key;

drop table if exists user_info;

/*==============================================================*/
/* Table: comment_info                                          */
/*==============================================================*/
create table comment_info
(
   comment_id           varchar(64) not null,
   dish_id              varchar(64),
   user_id              varchar(64),
   comment_text         longtext,
   comment_time         datetime default current_timestamp,
   parent_user_id       varchar(64),
   comment_delete       int(1) default 0
);

alter table comment_info
   add primary key (comment_id);

/*==============================================================*/
/* Table: dish_info                                             */
/*==============================================================*/
create table dish_info
(
   dish_id              varchar(64) not null,
   user_id              varchar(64),
   tag_id               varchar(64),
   dish_name            varchar(32),
   dish_intro           varchar(128),
   dish_text            longtext,
   dish_create_time     datetime default current_timestamp,
   dish_update_time     datetime default current_timestamp,
   dish_delete          int(1) default 0
);

alter table dish_info
   add primary key (dish_id);

/*==============================================================*/
/* Table: menu_dish                                             */
/*==============================================================*/
create table menu_dish
(
   menu_id              varchar(64) not null,
   dish_id              varchar(64),
   collection_time      datetime default current_timestamp,
   collection_delete    int(1) default 0
);

alter table menu_dish
   add primary key (menu_id);

/*==============================================================*/
/* Index: menu_dish_index                                       */
/*==============================================================*/
create index menu_dish_index on menu_dish
(
   menu_id
);

/*==============================================================*/
/* Table: menu_info                                             */
/*==============================================================*/
create table menu_info
(
   menu_id              varchar(64) not null,
   user_id              varchar(64),
   menu_name            varchar(64),
   menu_delete          int(1) default 0
);

alter table menu_info
   add primary key (menu_id);

/*==============================================================*/
/* Index: menu_index                                            */
/*==============================================================*/
create index menu_index on menu_info
(
   menu_id
);

/*==============================================================*/
/* Table: menu_popular                                          */
/*==============================================================*/
create table menu_popular
(
   dish_id              varchar(64) not null,
   dish_like            int default 0,
   dish_collect         int default 0,
   dish_click           int default 0
);

alter table menu_popular
   add primary key (dish_id);

/*==============================================================*/
/* Index: menu_pop                                              */
/*==============================================================*/
create index menu_pop on menu_popular
(
   dish_id,
   dish_like,
   dish_collect,
   dish_click
);

/*==============================================================*/
/* Table: picture_info                                          */
/*==============================================================*/
create table picture_info
(
   dish_id              varchar(64) not null,
   pic_address          longtext
);

alter table picture_info
   add primary key (dish_id);

/*==============================================================*/
/* Table: tag_info                                              */
/*==============================================================*/
create table tag_info
(
   tag_id               varchar(64) not null,
   tag_name             varchar(24),
   tag_intro            varchar(128),
   tag_delete           int(1) default 0
);

alter table tag_info
   add primary key (tag_id);

/*==============================================================*/
/* Index: tag_index                                             */
/*==============================================================*/
create index tag_index on tag_info
(
   tag_id
);

/*==============================================================*/
/* Table: user_info                                             */
/*==============================================================*/
create table user_info
(
   user_id              varchar(64) not null,
   user_name            varchar(12),
   user_nickname        varchar(24),
   user_password        varchar(12),
   user_mail            varchar(24),
   user_picture         varchar(64) default null,
   user_create_time     datetime default current_timestamp,
   user_update_time     datetime default current_timestamp,
   user_delete          int(1) default 0
);

alter table user_info
   add primary key (user_id);

