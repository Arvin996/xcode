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


-- 导出 xk-learn-infra 的数据库结构
CREATE DATABASE IF NOT EXISTS `xk-learn-infra` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `xk-learn-infra`;

-- 导出  表 xk-learn-infra.infra_database_conn_info 结构
CREATE TABLE IF NOT EXISTS `infra_database_conn_info` (
                                                          `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
                                                          `database_name` varchar(50) NOT NULL COMMENT '数据库名',
                                                          `url` varchar(100) NOT NULL COMMENT '数据库连接地址 ip:port 只支持mysql',
                                                          `user_name` varchar(30) NOT NULL COMMENT '数据库用户名',
                                                          `password` varchar(50) NOT NULL COMMENT '数据库密码',
                                                          `create_user` varchar(20) NOT NULL COMMENT '创建用户',
                                                          `create_time` datetime NOT NULL COMMENT '创建时间',
                                                          `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                                                          PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- 数据导出被取消选择。

-- 导出  表 xk-learn-infra.infra_sys_files 结构
CREATE TABLE IF NOT EXISTS `infra_sys_files` (
                                                 `id` varchar(32) NOT NULL COMMENT '文件id  md5值',
                                                 `file_name` varchar(100) NOT NULL COMMENT '文件名',
                                                 `bucket` varchar(30) NOT NULL COMMENT '文件存在于minio中的桶',
                                                 `file_path` varchar(512) NOT NULL COMMENT '文件在minio中的访问路径',
                                                 `file_type` varchar(20) NOT NULL COMMENT '文件类型',
                                                 `file_size` bigint(20) NOT NULL COMMENT '文件大小',
                                                 `create_user` varchar(50) NOT NULL COMMENT '上传人',
                                                 `create_time` datetime DEFAULT NULL COMMENT '上传时间',
                                                 PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- 数据导出被取消选择。

-- 导出  表 xk-learn-infra.infra_sys_files_process 结构
CREATE TABLE IF NOT EXISTS `infra_sys_files_process` (
                                                         `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '任务id',
                                                         `file_id` varchar(32) NOT NULL COMMENT '对应于file表中的id 表示要进行转码',
                                                         `file_name` varchar(128) NOT NULL COMMENT '文件名',
                                                         `bucket` varchar(30) NOT NULL COMMENT 'minio桶',
                                                         `file_path` varchar(512) NOT NULL COMMENT '文件在minio中的访问路劲',
                                                         `status` char(1) NOT NULL DEFAULT '0' COMMENT '状态 0待处理 1处理中 2处理成功 3处理失败',
                                                         `fail_count` int(2) DEFAULT '0' COMMENT '失败次数',
                                                         `upload_time` datetime DEFAULT NULL COMMENT '上传时间',
                                                         `finish_time` datetime DEFAULT NULL COMMENT '完成时间',
                                                         `err_msg` text COMMENT '失败原因',
                                                         PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- 数据导出被取消选择。

-- 导出  表 xk-learn-infra.infra_table_info 结构
CREATE TABLE IF NOT EXISTS `infra_table_info` (
                                                  `database_id` int(11) NOT NULL COMMENT '表所属数据库',
                                                  `table_name` varchar(30) NOT NULL COMMENT '表名',
                                                  `table_pre` varchar(20) DEFAULT NULL COMMENT '表名前缀',
                                                  `entity_suff` varchar(20) DEFAULT NULL COMMENT '实体类前缀',
                                                  `create_user` varchar(20) NOT NULL COMMENT '创建用户',
                                                  `create_time` datetime NOT NULL COMMENT '创建时间',
                                                  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                                                  PRIMARY KEY (`database_id`,`table_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- 数据导出被取消选择。

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
