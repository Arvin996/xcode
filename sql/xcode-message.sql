/*
 Navicat Premium Dump SQL

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 80029 (8.0.29)
 Source Host           : localhost:3306
 Source Schema         : xcode-message

 Target Server Type    : MySQL
 Target Server Version : 80029 (8.0.29)
 File Encoding         : 65001

 Date: 09/03/2025 13:59:18
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for message_channel
-- ----------------------------
DROP TABLE IF EXISTS `message_channel`;
CREATE TABLE `message_channel`  (
  `code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '渠道code 如短信sms 微信小程序等',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '渠道名称',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '0 启动 1未启用',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `create_user` bigint NULL DEFAULT NULL COMMENT '创建用户',
  `update_user` bigint NULL DEFAULT NULL COMMENT '更新用户',
  PRIMARY KEY (`code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '消息渠道表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of message_channel
-- ----------------------------

-- ----------------------------
-- Table structure for message_channel_access_client
-- ----------------------------
DROP TABLE IF EXISTS `message_channel_access_client`;
CREATE TABLE `message_channel_access_client`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '接入商名称',
  `access_token` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '接入密钥，用于校验合法性',
  `access_time` datetime NOT NULL COMMENT '令牌发送时间',
  `state` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '0启用 1禁用',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uni_name`(`name` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '渠道接入商（入驻消息消息平台的用户）' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of message_channel_access_client
-- ----------------------------

-- ----------------------------
-- Table structure for message_channel_account
-- ----------------------------
DROP TABLE IF EXISTS `message_channel_account`;
CREATE TABLE `message_channel_account`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `account_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '渠道账户名称',
  `channel_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '渠道code ',
  `channel_config` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '渠道配置 jsonz字符串 主要配置appid等一些必须的参数',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '0 启用 1弃用',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `create_user` bigint NULL DEFAULT NULL COMMENT '创建用户',
  `update_user` bigint NULL DEFAULT NULL COMMENT '更新用户',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '消息渠道账户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of message_channel_account
-- ----------------------------

-- ----------------------------
-- Table structure for message_client_message_statistics
-- ----------------------------
DROP TABLE IF EXISTS `message_client_message_statistics`;
CREATE TABLE `message_client_message_statistics`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `client_id` int NOT NULL COMMENT '渠道用户',
  `channel_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '渠道code',
  `count` int NOT NULL COMMENT '发送信息条数',
  `create_time` datetime NOT NULL COMMENT '统计时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '渠道用户发送渠道消息条数统计' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of message_client_message_statistics
-- ----------------------------

-- ----------------------------
-- Table structure for message_task
-- ----------------------------
DROP TABLE IF EXISTS `message_task`;
CREATE TABLE `message_task`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `client_id` int NOT NULL COMMENT '接入商id',
  `shield_type` varchar(20) CHARACTER SET tis620 COLLATE tis620_thai_ci NULL DEFAULT NULL COMMENT '消息屏蔽类型 00不屏蔽 10 夜间屏蔽 20时间区间屏蔽',
  `shield_start_time` datetime NULL DEFAULT NULL COMMENT '开始屏蔽时间',
  `shield_end_time` datetime NULL DEFAULT NULL COMMENT '结束屏蔽时间',
  `msg_type` varchar(20) CHARACTER SET tis620 COLLATE tis620_thai_ci NOT NULL COMMENT '消息类型 delay 延时发送 几点发送 now立即发送 corn 定时发送',
  `send_type` varchar(20) CHARACTER SET tis620 COLLATE tis620_thai_ci NOT NULL COMMENT '发送类型 如短信 微信公众号等',
  `msg_corn` varchar(50) CHARACTER SET tis620 COLLATE tis620_thai_ci NULL DEFAULT NULL COMMENT '定时任务corn',
  `task_corn_id` bigint NULL DEFAULT NULL COMMENT 'xxl中的任务id',
  `schedule_time` datetime NULL DEFAULT NULL COMMENT '执行时间 针对延时发送而言',
  `msg_content_type` varchar(20) CHARACTER SET tis620 COLLATE tis620_thai_ci NOT NULL COMMENT '消息内容类型 plain 文本消息 立即发送 template 模板消息',
  `template_id` int NULL DEFAULT NULL COMMENT '模板id',
  `message_content` text CHARACTER SET tis620 COLLATE tis620_thai_ci NOT NULL COMMENT '消息内容',
  `content_value_params` text CHARACTER SET tis620 COLLATE tis620_thai_ci NULL COMMENT '模板参数值',
  `receivers` text CHARACTER SET tis620 COLLATE tis620_thai_ci NOT NULL COMMENT '接收人 json格式',
  `status` varchar(50) CHARACTER SET tis620 COLLATE tis620_thai_ci NOT NULL COMMENT '00 待发送 10 部分发送成功 20 发送失败 30 全部发送成功 40 取消',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = tis620 COLLATE = tis620_thai_ci COMMENT = '消息任务表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of message_task
-- ----------------------------

-- ----------------------------
-- Table structure for message_task_detail
-- ----------------------------
DROP TABLE IF EXISTS `message_task_detail`;
CREATE TABLE `message_task_detail`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `task_id` int NOT NULL COMMENT '任务id',
  `receiver` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '接收人',
  `exec_time` datetime NOT NULL COMMENT '执行时间',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '执行状态 0成功 1失败',
  `retry_times` int NOT NULL DEFAULT 0 COMMENT '重试次数',
  `fail_msg` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '失败原因',
  `success_time` datetime NULL DEFAULT NULL COMMENT '成功时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '消息任务详情表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of message_task_detail
-- ----------------------------

-- ----------------------------
-- Table structure for message_template
-- ----------------------------
DROP TABLE IF EXISTS `message_template`;
CREATE TABLE `message_template`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `client_id` int NOT NULL COMMENT '模板所属用户id',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '模板内容信息 使用{}占位符',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '0 启用 1禁用',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of message_template
-- ----------------------------

-- ----------------------------
-- Table structure for message_template_params
-- ----------------------------
DROP TABLE IF EXISTS `message_template_params`;
CREATE TABLE `message_template_params`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `template_id` int NOT NULL COMMENT '模板id',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '参数名称',
  `create_time` datetime NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '消息模板参数表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of message_template_params
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
