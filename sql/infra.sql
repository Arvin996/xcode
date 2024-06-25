/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 50732 (5.7.32-log)
 Source Host           : localhost:3306
 Source Schema         : xk-learn-infra

 Target Server Type    : MySQL
 Target Server Version : 50732 (5.7.32-log)
 File Encoding         : 65001

 Date: 25/06/2024 16:23:46
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for infra_database_conn_info
-- ----------------------------
CREATE DATABASE IF NOT EXISTS `xk-learn-infra`;
USE `xk-learn-infra`;
DROP TABLE IF EXISTS `infra_database_conn_info`;
CREATE TABLE `infra_database_conn_info`  (
                                             `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
                                             `database_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '数据库名',
                                             `url` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '数据库连接地址 ip:port 只支持mysql',
                                             `user_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '数据库用户名',
                                             `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '数据库密码',
                                             `create_user` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建用户',
                                             `create_time` datetime NOT NULL COMMENT '创建时间',
                                             `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
                                             PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for infra_table_info
-- ----------------------------
DROP TABLE IF EXISTS `infra_table_info`;
CREATE TABLE `infra_table_info`  (
                                     `database_id` int(11) NOT NULL COMMENT '表所属数据库',
                                     `table_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '表名',
                                     `table_pre` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '表名前缀',
                                     `entity_suff` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '实体类前缀',
                                     `create_user` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建用户',
                                     `create_time` datetime NOT NULL COMMENT '创建时间',
                                     `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
                                     PRIMARY KEY (`database_id`, `table_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
