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


-- 导出 xk-learn-member 的数据库结构
CREATE DATABASE IF NOT EXISTS `xk-learn-member` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
USE `xk-learn-member`;

-- 导出  表 xk-learn-member.member_address 结构
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

-- 数据导出被取消选择。

-- 导出  表 xk-learn-member.member_config 结构
CREATE TABLE IF NOT EXISTS `member_config` (
                                               `id` int(12) NOT NULL COMMENT '自增id',
                                               `point_deduct_enable` char(1) DEFAULT '0' COMMENT '积分抵用开关0 开启 1关闭',
                                               `point_deduct_unit` int(12) DEFAULT NULL COMMENT '1积分抵扣多少钱 单位：分',
                                               `max_point_deduct` int(12) DEFAULT NULL COMMENT '最大使用积分数',
                                               `given_point_add` int(12) DEFAULT NULL COMMENT '一元赠送的积分数量',
                                               PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员配置表';

-- 数据导出被取消选择。

-- 导出  表 xk-learn-member.member_experience_record 结构
CREATE TABLE IF NOT EXISTS `member_experience_record` (
                                                          `id` int(12) NOT NULL COMMENT '自增id',
                                                          `user_id` varchar(30) NOT NULL COMMENT '用户id',
                                                          `biz_type` int(2) NOT NULL COMMENT '增加或者减少经验的业务类型',
                                                          `biz_id` varchar(50) DEFAULT NULL COMMENT '业务类型对应的业务id',
                                                          `description` text COMMENT '业务类型对应的描述',
                                                          `title` varchar(255) DEFAULT NULL COMMENT '业务类型对应的标题',
                                                          `experience` int(12) DEFAULT NULL COMMENT '变更经验',
                                                          `total_experience` int(12) DEFAULT NULL COMMENT '变更后的总经验',
                                                          `create_time` datetime DEFAULT NULL COMMENT '记录时间',
                                                          PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员经验记录表';

-- 数据导出被取消选择。

-- 导出  表 xk-learn-member.member_group 结构
CREATE TABLE IF NOT EXISTS `member_group` (
                                              `id` int(12) NOT NULL COMMENT '自增id',
                                              `name` varchar(50) NOT NULL COMMENT '组名',
                                              `status` char(1) NOT NULL DEFAULT '0' COMMENT '是否启用 0启用 1未启用',
                                              PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员所属组表';

-- 数据导出被取消选择。

-- 导出  表 xk-learn-member.member_level 结构
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

-- 数据导出被取消选择。

-- 导出  表 xk-learn-member.member_level_change_record 结构
CREATE TABLE IF NOT EXISTS `member_level_change_record` (
                                                            `id` int(11) DEFAULT NULL,
                                                            `user_id` varchar(30) NOT NULL,
                                                            `level_id` int(12) NOT NULL,
                                                            `level_name` varchar(50) NOT NULL,
                                                            `total_experience` int(12) NOT NULL,
                                                            `change_experience` int(12) NOT NULL,
                                                            `title` varchar(255) NOT NULL,
                                                            `description` text,
                                                            `create_time` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员等级变更记录表';

-- 数据导出被取消选择。

-- 导出  表 xk-learn-member.member_login_record 结构
CREATE TABLE IF NOT EXISTS `member_login_record` (
                                                     `id` int(12) NOT NULL COMMENT '自增id',
                                                     `user_id` varchar(30) NOT NULL COMMENT '用户id',
                                                     `login_ip` varchar(30) NOT NULL COMMENT '登录ip',
                                                     `login_time` datetime NOT NULL COMMENT '登录时间',
                                                     `login_area_id` int(12) NOT NULL COMMENT '登录地区',
                                                     PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员登录记录表';

-- 数据导出被取消选择。

-- 导出  表 xk-learn-member.member_point_record 结构
CREATE TABLE IF NOT EXISTS `member_point_record` (
                                                     `id` int(11) NOT NULL COMMENT '自增id',
                                                     `user_id` varchar(30) NOT NULL COMMENT '用户id',
                                                     `biz_type` int(12) NOT NULL COMMENT '变更的业务类型',
                                                     `biz_id` varchar(50) DEFAULT NULL COMMENT '变更的业务id',
                                                     `title` varchar(255) NOT NULL COMMENT '变更业务类型的标题',
                                                     `description` text COMMENT '变更业务描述',
                                                     `change_point` int(12) NOT NULL COMMENT '变更积分',
                                                     `total_point` int(12) NOT NULL COMMENT '变更后的总积分',
                                                     `create_time` datetime DEFAULT NULL COMMENT '变更时间',
                                                     PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户积分记录表';

-- 数据导出被取消选择。

-- 导出  表 xk-learn-member.member_sign 结构
CREATE TABLE IF NOT EXISTS `member_sign` (
                                             `day` int(2) NOT NULL COMMENT '签到第几天 星期一到星期天1-7',
                                             `point` int(12) NOT NULL COMMENT '奖励积分',
                                             `experience` int(12) NOT NULL COMMENT '奖励经验',
                                             `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                                             PRIMARY KEY (`day`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员签到表';

-- 数据导出被取消选择。

-- 导出  表 xk-learn-member.member_sign_record 结构
CREATE TABLE IF NOT EXISTS `member_sign_record` (
                                                    `id` int(12) NOT NULL COMMENT '自增id',
                                                    `user_id` varchar(50) NOT NULL COMMENT '用户id',
                                                    `day` int(2) NOT NULL COMMENT '第几天签到',
                                                    `point` int(12) NOT NULL COMMENT '签到获得的积分',
                                                    `experience` int(12) NOT NULL COMMENT '签到获得的经验',
                                                    `create_time` datetime DEFAULT NULL COMMENT '签到时间',
                                                    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员签到记录表';

-- 数据导出被取消选择。

-- 导出  表 xk-learn-member.member_tag 结构
CREATE TABLE IF NOT EXISTS `member_tag` (
                                            `id` int(12) NOT NULL COMMENT '自增id',
                                            `name` varchar(50) NOT NULL COMMENT '标签名称',
                                            `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                            `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                                            PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户标签表';

-- 数据导出被取消选择。

-- 导出  表 xk-learn-member.member_user 结构
CREATE TABLE `member_user` (
                               `id` VARCHAR(30) NOT NULL COMMENT '用户id' COLLATE 'utf8mb4_general_ci',
                               `password` VARCHAR(100) NOT NULL COMMENT '用户密码' COLLATE 'utf8mb4_general_ci',
                               `mobile` CHAR(11) NOT NULL COMMENT '用户手机号' COLLATE 'utf8mb4_general_ci',
                               `email` VARCHAR(50) NULL DEFAULT NULL COMMENT '用户邮箱' COLLATE 'utf8mb4_general_ci',
                               `status` CHAR(1) NOT NULL DEFAULT '0' COMMENT ' 0 正常 1禁用' COLLATE 'utf8mb4_general_ci',
                               `nickname` VARCHAR(50) NOT NULL DEFAULT 'gasada_029' COMMENT '用户昵称' COLLATE 'utf8mb4_general_ci',
                               `level_id` INT(12) NOT NULL COMMENT '用户等级',
                               `experience` INT(12) NOT NULL COMMENT '用户经验',
                               `avatar` TEXT NULL DEFAULT NULL COMMENT '用户头像' COLLATE 'utf8mb4_general_ci',
                               `point` INT(12) NOT NULL COMMENT '用户积分',
                               `tag_ids` VARCHAR(255) NULL DEFAULT NULL COMMENT '用户标签' COLLATE 'utf8mb4_general_ci',
                               `group_id` INT(12) NULL DEFAULT NULL COMMENT '用户分组id',
                               `sex` CHAR(1) NULL DEFAULT NULL COMMENT '用户性别' COLLATE 'utf8mb4_general_ci',
                               `birthday` DATE NULL DEFAULT NULL COMMENT '用户生日',
                               `login_ip` VARCHAR(30) NULL DEFAULT NULL COMMENT '登录ip' COLLATE 'utf8mb4_general_ci',
                               `login_area_id` INT(12) NULL DEFAULT NULL COMMENT '登录区域',
                               `login_time` DATETIME NULL DEFAULT NULL COMMENT '登录时间',
                               PRIMARY KEY (`id`) USING BTREE
)
    COMMENT='会员用户表'
    COLLATE='utf8mb4_general_ci'
    ENGINE=InnoDB
;

-- 数据导出被取消选择。

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
