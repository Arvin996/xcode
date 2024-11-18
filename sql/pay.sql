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

-- 导出  表 xcode-pay.pay_app_channel 结构
CREATE TABLE IF NOT EXISTS `pay_app_channel` (
  `id` int(12) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `app_id` int(12) NOT NULL COMMENT '应用id',
  `channel_id` int(12) NOT NULL COMMENT '渠道id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='支付应用渠道表';

-- 正在导出表  xcode-pay.pay_app_channel 的数据：~0 rows (大约)

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

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
