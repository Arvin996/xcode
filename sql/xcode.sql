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
  `product_name` varchar(50) NOT NULL COMMENT '接口所属服务',
  `api_code` varchar(512) NOT NULL COMMENT '接口权限标识',
  `api_path` varchar(512) NOT NULL COMMENT '接口路径',
  `api_desc` varchar(512) DEFAULT NULL COMMENT '接口描述',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_api_code` (`product_name`,`api_code`) USING BTREE,
  UNIQUE KEY `uk_api_path` (`product_name`,`api_path`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统接口维护';

-- 正在导出表  xcode-distribution-card-system.system_api 的数据：~0 rows (大约)
DELETE FROM `system_api`;

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
DELETE FROM `system_dict_data`;

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
DELETE FROM `system_dict_type`;

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
DELETE FROM `system_menu`;

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
DELETE FROM `system_operate_log`;

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

-- 正在导出表  xcode-distribution-card-system.system_role 的数据：~1 rows (大约)
DELETE FROM `system_role`;
INSERT INTO `system_role` (`id`, `code`, `name`, `is_deleted`, `create_time`, `create_user`, `update_time`, `update_user`) VALUES
	(1, 'root', '系统超级管理员', '0', '2025-05-20 16:23:05', NULL, '2025-05-20 16:23:07', NULL);

-- 导出  表 xcode-distribution-card-system.system_role_api 结构
CREATE TABLE IF NOT EXISTS `system_role_api` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `api_id` int(12) NOT NULL COMMENT '接口id',
  `role_id` int(12) NOT NULL COMMENT '角色id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统角色-接口关联表';

-- 正在导出表  xcode-distribution-card-system.system_role_api 的数据：~0 rows (大约)
DELETE FROM `system_role_api`;

-- 导出  表 xcode-distribution-card-system.system_role_menu 结构
CREATE TABLE IF NOT EXISTS `system_role_menu` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `role_id` int(12) NOT NULL COMMENT '角色id',
  `menu_id` int(12) NOT NULL COMMENT '菜单id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色-菜单绑定表';

-- 正在导出表  xcode-distribution-card-system.system_role_menu 的数据：~0 rows (大约)
DELETE FROM `system_role_menu`;

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
DELETE FROM `system_station_notice`;

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
  `ding_talk_webhook_token` text COMMENT '钉钉 webhook token',
  `ding_talk_webhook_secret` text COMMENT '钉钉 webhook 签名密钥',
  `feishu_webhook_token` text COMMENT '飞书 webhook token',
  `feishu_webhook_secret` text COMMENT '飞书 webhook 签名密钥',
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

-- 正在导出表  xcode-distribution-card-system.system_user 的数据：~1 rows (大约)
DELETE FROM `system_user`;
INSERT INTO `system_user` (`id`, `role_id`, `username`, `password`, `nickname`, `sex`, `age`, `mobile`, `email`, `ding_talk_webhook_token`, `ding_talk_webhook_secret`, `feishu_webhook_token`, `feishu_webhook_secret`, `avatar`, `status`, `last_login_ip`, `last_login_time`, `is_deleted`, `create_time`, `create_user`, `update_time`, `update_user`) VALUES
	(1, 1, 'root', '123456', '系统超级用户', '2', 12, '', NULL, NULL, NULL, NULL, NULL, NULL, '0', NULL, NULL, '0', NULL, NULL, NULL, NULL);

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
DELETE FROM `system_visit_log`;


-- 导出 xcode-flow 的数据库结构
CREATE DATABASE IF NOT EXISTS `xcode-flow` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
USE `xcode-flow`;

-- 导出  表 xcode-flow.flow_definition 结构
CREATE TABLE IF NOT EXISTS `flow_definition` (
  `id` bigint(20) unsigned NOT NULL COMMENT '主键id',
  `flow_code` varchar(40) NOT NULL COMMENT '流程编码',
  `flow_name` varchar(100) NOT NULL COMMENT '流程名称',
  `category` varchar(100) DEFAULT NULL COMMENT '流程类别',
  `version` varchar(20) NOT NULL COMMENT '流程版本',
  `is_publish` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否发布（0未发布 1已发布 9失效）',
  `form_custom` char(1) DEFAULT 'N' COMMENT '审批表单是否自定义（Y是 N否）',
  `form_path` varchar(100) DEFAULT NULL COMMENT '审批表单路径',
  `activity_status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '流程激活状态（0挂起 1激活）',
  `listener_type` varchar(100) DEFAULT NULL COMMENT '监听器类型',
  `listener_path` varchar(400) DEFAULT NULL COMMENT '监听器路径',
  `ext` varchar(500) DEFAULT NULL COMMENT '业务详情 存业务表对象json字符串',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志',
  `tenant_id` varchar(40) DEFAULT NULL COMMENT '租户id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='流程定义表';

-- 正在导出表  xcode-flow.flow_definition 的数据：~0 rows (大约)
DELETE FROM `flow_definition`;

-- 导出  表 xcode-flow.flow_his_task 结构
CREATE TABLE IF NOT EXISTS `flow_his_task` (
  `id` bigint(20) unsigned NOT NULL COMMENT '主键id',
  `definition_id` bigint(20) NOT NULL COMMENT '对应flow_definition表的id',
  `instance_id` bigint(20) NOT NULL COMMENT '对应flow_instance表的id',
  `task_id` bigint(20) NOT NULL COMMENT '对应flow_task表的id',
  `node_code` varchar(100) DEFAULT NULL COMMENT '开始节点编码',
  `node_name` varchar(100) DEFAULT NULL COMMENT '开始节点名称',
  `node_type` tinyint(1) DEFAULT NULL COMMENT '开始节点类型（0开始节点 1中间节点 2结束节点 3互斥网关 4并行网关）',
  `target_node_code` varchar(100) DEFAULT NULL COMMENT '目标节点编码',
  `target_node_name` varchar(100) DEFAULT NULL COMMENT '结束节点名称',
  `approver` varchar(40) DEFAULT NULL COMMENT '审批者',
  `cooperate_type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '协作方式(1审批 2转办 3委派 4会签 5票签 6加签 7减签)',
  `collaborator` varchar(40) DEFAULT NULL COMMENT '协作人',
  `skip_type` varchar(10) NOT NULL COMMENT '流转类型（PASS通过 REJECT退回 NONE无动作）',
  `flow_status` varchar(20) NOT NULL COMMENT '流程状态（1审批中 2 审批通过 9已退回 10失效）',
  `form_custom` char(1) DEFAULT 'N' COMMENT '审批表单是否自定义（Y是 N否）',
  `form_path` varchar(100) DEFAULT NULL COMMENT '审批表单路径',
  `message` varchar(500) DEFAULT NULL COMMENT '审批意见',
  `ext` varchar(500) DEFAULT NULL COMMENT '业务详情 存业务表对象json字符串',
  `create_time` datetime DEFAULT NULL COMMENT '任务开始时间',
  `update_time` datetime DEFAULT NULL COMMENT '审批完成时间',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志',
  `tenant_id` varchar(40) DEFAULT NULL COMMENT '租户id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='历史任务记录表';

-- 正在导出表  xcode-flow.flow_his_task 的数据：~0 rows (大约)
DELETE FROM `flow_his_task`;

-- 导出  表 xcode-flow.flow_instance 结构
CREATE TABLE IF NOT EXISTS `flow_instance` (
  `id` bigint(20) NOT NULL COMMENT '主键id',
  `definition_id` bigint(20) NOT NULL COMMENT '对应flow_definition表的id',
  `business_id` varchar(40) NOT NULL COMMENT '业务id',
  `node_type` tinyint(1) NOT NULL COMMENT '结点类型（0开始节点 1中间节点 2结束节点 3互斥网关 4并行网关）',
  `node_code` varchar(40) NOT NULL COMMENT '流程节点编码',
  `node_name` varchar(100) DEFAULT NULL COMMENT '流程节点名称',
  `variable` text COMMENT '任务变量',
  `flow_status` varchar(20) NOT NULL COMMENT '流程状态（0待提交 1审批中 2 审批通过 3自动通过 8已完成 9已退回 10失效）',
  `activity_status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '流程激活状态（0挂起 1激活）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `ext` varchar(500) DEFAULT NULL COMMENT '扩展字段，预留给业务系统使用',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志',
  `tenant_id` varchar(40) DEFAULT NULL COMMENT '租户id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='流程实例表';

-- 正在导出表  xcode-flow.flow_instance 的数据：~0 rows (大约)
DELETE FROM `flow_instance`;

-- 导出  表 xcode-flow.flow_node 结构
CREATE TABLE IF NOT EXISTS `flow_node` (
  `id` bigint(20) unsigned NOT NULL COMMENT '主键id',
  `node_type` tinyint(1) NOT NULL COMMENT '节点类型（0开始节点 1中间节点 2结束结点 3互斥网关 4并行网关）',
  `definition_id` bigint(20) NOT NULL COMMENT '流程定义id',
  `node_code` varchar(100) NOT NULL COMMENT '流程节点编码',
  `node_name` varchar(100) DEFAULT NULL COMMENT '流程节点名称',
  `permission_flag` varchar(200) DEFAULT NULL COMMENT '权限标识（权限类型:权限标识，可以多个，用逗号隔开)',
  `node_ratio` decimal(6,3) DEFAULT NULL COMMENT '流程签署比例值',
  `coordinate` varchar(100) DEFAULT NULL COMMENT '坐标',
  `skip_any_node` varchar(100) DEFAULT 'N' COMMENT '是否可以退回任意节点（Y是 N否）',
  `listener_type` varchar(100) DEFAULT NULL COMMENT '监听器类型',
  `listener_path` varchar(400) DEFAULT NULL COMMENT '监听器路径',
  `handler_type` varchar(100) DEFAULT NULL COMMENT '处理器类型',
  `handler_path` varchar(400) DEFAULT NULL COMMENT '处理器路径',
  `form_custom` char(1) DEFAULT 'N' COMMENT '审批表单是否自定义（Y是 N否）',
  `form_path` varchar(100) DEFAULT NULL COMMENT '审批表单路径',
  `version` varchar(20) NOT NULL COMMENT '版本',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志',
  `tenant_id` varchar(40) DEFAULT NULL COMMENT '租户id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='流程结点表';

-- 正在导出表  xcode-flow.flow_node 的数据：~0 rows (大约)
DELETE FROM `flow_node`;

-- 导出  表 xcode-flow.flow_oa_leave 结构
CREATE TABLE IF NOT EXISTS `flow_oa_leave` (
  `id` bigint(20) unsigned NOT NULL COMMENT '主键',
  `type` tinyint(4) NOT NULL COMMENT '请假类型',
  `reason` varchar(500) NOT NULL COMMENT '请假原因',
  `start_time` datetime NOT NULL COMMENT '开始时间',
  `end_time` datetime NOT NULL COMMENT '结束时间',
  `day` tinyint(4) DEFAULT NULL COMMENT '请假天数',
  `instance_id` bigint(20) DEFAULT NULL COMMENT '流程实例的id',
  `node_code` varchar(100) DEFAULT NULL COMMENT '节点编码',
  `node_name` varchar(100) DEFAULT NULL COMMENT '流程节点名称',
  `node_type` int(1) NOT NULL DEFAULT '0' COMMENT '节点类型（0开始节点 1中间节点 2结束结点 3互斥网关 4并行网关）',
  `flow_status` varchar(20) DEFAULT NULL COMMENT '流程状态（0待提交 1审批中 2 审批通过 3自动通过 8已完成 9已退回 10失效）',
  `create_user` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_user` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='OA 请假申请表';

-- 正在导出表  xcode-flow.flow_oa_leave 的数据：~0 rows (大约)
DELETE FROM `flow_oa_leave`;

-- 导出  表 xcode-flow.flow_skip 结构
CREATE TABLE IF NOT EXISTS `flow_skip` (
  `id` bigint(20) unsigned NOT NULL COMMENT '主键id',
  `definition_id` bigint(20) NOT NULL COMMENT '流程定义id',
  `now_node_code` varchar(100) NOT NULL COMMENT '当前流程节点的编码',
  `now_node_type` tinyint(1) DEFAULT NULL COMMENT '当前节点类型（0开始节点 1中间节点 2结束节点 3互斥网关 4并行网关）',
  `next_node_code` varchar(100) NOT NULL COMMENT '下一个流程节点的编码',
  `next_node_type` tinyint(1) DEFAULT NULL COMMENT '下一个节点类型（0开始节点 1中间节点 2结束节点 3互斥网关 4并行网关）',
  `skip_name` varchar(100) DEFAULT NULL COMMENT '跳转名称',
  `skip_type` varchar(40) DEFAULT NULL COMMENT '跳转类型（PASS审批通过 REJECT退回）',
  `skip_condition` varchar(200) DEFAULT NULL COMMENT '跳转条件',
  `coordinate` varchar(100) DEFAULT NULL COMMENT '坐标',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志',
  `tenant_id` varchar(40) DEFAULT NULL COMMENT '租户id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='结点跳转关联表';

-- 正在导出表  xcode-flow.flow_skip 的数据：~0 rows (大约)
DELETE FROM `flow_skip`;

-- 导出  表 xcode-flow.flow_task 结构
CREATE TABLE IF NOT EXISTS `flow_task` (
  `id` bigint(20) NOT NULL COMMENT '主键id',
  `definition_id` bigint(20) NOT NULL COMMENT '对应flow_definition表的id',
  `instance_id` bigint(20) NOT NULL COMMENT '对应flow_instance表的id',
  `node_code` varchar(100) NOT NULL COMMENT '节点编码',
  `node_name` varchar(100) DEFAULT NULL COMMENT '节点名称',
  `node_type` tinyint(1) NOT NULL COMMENT '节点类型（0开始节点 1中间节点 2结束节点 3互斥网关 4并行网关）',
  `form_custom` char(1) DEFAULT 'N' COMMENT '审批表单是否自定义（Y是 N否）',
  `form_path` varchar(100) DEFAULT NULL COMMENT '审批表单路径',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志',
  `tenant_id` varchar(40) DEFAULT NULL COMMENT '租户id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='待办任务表';

-- 正在导出表  xcode-flow.flow_task 的数据：~0 rows (大约)
DELETE FROM `flow_task`;

-- 导出  表 xcode-flow.flow_user 结构
CREATE TABLE IF NOT EXISTS `flow_user` (
  `id` bigint(20) unsigned NOT NULL COMMENT '主键id',
  `type` char(1) NOT NULL COMMENT '人员类型（1待办任务的审批人权限 2待办任务的转办人权限 3待办任务的委托人权限）',
  `processed_by` varchar(80) DEFAULT NULL COMMENT '权限人',
  `associated` bigint(20) NOT NULL COMMENT '任务表id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(80) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志',
  `tenant_id` varchar(40) DEFAULT NULL COMMENT '租户id',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `user_processed_type` (`processed_by`,`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='流程用户表';

-- 正在导出表  xcode-flow.flow_user 的数据：~0 rows (大约)
DELETE FROM `flow_user`;


-- 导出 xcode-infra 的数据库结构
CREATE DATABASE IF NOT EXISTS `xcode-infra` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `xcode-infra`;

-- 导出  表 xcode-infra.infra_database_conn_info 结构
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

-- 正在导出表  xcode-infra.infra_database_conn_info 的数据：~0 rows (大约)
DELETE FROM `infra_database_conn_info`;

-- 导出  表 xcode-infra.infra_gen_table 结构
CREATE TABLE IF NOT EXISTS `infra_gen_table` (
  `table_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `table_name` varchar(200) DEFAULT '' COMMENT '表名称',
  `table_comment` varchar(500) DEFAULT '' COMMENT '表描述',
  `sub_table_name` varchar(64) DEFAULT NULL COMMENT '关联子表的表名',
  `sub_table_fk_name` varchar(64) DEFAULT NULL COMMENT '子表关联的外键名',
  `class_name` varchar(100) DEFAULT '' COMMENT '实体类名称',
  `tpl_category` varchar(200) DEFAULT 'crud' COMMENT '使用的模板（crud单表操作 tree树表操作）',
  `tpl_web_type` varchar(30) DEFAULT '' COMMENT '前端模板类型（element-ui模版 element-plus模版）',
  `package_name` varchar(100) DEFAULT NULL COMMENT '生成包路径',
  `module_name` varchar(30) DEFAULT NULL COMMENT '生成模块名',
  `business_name` varchar(30) DEFAULT NULL COMMENT '生成业务名',
  `function_name` varchar(50) DEFAULT NULL COMMENT '生成功能名',
  `function_author` varchar(50) DEFAULT NULL COMMENT '生成功能作者',
  `gen_type` char(1) DEFAULT '0' COMMENT '生成代码方式（0zip压缩包 1自定义路径）',
  `gen_path` varchar(200) DEFAULT '/' COMMENT '生成路径（不填默认项目路径）',
  `options` varchar(1000) DEFAULT NULL COMMENT '其它生成选项',
  `create_user` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`table_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='代码生成业务表';

-- 正在导出表  xcode-infra.infra_gen_table 的数据：~0 rows (大约)
DELETE FROM `infra_gen_table`;

-- 导出  表 xcode-infra.infra_gen_table_column 结构
CREATE TABLE IF NOT EXISTS `infra_gen_table_column` (
  `column_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `table_id` bigint(20) DEFAULT NULL COMMENT '归属表编号',
  `column_name` varchar(200) DEFAULT NULL COMMENT '列名称',
  `column_comment` varchar(500) DEFAULT NULL COMMENT '列描述',
  `column_type` varchar(100) DEFAULT NULL COMMENT '列类型',
  `java_type` varchar(500) DEFAULT NULL COMMENT 'JAVA类型',
  `java_field` varchar(200) DEFAULT NULL COMMENT 'JAVA字段名',
  `is_pk` char(1) DEFAULT NULL COMMENT '是否主键（1是）',
  `is_increment` char(1) DEFAULT NULL COMMENT '是否自增（1是）',
  `is_required` char(1) DEFAULT NULL COMMENT '是否必填（1是）',
  `is_insert` char(1) DEFAULT NULL COMMENT '是否为插入字段（1是）',
  `is_edit` char(1) DEFAULT NULL COMMENT '是否编辑字段（1是）',
  `is_list` char(1) DEFAULT NULL COMMENT '是否列表字段（1是）',
  `is_query` char(1) DEFAULT NULL COMMENT '是否查询字段（1是）',
  `query_type` varchar(200) DEFAULT 'EQ' COMMENT '查询方式（等于、不等于、大于、小于、范围）',
  `html_type` varchar(200) DEFAULT NULL COMMENT '显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件）',
  `dict_type` varchar(200) DEFAULT '' COMMENT '字典类型',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `create_user` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`column_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='代码生成业务表字段';

-- 正在导出表  xcode-infra.infra_gen_table_column 的数据：~0 rows (大约)
DELETE FROM `infra_gen_table_column`;

-- 导出  表 xcode-infra.infra_sys_files 结构
CREATE TABLE IF NOT EXISTS `infra_sys_files` (
  `id` varchar(32) NOT NULL COMMENT '文件id  md5值',
  `file_name` varchar(512) NOT NULL COMMENT '文件名',
  `bucket` varchar(128) NOT NULL COMMENT '文件桶',
  `object_name` varchar(512) NOT NULL COMMENT '文件服务器中的key',
  `file_url` text COMMENT '文件访问地址',
  `content_type` varchar(128) NOT NULL COMMENT '文件类型',
  `file_size` bigint(20) NOT NULL COMMENT '文件大小',
  `e_tag` varchar(128) DEFAULT NULL COMMENT '文件tag',
  `create_user` varchar(50) NOT NULL COMMENT '上传人',
  `create_time` datetime DEFAULT NULL COMMENT '上传时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- 正在导出表  xcode-infra.infra_sys_files 的数据：~0 rows (大约)
DELETE FROM `infra_sys_files`;

-- 导出  表 xcode-infra.infra_sys_files_process 结构
CREATE TABLE IF NOT EXISTS `infra_sys_files_process` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '任务id',
  `file_id` varchar(32) NOT NULL COMMENT '对应于file表中的id 表示要进行转码',
  `file_name` varchar(128) NOT NULL COMMENT '文件名',
  `bucket` varchar(30) NOT NULL COMMENT 'minio桶',
  `object_name` varchar(512) NOT NULL COMMENT '文件在minio中的访问路劲',
  `status` char(1) NOT NULL DEFAULT '0' COMMENT '状态 0待处理 1处理中 2处理成功 3处理失败',
  `fail_count` int(2) DEFAULT '0' COMMENT '失败次数',
  `upload_time` datetime DEFAULT NULL COMMENT '上传时间',
  `finish_time` datetime DEFAULT NULL COMMENT '完成时间',
  `err_msg` text COMMENT '失败原因',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- 正在导出表  xcode-infra.infra_sys_files_process 的数据：~0 rows (大约)
DELETE FROM `infra_sys_files_process`;

-- 导出  表 xcode-infra.infra_table_info 结构
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

-- 正在导出表  xcode-infra.infra_table_info 的数据：~0 rows (大约)
DELETE FROM `infra_table_info`;


-- 导出 xcode-job 的数据库结构
CREATE DATABASE IF NOT EXISTS `xcode-job` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
USE `xcode-job`;

-- 导出  表 xcode-job.xxl_job_group 结构
CREATE TABLE IF NOT EXISTS `xxl_job_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `app_name` varchar(64) NOT NULL COMMENT '执行器AppName',
  `title` varchar(12) NOT NULL COMMENT '执行器名称',
  `address_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '执行器地址类型：0=自动注册、1=手动录入',
  `address_list` text COMMENT '执行器地址列表，多地址逗号分隔',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 正在导出表  xcode-job.xxl_job_group 的数据：~78 rows (大约)
DELETE FROM `xxl_job_group`;
INSERT INTO `xxl_job_group` (`id`, `app_name`, `title`, `address_type`, `address_list`, `update_time`) VALUES
	(1, 'xxl-test', '测试执行器', 0, NULL, '2025-05-23 17:30:54'),
	(2, 'xxl-test', '测试执行器', 0, NULL, '2025-05-23 17:30:54'),
	(3, 'xxl-test', '测试执行器', 0, NULL, '2025-05-23 17:30:54'),
	(4, 'xxl-test', '测试执行器', 0, NULL, '2025-05-23 17:30:54'),
	(5, 'xxl-test', '测试执行器', 0, NULL, '2025-05-23 17:30:54'),
	(6, 'xxl-test', '测试执行器', 0, NULL, '2025-05-23 17:30:54'),
	(7, 'xxl-test', '测试执行器', 0, NULL, '2025-05-23 17:30:54'),
	(8, 'xxl-test', '测试执行器', 0, NULL, '2025-05-23 17:30:54'),
	(9, 'xxl-test', '测试执行器', 0, NULL, '2025-05-23 17:30:54'),
	(10, 'xxl-test', '测试执行器', 0, NULL, '2025-05-23 17:30:54'),
	(11, 'xxl-test', '测试执行器', 0, NULL, '2025-05-23 17:30:54'),
	(12, 'xxl-test', '测试执行器', 0, NULL, '2025-05-23 17:30:54'),
	(13, 'xxl-test', '测试执行器', 0, NULL, '2025-05-23 17:30:54'),
	(14, 'xxl-test', '测试执行器', 0, NULL, '2025-05-23 17:30:54'),
	(15, 'xxl-test', '测试执行器', 0, NULL, '2025-05-23 17:30:54'),
	(16, 'xxl-test', '测试执行器', 0, NULL, '2025-05-23 17:30:54'),
	(17, 'xxl-test', '测试执行器', 0, NULL, '2025-05-23 17:30:54'),
	(18, 'xxl-test', '测试执行器', 0, NULL, '2025-05-23 17:30:54'),
	(19, 'xxl-test', '测试执行器', 0, NULL, '2025-05-23 17:30:54'),
	(20, 'xxl-test', '测试执行器', 0, NULL, '2025-05-23 17:30:54'),
	(21, 'xxl-test', '测试执行器', 0, NULL, '2025-05-23 17:30:54'),
	(22, 'xxl-test', '测试执行器', 0, NULL, '2025-05-23 17:30:54'),
	(23, 'xxl-test', '测试执行器', 0, NULL, '2025-05-23 17:30:54'),
	(24, 'xxl-test', '测试执行器', 0, NULL, '2025-05-23 17:30:54'),
	(25, 'xxl-test', '测试执行器', 0, NULL, '2025-05-23 17:30:54'),
	(26, 'xxl-test', '测试执行器', 0, NULL, '2025-05-23 17:30:54'),
	(27, 'xxl-test', '测试执行器', 0, NULL, '2025-05-23 17:30:54'),
	(28, 'xxl-test', '测试执行器', 0, NULL, '2025-05-23 17:30:54'),
	(29, 'xxl-test', '测试执行器', 0, NULL, '2025-05-23 17:30:54'),
	(30, 'xxl-test', '测试执行器', 0, NULL, '2025-05-23 17:30:54'),
	(31, 'xxl-test', '测试执行器', 0, NULL, '2025-05-23 17:30:54'),
	(32, 'xxl-test', '测试执行器', 0, NULL, '2025-05-23 17:30:54'),
	(33, 'xxl-test', '测试执行器', 0, NULL, '2025-05-23 17:30:54'),
	(34, 'xxl-test', '测试执行器', 0, NULL, '2025-05-23 17:30:54'),
	(35, 'xxl-test', '测试执行器', 0, NULL, '2025-05-23 17:30:54'),
	(36, 'xxl-test', '测试执行器', 0, NULL, '2025-05-23 17:30:54'),
	(37, 'xxl-test', '测试执行器', 0, NULL, '2025-05-23 17:30:54'),
	(38, 'xxl-test', '测试执行器', 0, NULL, '2025-05-23 17:30:54'),
	(39, 'xxl-test', '测试执行器', 0, NULL, '2025-05-23 17:30:54'),
	(40, 'xxl-test', '测试执行器', 0, NULL, '2025-05-23 17:30:54'),
	(41, 'xxl-test', '测试执行器', 0, NULL, '2025-05-23 17:30:54'),
	(42, 'xxl-test', '测试执行器', 0, NULL, '2025-05-23 17:30:54'),
	(43, 'xxl-test', '测试执行器', 0, NULL, '2025-05-23 17:30:54'),
	(44, 'xxl-test', '测试执行器', 0, NULL, '2025-05-23 17:30:54'),
	(45, 'xxl-test', '测试执行器', 0, NULL, '2025-05-23 17:30:54'),
	(46, 'xxl-test', '测试执行器', 0, NULL, '2025-05-23 17:30:54'),
	(47, 'xxl-test', '测试执行器', 0, NULL, '2025-05-23 17:30:54'),
	(48, 'xxl-test', '测试执行器', 0, NULL, '2025-05-23 17:30:54'),
	(49, 'xxl-test', '测试执行器', 0, NULL, '2025-05-23 17:30:54'),
	(50, 'xxl-test', '测试执行器', 0, NULL, '2025-05-23 17:30:54'),
	(51, 'xxl-test', '测试执行器', 0, NULL, '2025-05-23 17:30:54'),
	(52, 'xxl-test', '测试执行器', 0, NULL, '2025-05-23 17:30:54'),
	(53, 'xxl-test', '测试执行器', 0, NULL, '2025-05-23 17:30:54'),
	(54, 'xxl-test', '测试执行器', 0, NULL, '2025-05-23 17:30:54'),
	(55, 'xxl-test', '测试执行器', 0, NULL, '2025-05-23 17:30:54'),
	(56, 'xxl-test', '测试执行器', 0, NULL, '2025-05-23 17:30:54'),
	(57, 'xxl-test', '测试执行器', 0, NULL, '2025-05-23 17:30:54'),
	(58, 'xxl-test', '测试执行器', 0, NULL, '2025-05-23 17:30:54'),
	(59, 'xxl-test', '测试执行器', 0, NULL, '2025-05-23 17:30:54'),
	(60, 'xxl-test', '测试执行器', 0, NULL, '2025-05-23 17:30:54'),
	(61, 'xxl-test', '测试执行器', 0, NULL, '2025-05-23 17:30:54'),
	(62, 'xxl-test', '测试执行器', 0, NULL, '2025-05-23 17:30:54'),
	(63, 'xxl-test', '测试执行器', 0, NULL, '2025-05-23 17:30:54'),
	(64, 'xxl-test', '测试执行器', 0, NULL, '2025-05-23 17:30:54'),
	(65, 'xxl-test', '测试执行器', 0, NULL, '2025-05-23 17:30:54'),
	(66, 'xxl-test', '测试执行器', 0, NULL, '2025-05-23 17:30:54'),
	(67, 'xxl-test', '测试执行器', 0, NULL, '2025-05-23 17:30:54'),
	(68, 'xxl-test', '测试执行器', 0, NULL, '2025-05-23 17:30:54'),
	(69, 'xxl-test', '测试执行器', 0, NULL, '2025-05-23 17:30:54'),
	(70, 'xxl-test', '测试执行器', 0, NULL, '2025-05-23 17:30:54'),
	(71, 'xxl-test', '测试执行器', 0, NULL, '2025-05-23 17:30:54'),
	(72, 'xxl-test', '测试执行器', 0, NULL, '2025-05-23 17:30:54'),
	(73, 'xxl-test', '测试执行器', 0, NULL, '2025-05-23 17:30:54'),
	(74, 'xxl-test', '测试执行器', 0, NULL, '2025-05-23 17:30:54'),
	(75, 'xxl-test', '测试执行器', 0, NULL, '2025-05-23 17:30:54'),
	(76, 'xxl-test', '测试执行器', 0, NULL, '2025-05-23 17:30:54'),
	(77, 'xxl-test', '测试执行器', 0, NULL, '2025-05-23 17:30:54'),
	(78, 'xxl-test', '测试执行器', 0, NULL, '2025-05-23 17:30:54');

-- 导出  表 xcode-job.xxl_job_info 结构
CREATE TABLE IF NOT EXISTS `xxl_job_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_group` int(11) NOT NULL COMMENT '执行器主键ID',
  `job_desc` varchar(255) NOT NULL,
  `add_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `author` varchar(64) DEFAULT NULL COMMENT '作者',
  `alarm_email` varchar(255) DEFAULT NULL COMMENT '报警邮件',
  `schedule_type` varchar(50) NOT NULL DEFAULT 'NONE' COMMENT '调度类型',
  `schedule_conf` varchar(128) DEFAULT NULL COMMENT '调度配置，值含义取决于调度类型',
  `misfire_strategy` varchar(50) NOT NULL DEFAULT 'DO_NOTHING' COMMENT '调度过期策略',
  `executor_route_strategy` varchar(50) DEFAULT NULL COMMENT '执行器路由策略',
  `executor_handler` varchar(255) DEFAULT NULL COMMENT '执行器任务handler',
  `executor_param` varchar(512) DEFAULT NULL COMMENT '执行器任务参数',
  `executor_block_strategy` varchar(50) DEFAULT NULL COMMENT '阻塞处理策略',
  `executor_timeout` int(11) NOT NULL DEFAULT '0' COMMENT '任务执行超时时间，单位秒',
  `executor_fail_retry_count` int(11) NOT NULL DEFAULT '0' COMMENT '失败重试次数',
  `glue_type` varchar(50) NOT NULL COMMENT 'GLUE类型',
  `glue_source` mediumtext COMMENT 'GLUE源代码',
  `glue_remark` varchar(128) DEFAULT NULL COMMENT 'GLUE备注',
  `glue_updatetime` datetime DEFAULT NULL COMMENT 'GLUE更新时间',
  `child_jobid` varchar(255) DEFAULT NULL COMMENT '子任务ID，多个逗号分隔',
  `trigger_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '调度状态：0-停止，1-运行',
  `trigger_last_time` bigint(13) NOT NULL DEFAULT '0' COMMENT '上次调度时间',
  `trigger_next_time` bigint(13) NOT NULL DEFAULT '0' COMMENT '下次调度时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 正在导出表  xcode-job.xxl_job_info 的数据：~0 rows (大约)
DELETE FROM `xxl_job_info`;

-- 导出  表 xcode-job.xxl_job_lock 结构
CREATE TABLE IF NOT EXISTS `xxl_job_lock` (
  `lock_name` varchar(50) NOT NULL COMMENT '锁名称',
  PRIMARY KEY (`lock_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 正在导出表  xcode-job.xxl_job_lock 的数据：~0 rows (大约)
DELETE FROM `xxl_job_lock`;
INSERT INTO `xxl_job_lock` (`lock_name`) VALUES
	('schedule_lock');

-- 导出  表 xcode-job.xxl_job_log 结构
CREATE TABLE IF NOT EXISTS `xxl_job_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `job_group` int(11) NOT NULL COMMENT '执行器主键ID',
  `job_id` int(11) NOT NULL COMMENT '任务，主键ID',
  `executor_address` varchar(255) DEFAULT NULL COMMENT '执行器地址，本次执行的地址',
  `executor_handler` varchar(255) DEFAULT NULL COMMENT '执行器任务handler',
  `executor_param` varchar(512) DEFAULT NULL COMMENT '执行器任务参数',
  `executor_sharding_param` varchar(20) DEFAULT NULL COMMENT '执行器任务分片参数，格式如 1/2',
  `executor_fail_retry_count` int(11) NOT NULL DEFAULT '0' COMMENT '失败重试次数',
  `trigger_time` datetime DEFAULT NULL COMMENT '调度-时间',
  `trigger_code` int(11) NOT NULL COMMENT '调度-结果',
  `trigger_msg` text COMMENT '调度-日志',
  `handle_time` datetime DEFAULT NULL COMMENT '执行-时间',
  `handle_code` int(11) NOT NULL COMMENT '执行-状态',
  `handle_msg` text COMMENT '执行-日志',
  `alarm_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '告警状态：0-默认、1-无需告警、2-告警成功、3-告警失败',
  PRIMARY KEY (`id`),
  KEY `I_trigger_time` (`trigger_time`),
  KEY `I_handle_code` (`handle_code`),
  KEY `I_jobid_jobgroup` (`job_id`,`job_group`),
  KEY `I_job_id` (`job_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 正在导出表  xcode-job.xxl_job_log 的数据：~0 rows (大约)
DELETE FROM `xxl_job_log`;

-- 导出  表 xcode-job.xxl_job_logglue 结构
CREATE TABLE IF NOT EXISTS `xxl_job_logglue` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_id` int(11) NOT NULL COMMENT '任务，主键ID',
  `glue_type` varchar(50) DEFAULT NULL COMMENT 'GLUE类型',
  `glue_source` mediumtext COMMENT 'GLUE源代码',
  `glue_remark` varchar(128) NOT NULL COMMENT 'GLUE备注',
  `add_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 正在导出表  xcode-job.xxl_job_logglue 的数据：~0 rows (大约)
DELETE FROM `xxl_job_logglue`;

-- 导出  表 xcode-job.xxl_job_log_report 结构
CREATE TABLE IF NOT EXISTS `xxl_job_log_report` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `trigger_day` datetime DEFAULT NULL COMMENT '调度-时间',
  `running_count` int(11) NOT NULL DEFAULT '0' COMMENT '运行中-日志数量',
  `suc_count` int(11) NOT NULL DEFAULT '0' COMMENT '执行成功-日志数量',
  `fail_count` int(11) NOT NULL DEFAULT '0' COMMENT '执行失败-日志数量',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `i_trigger_day` (`trigger_day`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 正在导出表  xcode-job.xxl_job_log_report 的数据：~3 rows (大约)
DELETE FROM `xxl_job_log_report`;
INSERT INTO `xxl_job_log_report` (`id`, `trigger_day`, `running_count`, `suc_count`, `fail_count`, `update_time`) VALUES
	(1, '2025-05-23 00:00:00', 0, 0, 0, NULL),
	(2, '2025-05-22 00:00:00', 0, 0, 0, NULL),
	(3, '2025-05-21 00:00:00', 0, 0, 0, NULL);

-- 导出  表 xcode-job.xxl_job_registry 结构
CREATE TABLE IF NOT EXISTS `xxl_job_registry` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `registry_group` varchar(50) NOT NULL,
  `registry_key` varchar(255) NOT NULL,
  `registry_value` varchar(255) NOT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `i_g_k_v` (`registry_group`,`registry_key`,`registry_value`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 正在导出表  xcode-job.xxl_job_registry 的数据：~0 rows (大约)
DELETE FROM `xxl_job_registry`;

-- 导出  表 xcode-job.xxl_job_user 结构
CREATE TABLE IF NOT EXISTS `xxl_job_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL COMMENT '账号',
  `password` varchar(50) NOT NULL COMMENT '密码',
  `role` tinyint(4) NOT NULL COMMENT '角色：0-普通用户、1-管理员',
  `permission` varchar(255) DEFAULT NULL COMMENT '权限：执行器ID列表，多个逗号分割',
  PRIMARY KEY (`id`),
  UNIQUE KEY `i_username` (`username`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 正在导出表  xcode-job.xxl_job_user 的数据：~0 rows (大约)
DELETE FROM `xxl_job_user`;
INSERT INTO `xxl_job_user` (`id`, `username`, `password`, `role`, `permission`) VALUES
	(1, 'admin', 'e10adc3949ba59abbe56e057f20f883e', 1, NULL);


-- 导出 xcode-mall 的数据库结构
CREATE DATABASE IF NOT EXISTS `xcode-mall` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
USE `xcode-mall`;

-- 导出  表 xcode-mall.product_brand 结构
CREATE TABLE IF NOT EXISTS `product_brand` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `name` varchar(100) NOT NULL COMMENT '品牌名称',
  `pic_url` varchar(512) DEFAULT NULL COMMENT '品牌logo图片',
  `sort` int(12) DEFAULT NULL COMMENT '品牌排序字段',
  `desc` text COMMENT '品牌描述',
  `create_user` bigint(20) DEFAULT NULL,
  `update_user` bigint(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品品牌';

-- 正在导出表  xcode-mall.product_brand 的数据：~0 rows (大约)
DELETE FROM `product_brand`;

-- 导出  表 xcode-mall.product_category 结构
CREATE TABLE IF NOT EXISTS `product_category` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `parent_id` int(12) DEFAULT NULL COMMENT '父分类id',
  `name` varchar(50) DEFAULT NULL COMMENT '分类名称',
  `pic_url` varchar(512) DEFAULT NULL COMMENT '分类图片',
  `sort` int(12) DEFAULT NULL COMMENT '排序字段',
  `create_user` bigint(20) DEFAULT NULL COMMENT '创建用户',
  `update_user` bigint(20) DEFAULT NULL COMMENT '更新用户',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品分类表';

-- 正在导出表  xcode-mall.product_category 的数据：~0 rows (大约)
DELETE FROM `product_category`;

-- 导出  表 xcode-mall.product_comment 结构
CREATE TABLE IF NOT EXISTS `product_comment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '评论编号，主键自增',
  `user_id` bigint(20) DEFAULT NULL COMMENT '评价人的用户编号关联 MemberUserDO 的 id 编号',
  `user_nickname` varchar(255) DEFAULT NULL COMMENT '评价人名称',
  `user_avatar` varchar(1024) DEFAULT NULL COMMENT '评价人头像',
  `anonymous` char(1) DEFAULT NULL COMMENT '是否匿名0是 1否',
  `order_id` bigint(20) DEFAULT NULL COMMENT '交易订单编号关联 TradeOrderDO 的 id 编号',
  `order_item_id` bigint(20) DEFAULT NULL COMMENT '交易订单项编号关联 TradeOrderItemDO 的 id 编号',
  `spu_id` bigint(20) DEFAULT NULL COMMENT '商品 SPU 编号关联 ProductSpuDO 的 id',
  `spu_name` varchar(255) DEFAULT NULL COMMENT '商品 SPU 名称',
  `sku_id` bigint(20) DEFAULT NULL COMMENT '商品 SKU 编号关联 ProductSkuDO 的 id 编号',
  `visible` char(1) DEFAULT '0' COMMENT '是否可见0显示1隐藏',
  `scores` tinyint(4) DEFAULT NULL COMMENT '评分星级1-5分',
  `description_scores` tinyint(4) DEFAULT NULL COMMENT '描述星级1-5 星',
  `benefit_scores` tinyint(4) DEFAULT NULL COMMENT '服务星级1-5 星',
  `content` text COMMENT '评论内容',
  `pic_urls` varchar(4096) DEFAULT NULL COMMENT '评论图片地址数组',
  `reply_status` char(1) DEFAULT NULL COMMENT '商家是否回复0是 1否',
  `reply_user_id` bigint(20) DEFAULT NULL COMMENT '回复管理员编号关联 AdminUserDO 的 id 编号',
  `reply_content` varchar(1024) DEFAULT NULL COMMENT '商家回复内容',
  `reply_time` datetime DEFAULT NULL COMMENT '商家回复时间',
  `create_user` bigint(20) DEFAULT NULL COMMENT '创建用户',
  `update_user` bigint(20) DEFAULT NULL COMMENT '更新用户',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品评价表';

-- 正在导出表  xcode-mall.product_comment 的数据：~0 rows (大约)
DELETE FROM `product_comment`;

-- 导出  表 xcode-mall.product_favorite 结构
CREATE TABLE IF NOT EXISTS `product_favorite` (
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `sku_id` bigint(20) NOT NULL COMMENT '商品sku_id',
  PRIMARY KEY (`user_id`,`sku_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品收藏表';

-- 正在导出表  xcode-mall.product_favorite 的数据：~0 rows (大约)
DELETE FROM `product_favorite`;

-- 导出  表 xcode-mall.product_property 结构
CREATE TABLE IF NOT EXISTS `product_property` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(64) DEFAULT NULL COMMENT '规格名称',
  `create_user` bigint(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_user` bigint(20) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品属性';

-- 正在导出表  xcode-mall.product_property 的数据：~0 rows (大约)
DELETE FROM `product_property`;

-- 导出  表 xcode-mall.product_property_value 结构
CREATE TABLE IF NOT EXISTS `product_property_value` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `property_id` int(12) DEFAULT NULL COMMENT '规格键id',
  `name` varchar(128) DEFAULT NULL COMMENT '规格值名字',
  `create_user` bigint(20) DEFAULT NULL COMMENT '创建用户',
  `update_user` bigint(20) DEFAULT NULL COMMENT '更新用户',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品属性规则值表';

-- 正在导出表  xcode-mall.product_property_value 的数据：~0 rows (大约)
DELETE FROM `product_property_value`;

-- 导出  表 xcode-mall.product_sku 结构
CREATE TABLE IF NOT EXISTS `product_sku` (
  `id` bigint(20) NOT NULL,
  `spu_id` bigint(20) NOT NULL COMMENT 'spu编号',
  `property_values` varchar(512) DEFAULT NULL COMMENT 'spu对应的属性对应的值',
  `price` int(11) NOT NULL DEFAULT '-1' COMMENT '商品价格，单位：分',
  `bar_code` varchar(64) DEFAULT NULL COMMENT 'SKU 的条形码',
  `pic_url` varchar(256) NOT NULL COMMENT '图片地址',
  `stock` int(11) DEFAULT NULL COMMENT '库存',
  `weight` double DEFAULT NULL COMMENT '商品重量，单位：kg 千克',
  `volume` double DEFAULT NULL COMMENT '商品体积，单位：m^3 平米',
  `sales_count` int(11) DEFAULT NULL COMMENT '商品销量',
  `create_user` bigint(20) DEFAULT NULL COMMENT '创建用户',
  `update_user` bigint(20) DEFAULT NULL COMMENT '更新用户',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品sku';

-- 正在导出表  xcode-mall.product_sku 的数据：~0 rows (大约)
DELETE FROM `product_sku`;

-- 导出  表 xcode-mall.product_spu 结构
CREATE TABLE IF NOT EXISTS `product_spu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '商品 SPU 编号，自增',
  `name` varchar(128) NOT NULL COMMENT '商品名称',
  `introduction` varchar(256) NOT NULL COMMENT '商品简介',
  `description` text NOT NULL COMMENT '商品详情',
  `bar_code` varchar(64) NOT NULL COMMENT '条形码',
  `category_id` bigint(20) NOT NULL COMMENT '商品分类编号',
  `property_ids` varchar(100) DEFAULT NULL COMMENT '商品属性，如颜色内存 显卡等',
  `brand_id` int(11) DEFAULT NULL COMMENT '商品品牌编号',
  `pic_url` varchar(256) NOT NULL COMMENT '商品封面图',
  `slider_pic_urls` varchar(2000) DEFAULT '' COMMENT '商品轮播图地址\n 数组，以逗号分隔\n 最多上传15张',
  `video_url` varchar(256) DEFAULT NULL COMMENT '商品视频',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '排序字段',
  `status` char(1) NOT NULL COMMENT '商品状态: 0 上架（开启） 1 下架（禁用',
  `recommend_hot` char(1) NOT NULL COMMENT '是否热卖推荐: 0 默认 1 热卖',
  `recommend_benefit` char(1) NOT NULL COMMENT '是否优惠推荐: 0 默认 1 优选',
  `recommend_best` char(1) NOT NULL COMMENT '是否精品推荐: 0 默认 1 精品',
  `recommend_new` char(1) NOT NULL COMMENT '是否新品推荐: 0 默认 1 新品',
  `recommend_good` char(1) NOT NULL COMMENT '是否优品推荐',
  `give_integral` int(11) NOT NULL COMMENT '赠送积分',
  `give_coupon_template_ids` varchar(512) DEFAULT '' COMMENT '赠送的优惠劵编号的数组',
  `activity_orders` varchar(16) NOT NULL DEFAULT '' COMMENT '活动显示排序0=默认, 1=秒杀，2=砍价，3=拼团',
  `sales_count` int(11) DEFAULT '0' COMMENT '商品销量',
  `browse_count` int(11) DEFAULT '0' COMMENT '商品点击量',
  `create_user` bigint(20) DEFAULT NULL COMMENT '创建用户',
  `update_user` bigint(20) DEFAULT NULL COMMENT '更新用户',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品spu';

-- 正在导出表  xcode-mall.product_spu 的数据：~0 rows (大约)
DELETE FROM `product_spu`;


-- 导出 xcode-member 的数据库结构
CREATE DATABASE IF NOT EXISTS `xcode-member` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
USE `xcode-member`;

-- 导出  表 xcode-member.member_address 结构
CREATE TABLE IF NOT EXISTS `member_address` (
  `id` int(12) NOT NULL COMMENT '自增id',
  `user_id` varchar(30) NOT NULL COMMENT '用户id',
  `name` varchar(50) NOT NULL COMMENT '收件人姓名',
  `phone` char(11) NOT NULL COMMENT '收件人手机号',
  `area_id` int(12) NOT NULL COMMENT '地区id',
  `detail_address` text NOT NULL COMMENT '详细的收件地址 如楼层+门牌号',
  `default_address` char(1) NOT NULL DEFAULT '1' COMMENT '是否是默认地址 0是 1不是',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员收货地址表';

-- 正在导出表  xcode-member.member_address 的数据：~0 rows (大约)
DELETE FROM `member_address`;

-- 导出  表 xcode-member.member_config 结构
CREATE TABLE IF NOT EXISTS `member_config` (
  `id` int(12) NOT NULL COMMENT '自增id',
  `point_deduct_enable` char(1) DEFAULT '0' COMMENT '积分抵用开关0 开启 1关闭',
  `point_deduct_unit` int(12) DEFAULT NULL COMMENT '1积分抵扣多少钱 单位：分',
  `max_point_deduct` int(12) DEFAULT NULL COMMENT '最大使用积分数',
  `given_point_add` int(12) DEFAULT NULL COMMENT '一元赠送的积分数量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员配置表';

-- 正在导出表  xcode-member.member_config 的数据：~0 rows (大约)
DELETE FROM `member_config`;

-- 导出  表 xcode-member.member_experience_record 结构
CREATE TABLE IF NOT EXISTS `member_experience_record` (
  `id` int(12) NOT NULL COMMENT '自增id',
  `user_id` bigint(30) NOT NULL COMMENT '用户id',
  `biz_type` int(2) NOT NULL COMMENT '增加或者减少经验的业务类型',
  `biz_id` varchar(50) DEFAULT NULL COMMENT '业务类型对应的业务id',
  `description` text COMMENT '业务类型对应的描述',
  `title` varchar(255) DEFAULT NULL COMMENT '业务类型对应的标题',
  `experience` int(12) DEFAULT NULL COMMENT '变更经验',
  `total_experience` int(12) DEFAULT NULL COMMENT '变更后的总经验',
  `create_time` datetime DEFAULT NULL COMMENT '记录时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员经验记录表';

-- 正在导出表  xcode-member.member_experience_record 的数据：~0 rows (大约)
DELETE FROM `member_experience_record`;

-- 导出  表 xcode-member.member_group 结构
CREATE TABLE IF NOT EXISTS `member_group` (
  `id` int(12) NOT NULL COMMENT '自增id',
  `name` varchar(50) NOT NULL COMMENT '组名',
  `status` char(1) NOT NULL DEFAULT '0' COMMENT '是否启用 0启用 1未启用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员所属组表';

-- 正在导出表  xcode-member.member_group 的数据：~0 rows (大约)
DELETE FROM `member_group`;

-- 导出  表 xcode-member.member_level 结构
CREATE TABLE IF NOT EXISTS `member_level` (
  `id` int(12) NOT NULL COMMENT '自增id',
  `name` varchar(50) NOT NULL COMMENT '等级名称',
  `level` int(11) NOT NULL COMMENT '等级',
  `experience` int(11) NOT NULL COMMENT '升级所需经验',
  `discount_percent` int(11) NOT NULL COMMENT '享受折扣',
  `level_icon` text COMMENT '等级头像',
  `level_background` text COMMENT '等级背景图',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员等级表';

-- 正在导出表  xcode-member.member_level 的数据：~0 rows (大约)
DELETE FROM `member_level`;

-- 导出  表 xcode-member.member_level_change_record 结构
CREATE TABLE IF NOT EXISTS `member_level_change_record` (
  `id` int(11) DEFAULT NULL,
  `user_id` bigint(30) NOT NULL,
  `level_id` int(12) NOT NULL,
  `level_name` varchar(50) NOT NULL,
  `total_experience` int(12) NOT NULL,
  `change_experience` int(12) NOT NULL,
  `title` varchar(255) NOT NULL,
  `description` text,
  `create_time` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员等级变更记录表';

-- 正在导出表  xcode-member.member_level_change_record 的数据：~0 rows (大约)
DELETE FROM `member_level_change_record`;

-- 导出  表 xcode-member.member_login_record 结构
CREATE TABLE IF NOT EXISTS `member_login_record` (
  `id` int(12) NOT NULL COMMENT '自增id',
  `user_id` bigint(30) NOT NULL COMMENT '用户id',
  `login_ip` varchar(30) NOT NULL COMMENT '登录ip',
  `login_time` datetime NOT NULL COMMENT '登录时间',
  `login_area_id` int(12) NOT NULL COMMENT '登录地区',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员登录记录表';

-- 正在导出表  xcode-member.member_login_record 的数据：~0 rows (大约)
DELETE FROM `member_login_record`;

-- 导出  表 xcode-member.member_point_record 结构
CREATE TABLE IF NOT EXISTS `member_point_record` (
  `id` int(11) NOT NULL COMMENT '自增id',
  `user_id` bigint(30) NOT NULL COMMENT '用户id',
  `biz_type` int(12) NOT NULL COMMENT '变更的业务类型',
  `biz_id` varchar(50) DEFAULT NULL COMMENT '变更的业务id',
  `title` varchar(255) NOT NULL COMMENT '变更业务类型的标题',
  `description` text COMMENT '变更业务描述',
  `change_point` int(12) NOT NULL COMMENT '变更积分',
  `total_point` int(12) NOT NULL COMMENT '变更后的总积分',
  `create_time` datetime DEFAULT NULL COMMENT '变更时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户积分记录表';

-- 正在导出表  xcode-member.member_point_record 的数据：~0 rows (大约)
DELETE FROM `member_point_record`;

-- 导出  表 xcode-member.member_sign 结构
CREATE TABLE IF NOT EXISTS `member_sign` (
  `day` int(2) NOT NULL COMMENT '签到第几天 星期一到星期天1-7',
  `point` int(12) NOT NULL COMMENT '奖励积分',
  `experience` int(12) NOT NULL COMMENT '奖励经验',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`day`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员签到表';

-- 正在导出表  xcode-member.member_sign 的数据：~0 rows (大约)
DELETE FROM `member_sign`;

-- 导出  表 xcode-member.member_sign_record 结构
CREATE TABLE IF NOT EXISTS `member_sign_record` (
  `id` int(12) NOT NULL COMMENT '自增id',
  `user_id` bigint(30) NOT NULL COMMENT '用户id',
  `day` int(2) NOT NULL COMMENT '第几天签到',
  `point` int(12) NOT NULL COMMENT '签到获得的积分',
  `experience` int(12) NOT NULL COMMENT '签到获得的经验',
  `create_time` datetime DEFAULT NULL COMMENT '签到时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员签到记录表';

-- 正在导出表  xcode-member.member_sign_record 的数据：~0 rows (大约)
DELETE FROM `member_sign_record`;

-- 导出  表 xcode-member.member_tag 结构
CREATE TABLE IF NOT EXISTS `member_tag` (
  `id` int(12) NOT NULL COMMENT '自增id',
  `name` varchar(50) NOT NULL COMMENT '标签名称',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户标签表';

-- 正在导出表  xcode-member.member_tag 的数据：~0 rows (大约)
DELETE FROM `member_tag`;

-- 导出  表 xcode-member.member_user 结构
CREATE TABLE IF NOT EXISTS `member_user` (
  `id` bigint(30) NOT NULL COMMENT '用户id',
  `password` varchar(100) NOT NULL COMMENT '用户密码',
  `mobile` char(11) NOT NULL COMMENT '用户手机号',
  `email` varchar(50) DEFAULT NULL COMMENT '用户邮箱',
  `status` char(1) NOT NULL DEFAULT '0' COMMENT ' 0 正常 1禁用',
  `nickname` varchar(50) NOT NULL DEFAULT 'gasada_029' COMMENT '用户昵称',
  `level_id` int(12) NOT NULL COMMENT '用户等级',
  `experience` int(12) NOT NULL COMMENT '用户经验',
  `avatar` text COMMENT '用户头像',
  `point` int(12) NOT NULL COMMENT '用户积分',
  `tag_ids` varchar(255) DEFAULT NULL COMMENT '用户标签',
  `group_id` int(12) DEFAULT NULL COMMENT '用户分组id',
  `sex` char(1) DEFAULT NULL COMMENT '用户性别',
  `birthday` date DEFAULT NULL COMMENT '用户生日',
  `login_ip` varchar(30) DEFAULT NULL COMMENT '登录ip',
  `login_area_id` int(12) DEFAULT NULL COMMENT '登录区域',
  `login_time` datetime DEFAULT NULL COMMENT '登录时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员用户表';

-- 正在导出表  xcode-member.member_user 的数据：~0 rows (大约)
DELETE FROM `member_user`;


-- 导出 xcode-message 的数据库结构
CREATE DATABASE IF NOT EXISTS `xcode-message` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
USE `xcode-message`;

-- 导出  表 xcode-message.message_channel 结构
CREATE TABLE IF NOT EXISTS `message_channel` (
  `id` int(12) NOT NULL AUTO_INCREMENT,
  `code` varchar(50) NOT NULL COMMENT '渠道code 如短信sms 微信小程序等',
  `name` varchar(50) NOT NULL COMMENT '渠道名称',
  `support_load_balance` char(1) NOT NULL DEFAULT '1' COMMENT '是否支持负载均衡 0支持 1不支持',
  `status` char(1) NOT NULL DEFAULT '0' COMMENT '0 启动 1未启用',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_user` varchar(50) DEFAULT NULL COMMENT '创建用户',
  `update_user` varchar(50) DEFAULT NULL COMMENT '更新用户',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='消息渠道表';

-- 正在导出表  xcode-message.message_channel 的数据：~1 rows (大约)
DELETE FROM `message_channel`;
INSERT INTO `message_channel` (`id`, `code`, `name`, `support_load_balance`, `status`, `create_time`, `update_time`, `create_user`, `update_user`) VALUES
	(1, 'email', '邮箱渠道', '0', '0', '2025-05-23 09:21:09', '2025-05-23 09:21:10', 'root', 'root');

-- 导出  表 xcode-message.message_channel_access_client 结构
CREATE TABLE IF NOT EXISTS `message_channel_access_client` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `name` varchar(255) NOT NULL COMMENT '接入商名称',
  `email` varchar(255) DEFAULT NULL COMMENT '接入商联系人邮箱',
  `mobile` char(11) DEFAULT NULL COMMENT '接入商联系人手机号',
  `access_token` text NOT NULL COMMENT '接入密钥，用于校验合法性',
  `status` char(1) NOT NULL DEFAULT '0' COMMENT '0启用 1禁用',
  `access_count` int(12) NOT NULL DEFAULT '100' COMMENT '接入商消息配额 默认100',
  `used_count` int(12) NOT NULL DEFAULT '0' COMMENT '已用配额',
  `rest_count` int(12) NOT NULL COMMENT '剩余配额',
  `is_deleted` char(1) NOT NULL DEFAULT '0' COMMENT '0 未删除 1 已删除',
  `token_refresh_time` datetime DEFAULT NULL COMMENT 'token 刷新时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uni_name` (`name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='渠道接入商（入驻消息消息平台的用户）';

-- 正在导出表  xcode-message.message_channel_access_client 的数据：~1 rows (大约)
DELETE FROM `message_channel_access_client`;
INSERT INTO `message_channel_access_client` (`id`, `name`, `email`, `mobile`, `access_token`, `status`, `access_count`, `used_count`, `rest_count`, `is_deleted`, `token_refresh_time`, `create_time`, `update_time`) VALUES
	(1, '系统接入商', '1347459620@qq.com', '13027102413', 'sksjnga212sa238hojm', '0', 999999, 3, 999996, '0', '2025-05-23 09:22:21', '2025-05-23 09:22:24', '2025-05-23 17:27:42');

-- 导出  表 xcode-message.message_channel_account 结构
CREATE TABLE IF NOT EXISTS `message_channel_account` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `account_name` varchar(50) NOT NULL COMMENT '渠道账户名称',
  `channel_id` int(12) NOT NULL COMMENT '渠道id',
  `weight` double NOT NULL DEFAULT '1' COMMENT '账号权重',
  `status` char(1) NOT NULL DEFAULT '0' COMMENT '0 启用 1弃用',
  `is_deleted` char(1) NOT NULL DEFAULT '0' COMMENT '0 未删除 1已删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_user` varchar(50) DEFAULT NULL COMMENT '创建用户',
  `update_user` varchar(50) DEFAULT NULL COMMENT '更新用户',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_account_id_name` (`channel_id`,`account_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='消息渠道账户表';

-- 正在导出表  xcode-message.message_channel_account 的数据：~1 rows (大约)
DELETE FROM `message_channel_account`;
INSERT INTO `message_channel_account` (`id`, `account_name`, `channel_id`, `weight`, `status`, `is_deleted`, `create_time`, `update_time`, `create_user`, `update_user`) VALUES
	(1, '系统用户邮箱', 1, 1, '0', '0', '2025-05-23 09:23:00', '2025-05-23 09:23:01', 'root', 'root');

-- 导出  表 xcode-message.message_channel_account_param_value 结构
CREATE TABLE IF NOT EXISTS `message_channel_account_param_value` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `account_id` int(12) NOT NULL COMMENT '账号id',
  `channel_param_id` int(12) NOT NULL COMMENT '渠道参数id',
  `param_value` varchar(1024) NOT NULL COMMENT '参数值',
  `is_deleted` char(1) NOT NULL DEFAULT '0' COMMENT '是否删除 0未删除 1已删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(50) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user` varchar(50) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='消息渠道账户参数值表';

-- 正在导出表  xcode-message.message_channel_account_param_value 的数据：~4 rows (大约)
DELETE FROM `message_channel_account_param_value`;
INSERT INTO `message_channel_account_param_value` (`id`, `account_id`, `channel_param_id`, `param_value`, `is_deleted`, `create_time`, `create_user`, `update_time`, `update_user`) VALUES
	(1, 1, 1, '1347459620@qq.com', '0', '2025-05-23 09:27:18', NULL, '2025-05-23 09:27:19', NULL),
	(2, 1, 2, 'dummdezbcriuhahh', '0', '2025-05-23 09:28:13', NULL, '2025-05-23 09:28:14', NULL),
	(3, 1, 3, 'smtp.qq.com', '0', '2025-05-23 09:28:13', NULL, '2025-05-23 09:28:15', NULL),
	(4, 1, 4, '465', '0', '2025-05-23 09:28:12', NULL, '2025-05-23 09:28:15', NULL);

-- 导出  表 xcode-message.message_channel_param 结构
CREATE TABLE IF NOT EXISTS `message_channel_param` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `channel_id` int(12) NOT NULL COMMENT '渠道id',
  `name` varchar(50) NOT NULL COMMENT '参数名称',
  `required` char(1) NOT NULL DEFAULT '0' COMMENT '是否必须 0否 1是',
  `desc` varchar(256) DEFAULT NULL COMMENT '参数描述',
  `is_deleted` char(1) NOT NULL DEFAULT '0' COMMENT '是否删除 0未删除 1已删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(50) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user` varchar(50) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='消息渠道参数';

-- 正在导出表  xcode-message.message_channel_param 的数据：~4 rows (大约)
DELETE FROM `message_channel_param`;
INSERT INTO `message_channel_param` (`id`, `channel_id`, `name`, `required`, `desc`, `is_deleted`, `create_time`, `create_user`, `update_time`, `update_user`) VALUES
	(1, 1, 'username', '1', '邮箱用户名', '0', '2025-05-23 09:24:45', NULL, '2025-05-23 09:24:59', NULL),
	(2, 1, 'password', '1', '邮箱密码', '0', NULL, NULL, NULL, NULL),
	(3, 1, 'host', '1', '邮箱主机名', '0', NULL, NULL, NULL, NULL),
	(4, 1, 'port', '1', '邮箱服务器端口', '0', NULL, NULL, NULL, NULL);

-- 导出  表 xcode-message.message_client_channel 结构
CREATE TABLE IF NOT EXISTS `message_client_channel` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `client_id` int(12) NOT NULL COMMENT '接入商id',
  `channel_id` int(12) NOT NULL COMMENT '渠道',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='消息接入商与渠道的绑定关系';

-- 正在导出表  xcode-message.message_client_channel 的数据：~1 rows (大约)
DELETE FROM `message_client_channel`;
INSERT INTO `message_client_channel` (`id`, `client_id`, `channel_id`) VALUES
	(1, 1, 1);

-- 导出  表 xcode-message.message_client_message_statistics 结构
CREATE TABLE IF NOT EXISTS `message_client_message_statistics` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `client_id` int(11) NOT NULL COMMENT '渠道用户',
  `channel_id` int(11) NOT NULL COMMENT '渠道code',
  `channel_account` int(11) NOT NULL COMMENT '渠道发送账户',
  `count` int(11) NOT NULL COMMENT '发送信息条数',
  `success_count` int(11) NOT NULL COMMENT '成功条数',
  `fail_count` int(11) NOT NULL COMMENT '失败条数',
  `create_time` datetime DEFAULT NULL COMMENT '统计时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='渠道用户发送渠道消息条数统计';

-- 正在导出表  xcode-message.message_client_message_statistics 的数据：~0 rows (大约)
DELETE FROM `message_client_message_statistics`;

-- 导出  表 xcode-message.message_task 结构
CREATE TABLE IF NOT EXISTS `message_task` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `client_id` int(11) NOT NULL COMMENT '接入商id',
  `account_id` int(11) NOT NULL COMMENT '渠道账号id',
  `shield_type` varchar(20) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '消息屏蔽类型 00不屏蔽 10 夜间屏蔽 20时间区间屏蔽',
  `shield_start_time` char(4) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '开始屏蔽时间 0800',
  `shield_end_time` char(4) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '结束屏蔽时间 1700',
  `msg_type` varchar(20) CHARACTER SET utf8mb4 NOT NULL COMMENT '消息类型 delay 延时发送 now立即发送 corn 定时发送',
  `channel_id` int(12) NOT NULL COMMENT '发送渠道 如短信 微信公众号等',
  `task_corn` varchar(50) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '如果为定时任务 则是定时任务corn',
  `task_corn_id` bigint(20) DEFAULT NULL COMMENT 'xxl中的任务id',
  `schedule_time` datetime DEFAULT NULL COMMENT '执行时间 针对延时发送而言',
  `msg_content_type` varchar(20) CHARACTER SET utf8mb4 NOT NULL COMMENT '消息内容类型 plain文本 template 模板消息',
  `template_id` int(11) DEFAULT NULL COMMENT '模板id',
  `message_content` text CHARACTER SET utf8mb4 NOT NULL COMMENT '消息内容',
  `content_value_params` text CHARACTER SET utf8mb4 COMMENT '模板参数值 json格式',
  `receiver_type` char(2) CHARACTER SET utf8mb4 NOT NULL COMMENT '接收人类型 00透传直接发送 10 csv文件',
  `receivers` longtext COMMENT '接收人',
  `status` varchar(50) CHARACTER SET utf8mb4 NOT NULL DEFAULT '00' COMMENT '00 待发送 \r\n10 部分发送成功 \r\n20 全部发送失败 \r\n30 全部发送成功 \r\n40 取消发送（延时任务）\r\n50 暂停发送（定时任务）',
  `create_time` datetime NOT NULL COMMENT '任务创建时间',
  `trigger_time` datetime DEFAULT NULL COMMENT '任务执行时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `key_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=tis620 ROW_FORMAT=DYNAMIC COMMENT='消息任务表';

-- 正在导出表  xcode-message.message_task 的数据：~0 rows (大约)
DELETE FROM `message_task`;
INSERT INTO `message_task` (`id`, `client_id`, `account_id`, `shield_type`, `shield_start_time`, `shield_end_time`, `msg_type`, `channel_id`, `task_corn`, `task_corn_id`, `schedule_time`, `msg_content_type`, `template_id`, `message_content`, `content_value_params`, `receiver_type`, `receivers`, `status`, `create_time`, `trigger_time`) VALUES
	(1, 1, 1, '10', NULL, NULL, 'now', 1, NULL, NULL, NULL, 'template', 1, '{"subject1":"发卡平台邮箱短信验证码","content":"您好，您此次的验证码为【123456】, 请尽快完成验证！"}', '{"code": "123456"}', '00', '1347459620@qq.com', '20', '2025-05-23 17:27:41', NULL);

-- 导出  表 xcode-message.message_task_detail 结构
CREATE TABLE IF NOT EXISTS `message_task_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `task_id` bigint(20) NOT NULL COMMENT '任务id',
  `receiver` varchar(50) NOT NULL COMMENT '接收人',
  `status` char(1) NOT NULL COMMENT '执行状态 0成功 1失败',
  `retry_times` int(11) NOT NULL DEFAULT '0' COMMENT '重试次数',
  `fail_msg` text COMMENT '失败原因',
  `exec_time` datetime NOT NULL COMMENT '执行时间',
  `success_time` datetime DEFAULT NULL COMMENT '成功时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `key_exec_time` (`exec_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='消息任务详情表';

-- 正在导出表  xcode-message.message_task_detail 的数据：~1 rows (大约)
DELETE FROM `message_task_detail`;
INSERT INTO `message_task_detail` (`id`, `task_id`, `receiver`, `status`, `retry_times`, `fail_msg`, `exec_time`, `success_time`) VALUES
	(1, 1, '1347459620@qq.com', '1', 0, '消息模板格式不正确，缺少subject字段', '2025-05-23 17:27:42', NULL);

-- 导出  表 xcode-message.message_template 结构
CREATE TABLE IF NOT EXISTS `message_template` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL COMMENT '模板名称',
  `type` char(1) NOT NULL COMMENT '0自定义模板 1 三方平台的模板',
  `template_id` varchar(1024) NOT NULL COMMENT '业务id 指的是在三方平台中定义模板后的id值',
  `content` text COMMENT '模板内容信息 使用{}占位符',
  `status` char(1) NOT NULL DEFAULT '0' COMMENT '0 启用 1禁用',
  `desc` text COMMENT '模板描述',
  `is_deleted` char(1) NOT NULL DEFAULT '0' COMMENT '0 已删除 1 未删除',
  `create_user` varchar(50) DEFAULT NULL COMMENT '创建用户',
  `update_user` varchar(50) DEFAULT NULL COMMENT '更新用户',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- 正在导出表  xcode-message.message_template 的数据：~1 rows (大约)
DELETE FROM `message_template`;
INSERT INTO `message_template` (`id`, `name`, `type`, `template_id`, `content`, `status`, `desc`, `is_deleted`, `create_user`, `update_user`, `create_time`, `update_time`) VALUES
	(1, '邮箱验证码模板', '0', 'sds4d5ad2ojfns2', '{"subject1":"发卡平台邮箱短信验证码","content":"您好，您此次的验证码为【{code}】, 请尽快完成验证！"}', '0', 's邮箱验证码模板', '0', NULL, NULL, NULL, NULL);

-- 导出  表 xcode-message.message_template_params 结构
CREATE TABLE IF NOT EXISTS `message_template_params` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `template_id` int(11) NOT NULL COMMENT '模板id',
  `name` varchar(50) NOT NULL COMMENT '参数名称',
  `desc` varchar(512) DEFAULT NULL COMMENT '参数描述',
  `create_user` varchar(50) DEFAULT NULL COMMENT '创建人',
  `update_user` varchar(50) DEFAULT NULL COMMENT '更信任',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='消息模板参数表';

-- 正在导出表  xcode-message.message_template_params 的数据：~1 rows (大约)
DELETE FROM `message_template_params`;
INSERT INTO `message_template_params` (`id`, `template_id`, `name`, `desc`, `create_user`, `update_user`, `create_time`, `update_time`) VALUES
	(1, 1, 'code', '验证码', NULL, NULL, NULL, NULL);


-- 导出 xcode-pay 的数据库结构
CREATE DATABASE IF NOT EXISTS `xcode-pay` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
USE `xcode-pay`;

-- 导出  表 xcode-pay.pay_app 结构
CREATE TABLE IF NOT EXISTS `pay_app` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '应用id 自增',
  `app_code` varchar(50) NOT NULL COMMENT '应用编号',
  `app_name` varchar(50) NOT NULL COMMENT '应用名称',
  `status` char(1) DEFAULT '0' COMMENT '应用状态 0开启 1关闭',
  `remark` text COMMENT '应用备注',
  `order_notify_url` text COMMENT '支付结果的回调地址',
  `refund_notify_url` text COMMENT '退款结果的回调地址',
  `create_user` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='支付应用表（京东支付等 一个商户对用多个支付应用）';

-- 正在导出表  xcode-pay.pay_app 的数据：~0 rows (大约)
DELETE FROM `pay_app`;

-- 导出  表 xcode-pay.pay_app_channel 结构
CREATE TABLE IF NOT EXISTS `pay_app_channel` (
  `id` int(12) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `app_id` int(12) NOT NULL COMMENT '应用id',
  `channel_id` int(12) NOT NULL COMMENT '渠道id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='支付应用渠道表';

-- 正在导出表  xcode-pay.pay_app_channel 的数据：~0 rows (大约)
DELETE FROM `pay_app_channel`;

-- 导出  表 xcode-pay.pay_channel 结构
CREATE TABLE IF NOT EXISTS `pay_channel` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '渠道id 自增',
  `channel_code` varchar(50) NOT NULL COMMENT '支付渠道编码',
  `status` char(1) DEFAULT '0' COMMENT '渠道状态 0 可用 1不可用',
  `fee_rate` varchar(20) DEFAULT NULL COMMENT '渠道费率，单位：百分比',
  `remark` text COMMENT '渠道备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='支付渠道表';

-- 正在导出表  xcode-pay.pay_channel 的数据：~0 rows (大约)
DELETE FROM `pay_channel`;

-- 导出  表 xcode-pay.pay_merchant 结构
CREATE TABLE IF NOT EXISTS `pay_merchant` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '商户id 自增',
  `merchant_no` varchar(50) NOT NULL COMMENT '商户编号',
  `merchant_name` varchar(50) NOT NULL COMMENT '商户名称',
  `merchant_short_name` varchar(50) NOT NULL COMMENT '商户简称',
  `status` char(1) DEFAULT '0' COMMENT '商户状态 0 开启 1关闭',
  `remark` text COMMENT '商户备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(50) DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商户表';

-- 正在导出表  xcode-pay.pay_merchant 的数据：~0 rows (大约)
DELETE FROM `pay_merchant`;

-- 导出  表 xcode-pay.pay_notify_log 结构
CREATE TABLE IF NOT EXISTS `pay_notify_log` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '日志id',
  `task_id` int(12) NOT NULL COMMENT '任务id',
  `notify_times` int(2) NOT NULL COMMENT '当前通知次数',
  `response` text COMMENT '通知响应结果',
  `status` int(2) NOT NULL COMMENT '通知结果',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='支付任务的通知日志';

-- 正在导出表  xcode-pay.pay_notify_log 的数据：~0 rows (大约)
DELETE FROM `pay_notify_log`;

-- 导出  表 xcode-pay.pay_notify_task 结构
CREATE TABLE IF NOT EXISTS `pay_notify_task` (
  `id` int(12) unsigned NOT NULL AUTO_INCREMENT COMMENT '任务id 自增',
  `app_id` int(12) unsigned NOT NULL COMMENT '应用编号',
  `notify_type` varchar(20) NOT NULL COMMENT '通知类型 支付或者退款',
  `bizId` varchar(50) NOT NULL COMMENT '业务id 订单或者退费id',
  `status` int(2) NOT NULL COMMENT '通知状态',
  `next_notify_time` datetime DEFAULT NULL COMMENT '下一次通知时间',
  `last_execute_time` datetime DEFAULT NULL COMMENT '最后一次执行的时间',
  `current_notify_times` int(3) unsigned NOT NULL DEFAULT '0' COMMENT '当前通知次数',
  `max_notify_times` int(3) NOT NULL COMMENT '默认通知次数',
  `notify_url` text NOT NULL COMMENT '通知地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='支付回调通知任务表';

-- 正在导出表  xcode-pay.pay_notify_task 的数据：~0 rows (大约)
DELETE FROM `pay_notify_task`;

-- 导出  表 xcode-pay.pay_order 结构
CREATE TABLE IF NOT EXISTS `pay_order` (
  `id` bigint(30) NOT NULL COMMENT '订单号',
  `app_id` int(12) NOT NULL COMMENT '应用id',
  `channel_code` varchar(20) NOT NULL COMMENT '渠道编号',
  `merchant_order_id` varchar(50) DEFAULT NULL COMMENT '商户订单编号',
  `subject` varchar(50) NOT NULL COMMENT '商品标题',
  `body` text NOT NULL COMMENT '商品描述',
  `notify_url` text NOT NULL COMMENT '异步通知地址',
  `price` int(12) NOT NULL COMMENT '支付金额 单位分',
  `channel_fee_price` int(12) NOT NULL COMMENT '渠道费 单位分',
  `status` int(2) NOT NULL COMMENT '支付状态',
  `user_ip` varchar(50) NOT NULL COMMENT '用户ip',
  `expire_time` datetime DEFAULT NULL COMMENT '订单失效时间',
  `success_time` datetime DEFAULT NULL COMMENT '订单支付成功时间',
  `out_trade_no` varchar(50) DEFAULT NULL COMMENT '外部订单编号',
  `refund_price` int(12) DEFAULT NULL COMMENT '退费金额',
  `channel_user_id` varchar(50) DEFAULT NULL COMMENT '渠道用户id 如openid',
  `channel_order_no` varchar(50) DEFAULT NULL COMMENT '渠道订单号',
  `channel_extras` text COMMENT '渠道支付额外参数',
  `channel_error_code` varchar(50) DEFAULT NULL COMMENT '渠道错误码',
  `channel_error_msg` text COMMENT '渠道错误信息',
  `channel_notify_data` text COMMENT '渠道通知内容',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='支付订单表';

-- 正在导出表  xcode-pay.pay_order 的数据：~0 rows (大约)
DELETE FROM `pay_order`;

-- 导出  表 xcode-pay.pay_refund 结构
CREATE TABLE IF NOT EXISTS `pay_refund` (
  `id` bigint(30) NOT NULL COMMENT '退款订单号',
  `out_refund_no` varchar(50) NOT NULL COMMENT '外部退款订单编号',
  `app_id` int(12) NOT NULL COMMENT '应用编号',
  `channel_code` varchar(20) NOT NULL COMMENT '渠道码编号',
  `order_id` bigint(30) NOT NULL COMMENT '订单号',
  `out_trade_no` varchar(50) NOT NULL COMMENT '外部订单号',
  `merchant_order_id` varchar(50) NOT NULL COMMENT '商户订单编号',
  `merchant_refund_id` varchar(50) NOT NULL COMMENT '商户退款订单号',
  `notify_url` text NOT NULL COMMENT '退款异步通知url',
  `status` int(2) NOT NULL COMMENT '退款状态',
  `refund_price` int(12) NOT NULL COMMENT '退款金额 单位分',
  `pay_price` int(12) NOT NULL COMMENT '支付金额 单位分',
  `reason` text NOT NULL COMMENT '退款理由',
  `user_ip` varchar(50) NOT NULL COMMENT '用户ip',
  `channel_order_no` varchar(50) DEFAULT NULL COMMENT '渠道订单编号',
  `channel_refund_no` varchar(50) DEFAULT NULL COMMENT '渠道退款编号',
  `success_time` datetime DEFAULT NULL COMMENT '退款成功时间',
  `channel_error_code` varchar(50) DEFAULT NULL COMMENT '渠道错误码',
  `channel_error_msg` text COMMENT '渠道错误信息',
  `channel_notify_data` text COMMENT '渠道通知数据',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='支付退款表';

-- 正在导出表  xcode-pay.pay_refund 的数据：~0 rows (大约)
DELETE FROM `pay_refund`;


-- 导出 xcode-system 的数据库结构
CREATE DATABASE IF NOT EXISTS `xcode-system` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
USE `xcode-system`;

-- 导出  表 xcode-system.system_client 结构
CREATE TABLE IF NOT EXISTS `system_client` (
  `client_id` varchar(30) NOT NULL COMMENT '客户端id',
  `subject_id` varchar(50) DEFAULT NULL COMMENT '申请者唯一标识',
  `client_key` varchar(50) DEFAULT NULL COMMENT '客户端key',
  `client_secret` varchar(50) DEFAULT NULL COMMENT '客户端密钥',
  `scopes` varchar(100) DEFAULT NULL COMMENT '授权范围',
  `grant_type` varchar(255) DEFAULT NULL COMMENT '授权类型',
  `redirect_uris` varchar(255) DEFAULT NULL COMMENT '允许重定向的地址',
  `access_token_validity` int(10) DEFAULT NULL COMMENT '访问令牌过期时间',
  `refresh_token_validity` int(10) DEFAULT NULL COMMENT '刷新令牌过期时间',
  `status` char(1) DEFAULT NULL COMMENT '0 可用 1不可用',
  `create_user` varchar(30) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统客户端表';

-- 正在导出表  xcode-system.system_client 的数据：~0 rows (大约)
DELETE FROM `system_client`;

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
DELETE FROM `system_dict`;

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
DELETE FROM `system_resource`;

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
DELETE FROM `system_role`;

-- 导出  表 xcode-system.system_role_resource 结构
CREATE TABLE IF NOT EXISTS `system_role_resource` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `resource_id` int(12) NOT NULL COMMENT '资源id',
  `role_id` int(12) NOT NULL COMMENT '角色id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色资源表';

-- 正在导出表  xcode-system.system_role_resource 的数据：~0 rows (大约)
DELETE FROM `system_role_resource`;

-- 导出  表 xcode-system.system_route 结构
CREATE TABLE IF NOT EXISTS `system_route` (
  `id` int(12) NOT NULL AUTO_INCREMENT,
  `service_name` varchar(50) NOT NULL,
  `route` varchar(50) DEFAULT NULL COMMENT '路由',
  `create_user` bigint(20) DEFAULT NULL COMMENT '创建用户',
  `update_user` bigint(20) DEFAULT NULL COMMENT '更新用户',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统路由表';

-- 正在导出表  xcode-system.system_route 的数据：~0 rows (大约)
DELETE FROM `system_route`;

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
DELETE FROM `system_third_user`;

-- 导出  表 xcode-system.system_user 结构
CREATE TABLE IF NOT EXISTS `system_user` (
  `id` bigint(20) NOT NULL COMMENT '用户id',
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
DELETE FROM `system_user`;

-- 导出  表 xcode-system.system_user_role 结构
CREATE TABLE IF NOT EXISTS `system_user_role` (
  `id` int(12) NOT NULL COMMENT '自增id',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `role_id` int(12) NOT NULL COMMENT '角色id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统用户角色表';

-- 正在导出表  xcode-system.system_user_role 的数据：~0 rows (大约)
DELETE FROM `system_user_role`;


-- 导出 xcode-takeout 的数据库结构
CREATE DATABASE IF NOT EXISTS `xcode-takeout` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
USE `xcode-takeout`;

-- 导出  表 xcode-takeout.takeout_address 结构
CREATE TABLE IF NOT EXISTS `takeout_address` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `consignee` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '收货人',
  `sex` char(1) COLLATE utf8_bin NOT NULL COMMENT '性别 0 男 1 女',
  `phone` varchar(11) COLLATE utf8_bin NOT NULL COMMENT '手机号',
  `province_code` varchar(12) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '省级区划编号',
  `province_name` varchar(32) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '省级名称',
  `city_code` varchar(12) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '市级区划编号',
  `city_name` varchar(32) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '市级名称',
  `district_code` varchar(12) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '区级区划编号',
  `district_name` varchar(32) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '区级名称',
  `detail` varchar(200) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '详细地址',
  `label` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '标签',
  `is_default` int(1) NOT NULL DEFAULT '0' COMMENT '默认 0 否 1是',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `create_user` bigint(20) NOT NULL COMMENT '创建人',
  `update_user` bigint(20) NOT NULL COMMENT '修改人',
  `is_deleted` int(11) NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='地址管理';

-- 正在导出表  xcode-takeout.takeout_address 的数据：~0 rows (大约)
DELETE FROM `takeout_address`;

-- 导出  表 xcode-takeout.takeout_category 结构
CREATE TABLE IF NOT EXISTS `takeout_category` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `type` int(11) DEFAULT NULL COMMENT '类型   1 菜品分类 2 套餐分类',
  `name` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '分类名称',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '顺序',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `create_user` bigint(20) NOT NULL COMMENT '创建人',
  `update_user` bigint(20) NOT NULL COMMENT '修改人',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `idx_category_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='菜品及套餐分类';

-- 正在导出表  xcode-takeout.takeout_category 的数据：~7 rows (大约)
DELETE FROM `takeout_category`;
INSERT INTO `takeout_category` (`id`, `type`, `name`, `sort`, `create_time`, `update_time`, `create_user`, `update_user`) VALUES
	(1397844263642378242, 1, '湘菜', 1, '2021-05-27 09:16:58', '2021-07-15 20:25:23', 1, 1),
	(1397844303408574465, 1, '川菜', 2, '2021-05-27 09:17:07', '2021-06-02 14:27:22', 1, 1),
	(1397844391040167938, 1, '粤菜', 3, '2021-05-27 09:17:28', '2021-07-09 14:37:13', 1, 1),
	(1413341197421846529, 1, '饮品', 11, '2021-07-09 11:36:15', '2021-07-09 14:39:15', 1, 1),
	(1413342269393674242, 2, '商务套餐', 5, '2021-07-09 11:40:30', '2021-07-09 14:43:45', 1, 1),
	(1413384954989060097, 1, '主食', 12, '2021-07-09 14:30:07', '2021-07-09 14:39:19', 1, 1),
	(1413386191767674881, 2, '儿童套餐', 6, '2021-07-09 14:35:02', '2021-07-09 14:39:05', 1, 1);

-- 导出  表 xcode-takeout.takeout_dish 结构
CREATE TABLE IF NOT EXISTS `takeout_dish` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `name` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '菜品名称',
  `category_id` bigint(20) NOT NULL COMMENT '菜品分类id',
  `price` decimal(10,2) DEFAULT NULL COMMENT '菜品价格',
  `code` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '商品码',
  `image` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '图片',
  `stock` int(12) NOT NULL DEFAULT '9999' COMMENT '菜品库存',
  `description` varchar(400) COLLATE utf8_bin DEFAULT NULL COMMENT '描述信息',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT ' 0 起售 1 停售',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '顺序',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `create_user` bigint(20) NOT NULL COMMENT '创建人',
  `update_user` bigint(20) NOT NULL COMMENT '修改人',
  `is_deleted` int(11) NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='菜品管理';

-- 正在导出表  xcode-takeout.takeout_dish 的数据：~23 rows (大约)
DELETE FROM `takeout_dish`;
INSERT INTO `takeout_dish` (`id`, `name`, `category_id`, `price`, `code`, `image`, `stock`, `description`, `status`, `sort`, `create_time`, `update_time`, `create_user`, `update_user`, `is_deleted`) VALUES
	(1397849739276890114, '辣子鸡', 1397844263642378242, 7800.00, '222222222', 'f966a38e-0780-40be-bb52-5699d13cb3d9.jpg', 9999, '来自鲜嫩美味的小鸡，值得一尝', 1, 0, '2021-05-27 09:38:43', '2021-05-27 09:38:43', 1, 1, 0),
	(1397850140982161409, '毛氏红烧肉', 1397844263642378242, 6800.00, '123412341234', '0a3b3288-3446-4420-bbff-f263d0c02d8e.jpg', 9999, '毛氏红烧肉毛氏红烧肉，确定不来一份？', 1, 0, '2021-05-27 09:40:19', '2021-05-27 09:40:19', 1, 1, 0),
	(1397850392090947585, '组庵鱼翅', 1397844263642378242, 4800.00, '123412341234', '740c79ce-af29-41b8-b78d-5f49c96e38c4.jpg', 9999, '组庵鱼翅，看图足以表明好吃程度', 1, 0, '2021-05-27 09:41:19', '2021-05-27 09:41:19', 1, 1, 0),
	(1397850851245600769, '霸王别姬', 1397844263642378242, 12800.00, '123412341234', '057dd338-e487-4bbc-a74c-0384c44a9ca3.jpg', 9999, '还有什么比霸王别姬更美味的呢？', 1, 0, '2021-05-27 09:43:08', '2021-05-27 09:43:08', 1, 1, 0),
	(1397851099502260226, '全家福', 1397844263642378242, 11800.00, '23412341234', 'a53a4e6a-3b83-4044-87f9-9d49b30a8fdc.jpg', 9999, '别光吃肉啦，来份全家福吧，让你长寿又美味', 1, 0, '2021-05-27 09:44:08', '2021-05-27 09:44:08', 1, 1, 0),
	(1397851370462687234, '邵阳猪血丸子', 1397844263642378242, 13800.00, '1246812345678', '2a50628e-7758-4c51-9fbb-d37c61cdacad.jpg', 9999, '看，美味不？来嘛来嘛，这才是最爱吖', 1, 0, '2021-05-27 09:45:12', '2021-05-27 09:45:12', 1, 1, 0),
	(1397851668262465537, '口味蛇', 1397844263642378242, 16800.00, '1234567812345678', '0f4bd884-dc9c-4cf9-b59e-7d5958fec3dd.jpg', 9999, '爬行界的扛把子，东兴-口味蛇，让你欲罢不能', 1, 0, '2021-05-27 09:46:23', '2021-05-27 09:46:23', 1, 1, 0),
	(1397852391150759938, '辣子鸡丁', 1397844303408574465, 8800.00, '2346812468', 'ef2b73f2-75d1-4d3a-beea-22da0e1421bd.jpg', 9999, '辣子鸡丁，辣子鸡丁，永远的魂', 1, 0, '2021-05-27 09:49:16', '2021-05-27 09:49:16', 1, 1, 0),
	(1397853183287013378, '麻辣兔头', 1397844303408574465, 19800.00, '123456787654321', '2a2e9d66-b41d-4645-87bd-95f2cfeed218.jpg', 9999, '麻辣兔头的详细制作，麻辣鲜香，色泽红润，回味悠长', 1, 0, '2021-05-27 09:52:24', '2021-05-27 09:52:24', 1, 1, 0),
	(1397853709101740034, '蒜泥白肉', 1397844303408574465, 9800.00, '1234321234321', 'd2f61d70-ac85-4529-9b74-6d9a2255c6d7.jpg', 9999, '多么的有食欲啊', 1, 0, '2021-05-27 09:54:30', '2021-05-27 09:54:30', 1, 1, 0),
	(1397853890262118402, '鱼香肉丝', 1397844303408574465, 3800.00, '1234212321234', '8dcfda14-5712-4d28-82f7-ae905b3c2308.jpg', 9999, '鱼香肉丝简直就是我们童年回忆的一道经典菜，上学的时候点个鱼香肉丝盖饭坐在宿舍床上看着肥皂剧，绝了！现在完美复刻一下上学的时候感觉', 1, 0, '2021-05-27 09:55:13', '2021-05-27 09:55:13', 1, 1, 0),
	(1397854652581064706, '麻辣水煮鱼', 1397844303408574465, 14800.00, '2345312·345321', '1fdbfbf3-1d86-4b29-a3fc-46345852f2f8.jpg', 9999, '鱼片是买的切好的鱼片，放几个虾，增加味道', 1, 0, '2021-05-27 09:58:15', '2021-05-27 09:58:15', 1, 1, 0),
	(1397854865672679425, '鱼香炒鸡蛋', 1397844303408574465, 2000.00, '23456431·23456', '0f252364-a561-4e8d-8065-9a6797a6b1d3.jpg', 9999, '鱼香菜也是川味的特色。里面没有鱼却鱼香味', 1, 0, '2021-05-27 09:59:06', '2021-05-27 09:59:06', 1, 1, 0),
	(1397860242057375745, '脆皮烧鹅', 1397844391040167938, 12800.00, '123456786543213456', 'e476f679-5c15-436b-87fa-8c4e9644bf33.jpeg', 9999, '“广东烤鸭美而香，却胜烧鹅说古冈（今新会），燕瘦环肥各佳妙，君休偏重便宜坊”，可见烧鹅与烧鸭在粤菜之中已早负盛名。作为广州最普遍和最受欢迎的烧烤肉食，以它的“色泽金红，皮脆肉嫩，味香可口”的特色，在省城各大街小巷的烧卤店随处可见。', 1, 0, '2021-05-27 10:20:27', '2021-05-27 10:20:27', 1, 1, 0),
	(1397860578738352129, '白切鸡', 1397844391040167938, 6600.00, '12345678654', '9ec6fc2d-50d2-422e-b954-de87dcd04198.jpeg', 9999, '白切鸡是一道色香味俱全的特色传统名肴，又叫白斩鸡，是粤菜系鸡肴中的一种，始于清代的民间。白切鸡通常选用细骨农家鸡与沙姜、蒜茸等食材，慢火煮浸白切鸡皮爽肉滑，清淡鲜美。著名的泮溪酒家白切鸡，曾获商业部优质产品金鼎奖。湛江白切鸡更是驰名粤港澳。粤菜厨坛中，鸡的菜式有200余款之多，而最为人常食不厌的正是白切鸡，深受食家青睐。', 1, 0, '2021-05-27 10:21:48', '2021-05-27 10:21:48', 1, 1, 0),
	(1397860792492666881, '烤乳猪', 1397844391040167938, 38800.00, '213456432123456', '2e96a7e3-affb-438e-b7c3-e1430df425c9.jpeg', 9999, '广式烧乳猪主料是小乳猪，辅料是蒜，调料是五香粉、芝麻酱、八角粉等，本菜品主要通过将食材放入炭火中烧烤而成。烤乳猪是广州最著名的特色菜，并且是“满汉全席”中的主打菜肴之一。烤乳猪也是许多年来广东人祭祖的祭品之一，是家家都少不了的应节之物，用乳猪祭完先人后，亲戚们再聚餐食用。', 1, 0, '2021-05-27 10:22:39', '2021-05-27 10:22:39', 1, 1, 0),
	(1397860963880316929, '脆皮乳鸽', 1397844391040167938, 10800.00, '1234563212345', '3fabb83a-1c09-4fd9-892b-4ef7457daafa.jpeg', 9999, '“脆皮乳鸽”是广东菜中的一道传统名菜，属于粤菜系，具有皮脆肉嫩、色泽红亮、鲜香味美的特点，常吃可使身体强健，清肺顺气。随着菜品制作工艺的不断发展，逐渐形成了熟炸法、生炸法和烤制法三种制作方法。无论那种制作方法，都是在鸽子经过一系列的加工，挂脆皮水后再加工而成，正宗的“脆皮乳鸽皮脆肉嫩、色泽红亮、鲜香味美、香气馥郁。这三种方法的制作过程都不算复杂，但想达到理想的效果并不容易。', 1, 0, '2021-05-27 10:23:19', '2021-05-27 10:23:19', 1, 1, 0),
	(1397861683434139649, '清蒸河鲜海鲜', 1397844391040167938, 38800.00, '1234567876543213456', '1405081e-f545-42e1-86a2-f7559ae2e276.jpeg', 9999, '新鲜的海鲜，清蒸是最好的处理方式。鲜，体会为什么叫海鲜。清蒸是广州最经典的烹饪手法，过去岭南地区由于峻山大岭阻隔，交通不便，经济发展起步慢，自家打的鱼放在锅里煮了就吃，没有太多的讲究，但却发现这清淡的煮法能使鱼的鲜甜跃然舌尖。', 1, 0, '2021-05-27 10:26:11', '2021-05-27 10:26:11', 1, 1, 0),
	(1397862198033297410, '老火靓汤', 1397844391040167938, 49800.00, '123456786532455', '583df4b7-a159-4cfc-9543-4f666120b25f.jpeg', 9999, '老火靓汤又称广府汤，是广府人传承数千年的食补养生秘方，慢火煲煮的中华老火靓汤，火候足，时间长，既取药补之效，又取入口之甘甜。 广府老火汤种类繁多，可以用各种汤料和烹调方法，烹制出各种不同口味、不同功效的汤来。', 1, 0, '2021-05-27 10:28:14', '2021-05-27 10:28:14', 1, 1, 0),
	(1397862477831122945, '上汤焗龙虾', 1397844391040167938, 108800.00, '1234567865432', '5b8d2da3-3744-4bb3-acdc-329056b8259d.jpeg', 9999, '上汤焗龙虾是一道色香味俱全的传统名菜，属于粤菜系。此菜以龙虾为主料，配以高汤制成的一道海鲜美食。本品肉质洁白细嫩，味道鲜美，蛋白质含量高，脂肪含量低，营养丰富。是色香味俱全的传统名菜。', 1, 0, '2021-05-27 10:29:20', '2021-05-27 10:29:20', 1, 1, 0),
	(1413342036832100354, '北冰洋', 1413341197421846529, 500.00, '', 'c99e0aab-3cb7-4eaa-80fd-f47d4ffea694.png', 9999, '', 1, 0, '2021-07-09 11:39:35', '2021-07-09 15:12:18', 1, 1, 0),
	(1413384757047271425, '王老吉', 1413341197421846529, 500.00, '', '00874a5e-0df2-446b-8f69-a30eb7d88ee8.png', 9999, '', 1, 0, '2021-07-09 14:29:20', '2021-07-12 09:09:16', 1, 1, 0),
	(1413385247889891330, '米饭', 1413384954989060097, 200.00, '', 'ee04a05a-1230-46b6-8ad5-1a95b140fff3.png', 9999, '', 1, 0, '2021-07-09 14:31:17', '2021-07-11 16:35:26', 1, 1, 0);

-- 导出  表 xcode-takeout.takeout_dish_flavor 结构
CREATE TABLE IF NOT EXISTS `takeout_dish_flavor` (
  `id` bigint(20) NOT NULL COMMENT '主键id',
  `dish_id` bigint(20) NOT NULL COMMENT '菜品id',
  `flavor_id` bigint(20) NOT NULL COMMENT '口味id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜品口味关系表';

-- 正在导出表  xcode-takeout.takeout_dish_flavor 的数据：~0 rows (大约)
DELETE FROM `takeout_dish_flavor`;

-- 导出  表 xcode-takeout.takeout_flavor 结构
CREATE TABLE IF NOT EXISTS `takeout_flavor` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `name` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '口味名称',
  `value` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '口味数据list',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `create_user` bigint(20) NOT NULL COMMENT '创建人',
  `update_user` bigint(20) NOT NULL COMMENT '修改人',
  `is_deleted` int(11) NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='菜品口味表';

-- 正在导出表  xcode-takeout.takeout_flavor 的数据：~0 rows (大约)
DELETE FROM `takeout_flavor`;

-- 导出  表 xcode-takeout.takeout_orders 结构
CREATE TABLE IF NOT EXISTS `takeout_orders` (
  `id` bigint(20) NOT NULL COMMENT '订单id',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '订单状态 1待付款，2待派送，3已派送，4已完成，5已取消',
  `user_id` bigint(20) NOT NULL COMMENT '下单用户',
  `address_id` bigint(20) NOT NULL COMMENT '地址id',
  `order_time` datetime NOT NULL COMMENT '下单时间',
  `checkout_time` datetime NOT NULL COMMENT '结账时间',
  `pay_method` int(11) NOT NULL DEFAULT '1' COMMENT '支付方式 1微信,2支付宝',
  `amount` decimal(10,2) NOT NULL COMMENT '实收金额',
  `remark` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `mobile` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `address` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `user_name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `consignee` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='订单表';

-- 正在导出表  xcode-takeout.takeout_orders 的数据：~0 rows (大约)
DELETE FROM `takeout_orders`;

-- 导出  表 xcode-takeout.takeout_order_detail 结构
CREATE TABLE IF NOT EXISTS `takeout_order_detail` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '名字',
  `image` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '图片',
  `order_id` bigint(20) NOT NULL COMMENT '订单id',
  `dish_id` bigint(20) DEFAULT NULL COMMENT '菜品id',
  `setmeal_id` bigint(20) DEFAULT NULL COMMENT '套餐id',
  `dish_flavor` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '口味',
  `number` int(11) NOT NULL DEFAULT '1' COMMENT '数量',
  `amount` decimal(10,2) NOT NULL COMMENT '金额',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='订单明细表';

-- 正在导出表  xcode-takeout.takeout_order_detail 的数据：~0 rows (大约)
DELETE FROM `takeout_order_detail`;

-- 导出  表 xcode-takeout.takeout_permission 结构
CREATE TABLE IF NOT EXISTS `takeout_permission` (
  `id` int(12) NOT NULL COMMENT '自增id',
  `code` varchar(50) NOT NULL COMMENT '资源名',
  `create_user` bigint(20) DEFAULT NULL COMMENT '创建用户',
  `update_user` bigint(20) DEFAULT NULL COMMENT '更新用户',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_deleted` int(1) NOT NULL DEFAULT '0' COMMENT '是否删除 0否 1是',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户权限表';

-- 正在导出表  xcode-takeout.takeout_permission 的数据：~0 rows (大约)
DELETE FROM `takeout_permission`;

-- 导出  表 xcode-takeout.takeout_role 结构
CREATE TABLE IF NOT EXISTS `takeout_role` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `name` varchar(50) NOT NULL COMMENT '角色名称',
  `create_user` bigint(20) DEFAULT NULL COMMENT '创建用户',
  `update_user` bigint(20) DEFAULT NULL COMMENT '更新用户',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_deleted` char(1) NOT NULL DEFAULT '0' COMMENT '是否删除 0没有 1是',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

-- 正在导出表  xcode-takeout.takeout_role 的数据：~0 rows (大约)
DELETE FROM `takeout_role`;

-- 导出  表 xcode-takeout.takeout_role_permission 结构
CREATE TABLE IF NOT EXISTS `takeout_role_permission` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `role_id` int(12) NOT NULL COMMENT '角色id',
  `permission_id` int(12) NOT NULL COMMENT '权限id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色权限关联表';

-- 正在导出表  xcode-takeout.takeout_role_permission 的数据：~0 rows (大约)
DELETE FROM `takeout_role_permission`;

-- 导出  表 xcode-takeout.takeout_setmeal 结构
CREATE TABLE IF NOT EXISTS `takeout_setmeal` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `category_id` bigint(20) NOT NULL COMMENT '套餐分类id',
  `name` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '套餐名称',
  `price` decimal(10,2) NOT NULL COMMENT '套餐价格',
  `stock` int(12) NOT NULL DEFAULT '9999' COMMENT '套餐库存',
  `status` int(11) DEFAULT NULL COMMENT '状态 0:启用 1:停用',
  `code` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '编码',
  `description` varchar(512) COLLATE utf8_bin DEFAULT NULL COMMENT '描述信息',
  `image` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '图片',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `create_user` bigint(20) NOT NULL COMMENT '创建人',
  `update_user` bigint(20) NOT NULL COMMENT '修改人',
  `is_deleted` int(11) NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `idx_setmeal_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='套餐';

-- 正在导出表  xcode-takeout.takeout_setmeal 的数据：~0 rows (大约)
DELETE FROM `takeout_setmeal`;

-- 导出  表 xcode-takeout.takeout_setmeal_dish 结构
CREATE TABLE IF NOT EXISTS `takeout_setmeal_dish` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `setmeal_id` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '套餐id ',
  `dish_id` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '菜品id',
  `name` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '菜品名称 （冗余字段）',
  `price` decimal(10,2) DEFAULT NULL COMMENT '菜品原价（冗余字段）',
  `copies` int(11) NOT NULL COMMENT '份数',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '排序',
  `is_deleted` int(11) NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='套餐菜品关系';

-- 正在导出表  xcode-takeout.takeout_setmeal_dish 的数据：~0 rows (大约)
DELETE FROM `takeout_setmeal_dish`;

-- 导出  表 xcode-takeout.takeout_shopping_cart 结构
CREATE TABLE IF NOT EXISTS `takeout_shopping_cart` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '名称',
  `image` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '图片',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `dish_id` bigint(20) DEFAULT NULL COMMENT '菜品id',
  `setmeal_id` bigint(20) DEFAULT NULL COMMENT '套餐id',
  `dish_flavor` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '口味',
  `number` int(11) NOT NULL DEFAULT '1' COMMENT '数量',
  `amount` decimal(10,2) NOT NULL COMMENT '金额',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='购物车';

-- 正在导出表  xcode-takeout.takeout_shopping_cart 的数据：~0 rows (大约)
DELETE FROM `takeout_shopping_cart`;

-- 导出  表 xcode-takeout.takeout_user 结构
CREATE TABLE IF NOT EXISTS `takeout_user` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '姓名',
  `role_id` int(12) NOT NULL COMMENT '角色id',
  `mobile` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '手机号',
  `password` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '密码',
  `sex` char(1) COLLATE utf8_bin NOT NULL COMMENT '性别 0 男 1 女',
  `id_number` varchar(18) COLLATE utf8_bin DEFAULT NULL COMMENT '身份证号',
  `avatar` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '头像',
  `status` int(11) DEFAULT '0' COMMENT '状态 0:正常，1:禁用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户信息';

-- 正在导出表  xcode-takeout.takeout_user 的数据：~0 rows (大约)
DELETE FROM `takeout_user`;

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
