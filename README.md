# ormissia_blog ![](https://badgen.net/badge/github/Blog/blue?label=JAVA)

## 我的博客后端项目

#### TODO
- 博客表需要添加字段区分发布和草稿的状态
---
### 创建表结构的SQL
###### 按顺序创建
```mysql
-- 用户表
CREATE TABLE user
(
    `pk_user_id`              varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户ID',
    `user_role_id`            varchar(100) DEFAULT NULL COMMENT '用户权限ID',
    `username`                varchar(100)                                            NOT NULL COMMENT '用户名',
    `password`                varchar(100)                                            NOT NULL COMMENT '密码',
    `email`                   varchar(100) DEFAULT NULL COMMENT '邮箱',
    `phone_number`            varchar(100) DEFAULT NULL COMMENT '手机号',
    `head_portrait`           varchar(100) DEFAULT NULL COMMENT '头像',
    `register_date`           datetime                                                NOT NULL COMMENT '用户注册时间',
    `last_edit_password_date` datetime     DEFAULT NULL COMMENT '最近一次修改密码的日期',
    PRIMARY KEY (`pk_user_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
```
```mysql
-- 类型表
CREATE TABLE type
(
    `pk_type_id` int          NOT NULL AUTO_INCREMENT COMMENT '类型ID',
    `type_name`  varchar(100) NOT NULL COMMENT '类型名称',
    PRIMARY KEY (`pk_type_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
```
```mysql
-- 标签表
CREATE TABLE tag
(
    `pk_tag_id` int          NOT NULL AUTO_INCREMENT COMMENT '标签ID',
    `tag_name`  varchar(100) NOT NULL COMMENT '标签名称',
    PRIMARY KEY (`pk_tag_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
```
```mysql
-- 博客信息表
CREATE TABLE blog_information
(
    `pk_blog_id`     varchar(100) NOT NULL COMMENT '博客ID',
    `user_id`        varchar(100) NOT NULL COMMENT '博客所属用户的ID',
    `type_id`        int          DEFAULT NULL COMMENT '类型ID，默认为-1，即无类型',
    `top_image`      varchar(100) DEFAULT NULL COMMENT '博客首图',
    `blog_title`     varchar(100) NOT NULL COMMENT '博客标题',
    `description`    varchar(255) NOT NULL COMMENT '博客描述',
    `create_date`    varchar(100) NOT NULL COMMENT '创建时间',
    `last_edit_date` varchar(100) DEFAULT NULL COMMENT '上一次修改时间',
    `delete_date`    varchar(100) DEFAULT NULL COMMENT '删除时间',
    is_deleted       tinyint(1)   null comment '是否标记为删除，true或NULL-正常，false-标记为删除',
    is_recommended   tinyint(1)   null comment '是否推荐，true或NULL-推荐，false-正常',
    is_published     tinyint(1)   null comment '是否发布，true或NULL-发布，1-草稿',
    `visits`         int          DEFAULT NULL COMMENT '访客数量',
    PRIMARY KEY (`pk_blog_id`),
    KEY `blog_FK` (`user_id`),
    KEY `blog_FK_1` (`type_id`),
    CONSTRAINT `blog_FK` FOREIGN KEY (`user_id`) REFERENCES `user` (`pk_user_id`) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT `blog_FK_1` FOREIGN KEY (`type_id`) REFERENCES `type` (`pk_type_id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
```
```mysql
-- 博客内容表
CREATE TABLE `blog_content`
(
    `blog_id`      varchar(100) NOT NULL COMMENT '博客ID，外键关联blog_information表的blog_id',
    `blog_content` text COMMENT '博客内容',
    `is_deleted`   int DEFAULT NULL COMMENT '是否被删除，0或NULL-否，1-被删除',
    KEY `blog_content_FK` (`blog_id`),
    CONSTRAINT `blog_content_FK` FOREIGN KEY (`blog_id`) REFERENCES `blog_information` (`pk_blog_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
```
```mysql
-- 博客标签关系表
CREATE TABLE `blog_tag`
(
    `blog_id`    varchar(100) NOT NULL COMMENT '博客ID',
    `tag_id`     int          NOT NULL COMMENT '标签ID',
    `is_deleted` int DEFAULT NULL COMMENT '标记博客是否被删除，0或NULL-否，1-被删除，当博客标签删除时，删除对应关系，当博客删除时，修改该字段',
    KEY `blog_tag_FK` (`blog_id`),
    KEY `blog_tag_FK_1` (`tag_id`),
    CONSTRAINT `blog_tag_FK` FOREIGN KEY (`blog_id`) REFERENCES `blog_information` (`pk_blog_id`) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `blog_tag_FK_1` FOREIGN KEY (`tag_id`) REFERENCES `tag` (`pk_tag_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
```