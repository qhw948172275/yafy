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

 Date: 23/09/2019 17:44:16
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_shop_account
-- ----------------------------
DROP TABLE IF EXISTS `t_shop_account`;
CREATE TABLE `t_shop_account` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '商户账号id',
  `shop_id` int(20) DEFAULT NULL COMMENT '商户ID',
  `account` varchar(256) DEFAULT NULL COMMENT '账号',
  `password` varchar(256) DEFAULT NULL COMMENT '密码',
  `is_master` int(2) DEFAULT NULL COMMENT '是否是主账号。0表示否；1表示是',
  `is_notice` int(2) DEFAULT NULL COMMENT '是否接收语音通知。0表示否；1表示是',
  `status` int(2) DEFAULT NULL COMMENT '状态。0表示正常；1表示禁用；2表示已删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `creator_id` int(20) DEFAULT NULL COMMENT '创建人ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商户账号表';

SET FOREIGN_KEY_CHECKS = 1;