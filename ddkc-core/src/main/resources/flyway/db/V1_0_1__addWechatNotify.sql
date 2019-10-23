/*
 Navicat Premium Data Transfer

 Source Server         : 本地库
 Source Server Type    : MySQL
 Source Server Version : 50720
 Source Host           : localhost:3306
 Source Schema         : trip

 Target Server Type    : MySQL
 Target Server Version : 50720
 File Encoding         : 65001

 Date: 06/08/2019 16:58:22
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_wechat_notify
-- ----------------------------
DROP TABLE IF EXISTS `t_wechat_notify`;
CREATE TABLE `t_wechat_notify` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `appid` varchar(32) DEFAULT NULL COMMENT '公众号id',
  `mch_id` varchar(32) DEFAULT NULL COMMENT '商户号',
  `device_info` varchar(32) DEFAULT NULL COMMENT '微信支付分配的终端设备号，',
  `nonce_str` varchar(32) DEFAULT NULL COMMENT '随机字符串，不长于32位',
  `sign` varchar(32) DEFAULT NULL COMMENT '签名',
  `sign_type` varchar(32) DEFAULT NULL COMMENT '签名类型，默认MD5',
  `result_code` varchar(16) DEFAULT NULL COMMENT 'SUCCESS/FAIL',
  `err_code` varchar(32) DEFAULT NULL COMMENT '错误返回的信息描述',
  `err_code_des` varchar(128) DEFAULT NULL COMMENT '错误返回的信息描述',
  `openid` varchar(128) DEFAULT NULL COMMENT '用户在商户appid下的唯一标识',
  `is_subscribe` varchar(1) DEFAULT NULL COMMENT '用户是否关注公众账号，Y-关注，N-未关注，仅在公众账号类型支付有效',
  `trade_type` varchar(16) DEFAULT NULL COMMENT '交易类型',
  `bank_type` varchar(16) DEFAULT NULL COMMENT '付款银行',
  `total_fee` int(100) DEFAULT NULL COMMENT '总金额',
  `fee_type` varchar(8) DEFAULT NULL COMMENT '货币种类。货币类型，符合ISO4217标准的三位字母代码，默认人民币：CNY',
  `cash_fee` int(100) DEFAULT NULL COMMENT '现金支付金额',
  `cash_fee_type` varchar(16) DEFAULT NULL COMMENT '现金支付货币类型。 货币类型，符合ISO4217标准的三位字母代码，默认人民币：CNY',
  `coupon_fee` int(10) DEFAULT NULL COMMENT '代金券或立减优惠金额',
  `coupon_count` int(1) DEFAULT NULL COMMENT '代金券或立减优惠使用数量',
  `coupon_id_no` varchar(20) DEFAULT NULL COMMENT '代金券或立减优惠ID',
  `coupon_fee_no` int(20) DEFAULT NULL COMMENT '单个代金券或立减优惠支付金额',
  `transaction_id` varchar(32) DEFAULT NULL COMMENT '微信订单号ID',
  `out_trade_no` varchar(32) DEFAULT NULL COMMENT '商户订单号',
  `attach` varchar(128) DEFAULT NULL COMMENT '商家数据包',
  `time_end` varchar(14) DEFAULT NULL COMMENT '支付完成时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2308 DEFAULT CHARSET=utf8mb4;

SET FOREIGN_KEY_CHECKS = 1;