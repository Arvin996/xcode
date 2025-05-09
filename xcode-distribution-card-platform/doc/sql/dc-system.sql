-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        5.7.32-log - MySQL Community Server (GPL)
-- 服务器操作系统:                      Win64
-- HeidiSQL 版本:                  12.5.0.6677
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- 导出 xcode-distribution-card-system 的数据库结构
CREATE DATABASE IF NOT EXISTS `xcode-distribution-card-system` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
USE `xcode-distribution-card-system`;

-- 导出  表 xcode-distribution-card-system.system_api 结构
CREATE TABLE IF NOT EXISTS `system_api` (
  `id` int(12) NOT NULL COMMENT '自增id',
  `product_name` varchar(50) NOT NULL DEFAULT 'xcode-distribution-card-system',
  `api_code` varchar(512) NOT NULL COMMENT '接口权限标识',
  `api_path` varchar(512) NOT NULL COMMENT '接口路径',
  `api_desc` varchar(512) DEFAULT NULL COMMENT '接口描述',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_api_code` (`product_name`,`api_code`) USING BTREE,
  UNIQUE KEY `uk_api_path` (`product_name`,`api_path`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统接口维护';

-- 正在导出表  xcode-distribution-card-system.system_api 的数据：~0 rows (大约)

-- 导出  表 xcode-distribution-card-system.system_dict_data 结构
CREATE TABLE IF NOT EXISTS `system_dict_data` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典编码',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '字典排序',
  `label` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '字典标签',
  `value` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '字典键值',
  `dict_type` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '字典类型',
  `status` char(1) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `color` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '颜色类型',
  `remark` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `is_deleted` char(1) CHARACTER SET utf8mb4 NOT NULL DEFAULT '0' COMMENT '是否删除 0未删除 1已删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(50) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user` varchar(50) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='字典数据表';

-- 正在导出表  xcode-distribution-card-system.system_dict_data 的数据：~0 rows (大约)

-- 导出  表 xcode-distribution-card-system.system_dict_type 结构
CREATE TABLE IF NOT EXISTS `system_dict_type` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典主键',
  `name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '字典类型名称',
  `type` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '字典类型码',
  `status` char(1) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '0 正常 1禁用',
  `remark` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `is_deleted` char(1) CHARACTER SET utf8mb4 NOT NULL DEFAULT '0' COMMENT '是否删除 0未删除 1已删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(50) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user` varchar(50) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_type` (`type`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='字典类型表';

-- 正在导出表  xcode-distribution-card-system.system_dict_type 的数据：~0 rows (大约)

-- 导出  表 xcode-distribution-card-system.system_menu 结构
CREATE TABLE IF NOT EXISTS `system_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '菜单名称',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '显示顺序',
  `parent_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '父菜单ID',
  `path` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '路由地址',
  `icon` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT '#' COMMENT '菜单图标',
  `component` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '组件路径',
  `component_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '组件名',
  `visible` char(1) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '0' COMMENT '是否可见 0可见 1不可见',
  `is_deleted` char(1) CHARACTER SET utf8mb4 NOT NULL DEFAULT '0' COMMENT '是否删除 0未删除 1已删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(50) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user` varchar(50) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='菜单权限表';

-- 正在导出表  xcode-distribution-card-system.system_menu 的数据：~0 rows (大约)

-- 导出  表 xcode-distribution-card-system.system_operate_log 结构
CREATE TABLE IF NOT EXISTS `system_operate_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `api_id` int(12) NOT NULL COMMENT '调用的api详情',
  `username` varchar(50) NOT NULL COMMENT '操作用户',
  `state` char(50) NOT NULL DEFAULT '0' COMMENT '操作结果 0成功 1失败',
  `fail_msg` text COMMENT '失败原因',
  `operate_time` datetime NOT NULL COMMENT '操作时间',
  PRIMARY KEY (`id`),
  KEY `key_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统操作日志表';

-- 正在导出表  xcode-distribution-card-system.system_operate_log 的数据：~0 rows (大约)

-- 导出  表 xcode-distribution-card-system.system_role 结构
CREATE TABLE IF NOT EXISTS `system_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `code` varchar(50) NOT NULL COMMENT '角色权限名',
  `name` varchar(50) NOT NULL COMMENT '系统角色名',
  `is_deleted` char(1) NOT NULL DEFAULT '0' COMMENT '是否删除 0未删除 1已删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(50) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user` varchar(50) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_code` (`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统用户角色';

-- 正在导出表  xcode-distribution-card-system.system_role 的数据：~0 rows (大约)

-- 导出  表 xcode-distribution-card-system.system_role_api 结构
CREATE TABLE IF NOT EXISTS `system_role_api` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `api_id` int(12) NOT NULL COMMENT '接口id',
  `role_id` int(12) NOT NULL COMMENT '角色id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统角色-接口关联表';

-- 正在导出表  xcode-distribution-card-system.system_role_api 的数据：~0 rows (大约)

-- 导出  表 xcode-distribution-card-system.system_role_menu 结构
CREATE TABLE IF NOT EXISTS `system_role_menu` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `role_id` int(12) NOT NULL COMMENT '角色id',
  `menu_id` int(12) NOT NULL COMMENT '菜单id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色-菜单绑定表';

-- 正在导出表  xcode-distribution-card-system.system_role_menu 的数据：~0 rows (大约)

-- 导出  表 xcode-distribution-card-system.system_station_notice 结构
CREATE TABLE IF NOT EXISTS `system_station_notice` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `title` text COMMENT '消息titile',
  `to_user` varchar(50) NOT NULL COMMENT '接收人',
  `message` text COMMENT '消息正文',
  `is_read` char(50) NOT NULL DEFAULT '0' COMMENT '0 未读 1已读',
  `type` char(1) NOT NULL DEFAULT '0' COMMENT '通知类型 0公告 1通知',
  `create_time` datetime DEFAULT NULL COMMENT '发送时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统通知站内信';

-- 正在导出表  xcode-distribution-card-system.system_station_notice 的数据：~0 rows (大约)

-- 导出  表 xcode-distribution-card-system.system_user 结构
CREATE TABLE IF NOT EXISTS `system_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `role_id` int(12) NOT NULL COMMENT '角色id',
  `username` varchar(50) NOT NULL COMMENT '登录用户名',
  `password` varchar(128) NOT NULL COMMENT '登录密码',
  `nickname` varchar(128) NOT NULL COMMENT '用户昵称',
  `sex` char(1) DEFAULT NULL COMMENT '0 男  1女 2未知',
  `age` int(3) DEFAULT NULL COMMENT '年龄',
  `mobile` char(10) DEFAULT NULL COMMENT '手机号',
  `email` varchar(256) DEFAULT NULL COMMENT '邮箱',
  `qq_webhook_token` text COMMENT 'qq webhook token',
  `ding_talk_webhook_token` text COMMENT '钉钉 webhook token',
  `feishu_webhook_token` text COMMENT '飞书 webhook token',
  `avatar` text COMMENT '头像',
  `status` char(1) NOT NULL DEFAULT '0' COMMENT '账号状态 0正常 1停用',
  `last_login_ip` varchar(128) DEFAULT NULL COMMENT '最后登录ip',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `is_deleted` char(1) NOT NULL DEFAULT '0' COMMENT '是否删除 0未删除 1已删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(50) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user` varchar(50) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='后台系统用户表';

-- 正在导出表  xcode-distribution-card-system.system_user 的数据：~0 rows (大约)

-- 导出  表 xcode-distribution-card-system.system_visit_log 结构
CREATE TABLE IF NOT EXISTS `system_visit_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '访问ID',
  `log_type` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '日志类型 登录 登出 踢下线等',
  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户编号',
  `username` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '用户账号',
  `result` tinyint(4) NOT NULL COMMENT '操作结果',
  `user_ip` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户 IP',
  `user_agent` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '浏览器 UA',
  `create_time` datetime DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统访问记录';

-- 正在导出表  xcode-distribution-card-system.system_visit_log 的数据：~0 rows (大约)

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
