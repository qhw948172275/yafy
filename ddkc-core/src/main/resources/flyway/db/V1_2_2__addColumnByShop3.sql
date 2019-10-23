/*
 Navicat Premium Data Transfer

 Source Server         : 本地库
 Source Server Type    : MySQL
 Source Server Version : 50720
 Source Host           : localhost:3306
 Source Schema         : ddkc

 Target Server Type    : MySQL
 Target Server Version : 50720
 File Encoding         : 65001

 Date: 23/09/2019 20:19:26
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_shop_account_notice_log
-- ----------------------------
DROP TABLE IF EXISTS `t_shop_account_notice_log`;
CREATE TABLE `t_shop_account_notice_log` (
  `id` int(20) NOT NULL COMMENT '语音提醒记录ID',
  `notice_order_id` varchar(256) DEFAULT NULL COMMENT '语音提醒订单ID',
  `order_id` int(20) DEFAULT NULL COMMENT '平台订单ID',
  `phone` varchar(256) DEFAULT NULL COMMENT '提醒电话',
  `result_code` varchar(128) DEFAULT NULL COMMENT '返回码',
  `result_msg` varchar(512) DEFAULT NULL COMMENT '返回信息',
  `create_time` datetime DEFAULT NULL COMMENT '提醒时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商户账号订单提醒记录';

SET FOREIGN_KEY_CHECKS = 1;
