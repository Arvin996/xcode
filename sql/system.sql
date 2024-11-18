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


-- 导出 xcode-system 的数据库结构
CREATE DATABASE IF NOT EXISTS `xcode-system` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
USE `xcode-system`;

-- 导出  表 xcode-system.system_client 结构
CREATE TABLE IF NOT EXISTS `system_client` (
  `client_id` varchar(30) NOT NULL COMMENT '客户端id',
  `client_key` varchar(50) DEFAULT NULL COMMENT '客户端key',
  `client_secret` varchar(50) DEFAULT NULL COMMENT '客户端密钥',
  `grant_type` varchar(50) DEFAULT NULL COMMENT '授权类型',
  `create_user` varchar(30) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统客户端表';

-- 正在导出表  xcode-system.system_client 的数据：~0 rows (大约)

-- 导出  表 xcode-system.system_dict 结构
CREATE TABLE IF NOT EXISTS `system_dict` (
  `code` varchar(50) NOT NULL COMMENT '字典id',
  `name` varchar(50) NOT NULL COMMENT '字典值',
  `par_id` varchar(50) NOT NULL COMMENT '父字典id,一级为"##"',
  `note` varchar(20) DEFAULT NULL COMMENT '字典表备注，如排序',
  `pad` text COMMENT '填充字段',
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统字典表';

-- 正在导出表  xcode-system.system_dict 的数据：~0 rows (大约)

-- 导出  表 xcode-system.system_resource 结构
CREATE TABLE IF NOT EXISTS `system_resource` (
  `id` bigint(15) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `resource_code` varchar(50) NOT NULL COMMENT '资源路径 唯一',
  `resource_name` varchar(50) NOT NULL COMMENT '资源名称',
  `status` char(1) NOT NULL DEFAULT '0' COMMENT '资源是否启用，0启用 1未启用',
  `create_user` varchar(30) DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统资源表';

-- 正在导出表  xcode-system.system_resource 的数据：~0 rows (大约)

-- 导出  表 xcode-system.system_role 结构
CREATE TABLE IF NOT EXISTS `system_role` (
  `id` int(12) NOT NULL COMMENT '自增id',
  `name` varchar(30) NOT NULL COMMENT '角色名称',
  `status` char(1) NOT NULL DEFAULT '0' COMMENT '0 启用 1未启用',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统角色表';

-- 正在导出表  xcode-system.system_role 的数据：~0 rows (大约)

-- 导出  表 xcode-system.system_role_resource 结构
CREATE TABLE IF NOT EXISTS `system_role_resource` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `resource_id` int(12) NOT NULL COMMENT '资源id',
  `role_id` int(12) NOT NULL COMMENT '角色id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色资源表';

-- 正在导出表  xcode-system.system_role_resource 的数据：~0 rows (大约)

-- 导出  表 xcode-system.system_third_user 结构
CREATE TABLE IF NOT EXISTS `system_third_user` (
  `id` int(15) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `user_id` int(12) NOT NULL COMMENT 'system_user表中的用户id',
  `union_id` varchar(100) DEFAULT NULL COMMENT '微信unionid',
  `nickname` varchar(100) DEFAULT NULL COMMENT '用户昵称',
  `name` varchar(30) DEFAULT NULL COMMENT '真实姓名',
  `phone` char(11) DEFAULT NULL COMMENT '手机号',
  `avatar` text COMMENT '用户头像',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统三方用户表';

-- 正在导出表  xcode-system.system_third_user 的数据：~0 rows (大约)

-- 导出  表 xcode-system.system_user 结构
CREATE TABLE IF NOT EXISTS `system_user` (
  `id` int(12) NOT NULL COMMENT '自增id',
  `username` varchar(30) NOT NULL COMMENT '用户名，不重复',
  `password` varchar(50) NOT NULL COMMENT '登陆密码',
  `nickname` varchar(50) DEFAULT NULL COMMENT '用户昵称',
  `userpic` text COMMENT '用户头像',
  `qq` varchar(20) DEFAULT NULL COMMENT 'qq',
  `wx` varchar(50) DEFAULT NULL COMMENT '微信',
  `mobile` char(11) DEFAULT NULL COMMENT '手机号',
  `status` char(1) DEFAULT '1' COMMENT '用户状态 0在线 1离线 2封禁',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统用户表';

-- 正在导出表  xcode-system.system_user 的数据：~0 rows (大约)

-- 导出  表 xcode-system.system_user_role 结构
CREATE TABLE IF NOT EXISTS `system_user_role` (
  `id` int(12) NOT NULL COMMENT '自增id',
  `user_id` int(12) NOT NULL COMMENT '用户id',
  `role_id` int(12) NOT NULL COMMENT '角色id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统用户角色表';

-- 正在导出表  xcode-system.system_user_role 的数据：~0 rows (大约)

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
