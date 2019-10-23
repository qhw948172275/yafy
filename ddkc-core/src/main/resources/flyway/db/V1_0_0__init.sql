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

 Date: 06/08/2019 15:52:33
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for r_sys_role_resource
-- ----------------------------
DROP TABLE IF EXISTS `r_sys_role_resource`;
CREATE TABLE `r_sys_role_resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL DEFAULT '0' COMMENT '角色组ID',
  `resource_id` int(11) NOT NULL COMMENT '资源功能ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15565 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='权限对应请求';

-- ----------------------------
-- Records of r_sys_role_resource
-- ----------------------------
BEGIN;
INSERT INTO `r_sys_role_resource` VALUES (15555, 5, 6);
INSERT INTO `r_sys_role_resource` VALUES (15556, 5, 84);
INSERT INTO `r_sys_role_resource` VALUES (15557, 5, 85);
INSERT INTO `r_sys_role_resource` VALUES (15558, 5, 245);
INSERT INTO `r_sys_role_resource` VALUES (15559, 5, 246);
INSERT INTO `r_sys_role_resource` VALUES (15560, 5, 247);
INSERT INTO `r_sys_role_resource` VALUES (15561, 5, 86);
INSERT INTO `r_sys_role_resource` VALUES (15562, 5, 248);
INSERT INTO `r_sys_role_resource` VALUES (15563, 5, 249);
INSERT INTO `r_sys_role_resource` VALUES (15564, 5, 250);
COMMIT;

-- ----------------------------
-- Table structure for r_sys_role_user
-- ----------------------------
DROP TABLE IF EXISTS `r_sys_role_user`;
CREATE TABLE `r_sys_role_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=98 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of r_sys_role_user
-- ----------------------------
BEGIN;
INSERT INTO `r_sys_role_user` VALUES (91, 1, 4);
INSERT INTO `r_sys_role_user` VALUES (97, 58, 5);
COMMIT;

-- ----------------------------
-- Table structure for t_about_us
-- ----------------------------
DROP TABLE IF EXISTS `t_about_us`;
CREATE TABLE `t_about_us` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '服务协议ID',
  `types` int(11) DEFAULT NULL COMMENT '服务协议类型。0表示关于我们；1表示平台服务协议；2表示商家入驻协议',
  `content` text COMMENT '协议内容',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='协议信息表';

-- ----------------------------
-- Table structure for t_address
-- ----------------------------
DROP TABLE IF EXISTS `t_address`;
CREATE TABLE `t_address` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '收货地址ID',
  `member_id` int(11) DEFAULT NULL COMMENT '会员ID',
  `def` int(11) DEFAULT NULL COMMENT '是否默认。0表示否；1表示是',
  `province` varchar(258) DEFAULT NULL COMMENT '省',
  `city` varchar(258) DEFAULT NULL COMMENT '市',
  `area` varchar(258) DEFAULT NULL COMMENT '区',
  `address` varchar(256) DEFAULT NULL COMMENT '详细地址',
  `status` int(11) DEFAULT NULL COMMENT '状态。0表示正常；1表示已删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `lat` decimal(10,7) DEFAULT NULL COMMENT '经度',
  `lng` decimal(10,7) DEFAULT NULL COMMENT '纬度',
  `tag` varchar(128) DEFAULT NULL COMMENT '标签',
  `contact` varchar(512) DEFAULT NULL COMMENT '收货人',
  `phone` varchar(11) DEFAULT NULL COMMENT '联系电话',
  `contact_title` varchar(50) DEFAULT NULL COMMENT '称谓。先生/女士',
  PRIMARY KEY (`id`),
  KEY `fk_t_address_t_member` (`member_id`),
  CONSTRAINT `fk_t_address_t_member` FOREIGN KEY (`member_id`) REFERENCES `t_member` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='收货地址表';

-- ----------------------------
-- Table structure for t_cart
-- ----------------------------
DROP TABLE IF EXISTS `t_cart`;
CREATE TABLE `t_cart` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '购物车ID',
  `member_id` int(11) DEFAULT NULL COMMENT '会员ID',
  `shop_id` int(11) DEFAULT NULL COMMENT '店铺ID',
  `goods_id` int(11) DEFAULT NULL COMMENT '商品ID',
  `price` decimal(10,2) DEFAULT NULL COMMENT '商品原价',
  `sales_price` decimal(10,2) DEFAULT NULL COMMENT '销售价格',
  `counts` bigint(20) DEFAULT NULL COMMENT '数量',
  `status` int(11) DEFAULT NULL COMMENT '购物车状态。0表示正常；1表示已删除',
  `create_time` datetime DEFAULT NULL COMMENT '加入购物车时间',
  PRIMARY KEY (`id`),
  KEY `fk_t_cart_t_goods` (`goods_id`),
  KEY `fk_t_cart_t_member` (`member_id`),
  KEY `fk_t_cart_t_shop` (`shop_id`),
  CONSTRAINT `fk_t_cart_t_goods` FOREIGN KEY (`goods_id`) REFERENCES `t_goods` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_t_cart_t_member` FOREIGN KEY (`member_id`) REFERENCES `t_member` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_t_cart_t_shop` FOREIGN KEY (`shop_id`) REFERENCES `t_shop` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='购物车表';

-- ----------------------------
-- Table structure for t_goods
-- ----------------------------
DROP TABLE IF EXISTS `t_goods`;
CREATE TABLE `t_goods` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '商品ID',
  `goods_name` varchar(512) DEFAULT NULL COMMENT '商品名称',
  `shop_id` int(11) DEFAULT NULL COMMENT '店铺ID',
  `category_id` int(11) DEFAULT NULL COMMENT '商品分类ID',
  `cover` varchar(128) DEFAULT NULL COMMENT '商品封面',
  `details` varchar(1024) DEFAULT NULL COMMENT '商品详情图片信息',
  `price` decimal(10,2) DEFAULT NULL COMMENT '商品原价',
  `sales_price` decimal(10,2) DEFAULT NULL COMMENT '销售价格',
  `quanity` varchar(128) DEFAULT NULL COMMENT '数量单位。例如`盒`、`包`、`公斤`、`米`等。',
  `stock` bigint(20) DEFAULT NULL COMMENT '商品库存',
  `sales_counts` bigint(20) DEFAULT NULL COMMENT '销售量',
  `total_sales_price` decimal(10,2) DEFAULT NULL COMMENT '总销售额',
  `status` int(11) DEFAULT NULL COMMENT '状态。0表示上架；1表示下架',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `upate_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `fk_t_goods_t_shop` (`shop_id`),
  KEY `fk_t_goods_t_shop_category` (`category_id`),
  CONSTRAINT `fk_t_goods_t_shop` FOREIGN KEY (`shop_id`) REFERENCES `t_shop` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_t_goods_t_shop_category` FOREIGN KEY (`category_id`) REFERENCES `t_shop_category` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品表';

-- ----------------------------
-- Table structure for t_member
-- ----------------------------
DROP TABLE IF EXISTS `t_member`;
CREATE TABLE `t_member` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '会员ID',
  `avastar` varchar(512) DEFAULT NULL COMMENT '会员头像',
  `nick_name` varchar(512) DEFAULT NULL COMMENT '会员昵称',
  `phone` varchar(11) DEFAULT NULL COMMENT '会员手机号',
  `status` int(11) DEFAULT NULL COMMENT '状态。0表示启用；1表示禁用',
  `create_time` datetime DEFAULT NULL COMMENT '注册时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员表';

-- ----------------------------
-- Table structure for t_order
-- ----------------------------
DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `order_id` varchar(128) DEFAULT NULL COMMENT '订单号',
  `member_id` int(11) DEFAULT NULL COMMENT '会员ID',
  `total_price` decimal(10,2) DEFAULT NULL COMMENT '订单总价',
  `shop_id` int(11) DEFAULT NULL COMMENT '店铺ID',
  `status` int(11) DEFAULT NULL COMMENT '订单状态。0表示待支付；1表示待确认；2表示待收货；3表示已完成；4表示已取消；5表示退款中；6表示已退款',
  `create_time` datetime DEFAULT NULL COMMENT '下单时间',
  `pay_time` datetime DEFAULT NULL COMMENT '支付时间',
  `remark` varchar(512) DEFAULT NULL COMMENT '订单备注',
  `order_name` varchar(512) DEFAULT NULL COMMENT '订单名称',
  `contact` varchar(256) DEFAULT NULL COMMENT '收货人',
  `contact_phone` varchar(20) DEFAULT NULL COMMENT '联系电话',
  `contact_address` varchar(512) DEFAULT NULL COMMENT '收货地址',
  PRIMARY KEY (`id`),
  KEY `fk_t_order_t_member` (`member_id`),
  KEY `fk_t_order_t_shop` (`shop_id`),
  CONSTRAINT `fk_t_order_t_member` FOREIGN KEY (`member_id`) REFERENCES `t_member` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_t_order_t_shop` FOREIGN KEY (`shop_id`) REFERENCES `t_shop` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';

-- ----------------------------
-- Table structure for t_order_details
-- ----------------------------
DROP TABLE IF EXISTS `t_order_details`;
CREATE TABLE `t_order_details` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '订单明细ID',
  `order_id` int(11) DEFAULT NULL COMMENT '订单ID',
  `goods_id` int(11) DEFAULT NULL COMMENT '商品ID',
  `price` decimal(10,2) DEFAULT NULL COMMENT '商品原价',
  `sales_price` decimal(10,2) DEFAULT NULL COMMENT '销售价',
  `counts` bigint(20) DEFAULT NULL COMMENT '商品数量',
  `total_sales_price` decimal(10,2) DEFAULT NULL COMMENT '商品小计总价',
  `shop_id` int(11) DEFAULT NULL COMMENT '店铺ID',
  PRIMARY KEY (`id`),
  KEY `fk_t_order_details_t_goods` (`goods_id`),
  KEY `fk_t_order_details_t_order` (`order_id`),
  KEY `fk_t_order_details_t_shop` (`shop_id`),
  CONSTRAINT `fk_t_order_details_t_goods` FOREIGN KEY (`goods_id`) REFERENCES `t_goods` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_t_order_details_t_order` FOREIGN KEY (`order_id`) REFERENCES `t_order` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_t_order_details_t_shop` FOREIGN KEY (`shop_id`) REFERENCES `t_shop` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单明细表';

-- ----------------------------
-- Table structure for t_shop
-- ----------------------------
DROP TABLE IF EXISTS `t_shop`;
CREATE TABLE `t_shop` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '店铺ID',
  `name` varchar(256) DEFAULT NULL COMMENT '店铺名称',
  `cert` varchar(256) DEFAULT NULL COMMENT '营业执照',
  `contact` varchar(256) DEFAULT NULL COMMENT '联系人',
  `phone` varchar(128) DEFAULT NULL COMMENT '联系电话',
  `password` varchar(128) DEFAULT NULL COMMENT '商家登录密码',
  `openid` varchar(128) DEFAULT NULL COMMENT '微信openID',
  `nick_name` varchar(128) DEFAULT NULL COMMENT '微信商户昵称',
  `avastar` varchar(128) DEFAULT NULL COMMENT '微信头像地址',
  `province` varchar(128) DEFAULT NULL COMMENT '地址：省',
  `city` varchar(128) DEFAULT NULL COMMENT '市',
  `area` varchar(256) DEFAULT NULL COMMENT '区',
  `address` varchar(512) DEFAULT NULL COMMENT '详细地址',
  `lat` decimal(10,7) DEFAULT NULL COMMENT '经度',
  `lng` decimal(10,7) DEFAULT NULL COMMENT '纬度',
  `status` int(11) DEFAULT NULL COMMENT '状态。0表示启用；1表示禁用',
  `cover` int(11) DEFAULT NULL COMMENT '店铺封面',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `creator` varchar(512) DEFAULT NULL COMMENT '创建人',
  `create_id` int(11) DEFAULT NULL COMMENT '创建人ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `total_sales` decimal(10,2) DEFAULT NULL COMMENT '总销售额',
  `total_sales_counts` bigint(20) DEFAULT NULL COMMENT '总销售量',
  `buss_start_time` varchar(20) DEFAULT NULL COMMENT '开始营业时间',
  `buss_end_time` varchar(20) DEFAULT NULL COMMENT '营业截止时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='店铺信息';

-- ----------------------------
-- Table structure for t_shop_category
-- ----------------------------
DROP TABLE IF EXISTS `t_shop_category`;
CREATE TABLE `t_shop_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '店铺类别ID',
  `shop_id` int(11) DEFAULT NULL COMMENT '店铺ID',
  `name` varchar(512) DEFAULT NULL COMMENT '分类名称',
  `status` int(11) DEFAULT NULL COMMENT '分类状态。0表示启用；1表示禁用',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `remark` varchar(512) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `fk_t_shop_category_t_shop` (`shop_id`),
  CONSTRAINT `fk_t_shop_category_t_shop` FOREIGN KEY (`shop_id`) REFERENCES `t_shop` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='店铺类别';

-- ----------------------------
-- Table structure for t_shop_login_log
-- ----------------------------
DROP TABLE IF EXISTS `t_shop_login_log`;
CREATE TABLE `t_shop_login_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '商户登录ID',
  `shop_id` int(11) DEFAULT NULL COMMENT '商户ID',
  `login_times` datetime DEFAULT NULL COMMENT '登录时间',
  `login_ip` varchar(128) DEFAULT NULL COMMENT '登录IP',
  `address` varchar(512) DEFAULT NULL COMMENT '登录地址',
  `devices` varchar(512) DEFAULT NULL COMMENT '登录设备',
  PRIMARY KEY (`id`),
  KEY `fk_t_shop_login_log_t_shop` (`shop_id`),
  CONSTRAINT `fk_t_shop_login_log_t_shop` FOREIGN KEY (`shop_id`) REFERENCES `t_shop` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商户登录记录';

-- ----------------------------
-- Table structure for t_shop_search_log
-- ----------------------------
DROP TABLE IF EXISTS `t_shop_search_log`;
CREATE TABLE `t_shop_search_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '店铺搜索日志ID',
  `shop_id` int(11) DEFAULT NULL COMMENT '店铺ID',
  `keywords` varchar(512) DEFAULT NULL COMMENT '搜索关键字',
  `member_id` int(11) DEFAULT NULL COMMENT '搜索会员ID',
  `create_time` datetime DEFAULT NULL COMMENT '搜索时间',
  `status` int(11) DEFAULT NULL COMMENT '状态。0表示正常；1表示已删除',
  PRIMARY KEY (`id`),
  KEY `fk_t_shop_search_log_t_member` (`member_id`),
  CONSTRAINT `fk_t_shop_search_log_t_member` FOREIGN KEY (`member_id`) REFERENCES `t_member` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='店铺搜索日志表';

-- ----------------------------
-- Table structure for t_sys_login_log
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_login_log`;
CREATE TABLE `t_sys_login_log` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `user_id` int(20) NOT NULL,
  `user_name` varchar(512) NOT NULL,
  `realm_name` varchar(256) DEFAULT NULL,
  `status` int(1) DEFAULT NULL,
  `remark` varchar(512) DEFAULT NULL,
  `login_ip` varchar(128) DEFAULT NULL,
  `login_province` varchar(256) DEFAULT NULL,
  `login_address` varchar(512) DEFAULT NULL,
  `login_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3829 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of t_sys_login_log
-- ----------------------------
BEGIN;
INSERT INTO `t_sys_login_log` VALUES (1, 1, 'test', '陈彪', 0, NULL, '0:0:0:0:0:0:0:1', NULL, NULL, '2017-02-20 21:58:59');
INSERT INTO `t_sys_login_log` VALUES (3825, 1, 'biao.chen', '陈彪', 0, NULL, '0:0:0:0:0:0:0:1', NULL, NULL, '2018-06-03 14:57:06');
INSERT INTO `t_sys_login_log` VALUES (3826, 1, 'biao.chen', '陈彪', 0, NULL, '0:0:0:0:0:0:0:1', NULL, NULL, '2018-06-29 23:26:57');
INSERT INTO `t_sys_login_log` VALUES (3827, 1, 'biao.chen', '陈彪', 0, NULL, '0:0:0:0:0:0:0:1', NULL, NULL, '2018-06-29 23:31:26');
INSERT INTO `t_sys_login_log` VALUES (3828, 1, 'biao.chen', '陈彪', 0, NULL, '0:0:0:0:0:0:0:1', NULL, NULL, '2018-06-29 23:35:06');
COMMIT;

-- ----------------------------
-- Table structure for t_sys_resource
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_resource`;
CREATE TABLE `t_sys_resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '功能ID，功能在数据库的唯一标示。',
  `resource_name` varchar(40) NOT NULL COMMENT '功能名称',
  `resource_url` varchar(255) NOT NULL COMMENT '功能请求路径',
  `is_basic` int(1) NOT NULL DEFAULT '0' COMMENT '是否是基础功能,0表示不是基础功能，1表示是基础功能。',
  `parent_id` int(4) NOT NULL COMMENT '父节点ID',
  `parent_path` varchar(500) DEFAULT NULL COMMENT '所有父级id拼接',
  `level` int(4) DEFAULT NULL COMMENT '当前节点的级别，例如例如根节点，第一级节点，，，，',
  `remark` varchar(20) DEFAULT NULL COMMENT '功能描述',
  `status` int(1) DEFAULT '0' COMMENT '状态0表示正常，1表示不正常',
  `resource_kind` int(1) unsigned zerofill DEFAULT NULL COMMENT '1菜单资源属于商城，2菜单资源属于平台，3菜单资源属于直播 5菜单属于erp 6菜单属于系统',
  `resource_type` int(4) DEFAULT NULL COMMENT '资源类型 0 菜单 1按钮',
  `seq` int(1) DEFAULT NULL COMMENT '排序',
  `open_mode` int(1) DEFAULT NULL COMMENT '打开方式 ajax,iframe',
  `opened` int(1) DEFAULT NULL COMMENT '打开状态',
  `icon` varchar(32) DEFAULT NULL COMMENT '图标',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=812 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='请求资源';

-- ----------------------------
-- Records of t_sys_resource
-- ----------------------------
BEGIN;
INSERT INTO `t_sys_resource` VALUES (6, '系统', '#', 1, -1, '/', 0, NULL, 0, 6, 0, NULL, NULL, NULL, '&#xe614;');
INSERT INTO `t_sys_resource` VALUES (84, '系统管理', '#', 1, 6, '/6/', 1, '', 0, 6, 0, 1, NULL, NULL, '');
INSERT INTO `t_sys_resource` VALUES (85, '系统用户管理', '/system/user', 1, 84, '/6/84/', 2, '', 0, 6, 0, 1, NULL, NULL, '');
INSERT INTO `t_sys_resource` VALUES (86, '系统角色管理', '/system/role', 1, 84, '/6/84/', 2, '', 0, 6, 0, 2, NULL, NULL, '');
INSERT INTO `t_sys_resource` VALUES (87, '系统资源管理', '/system/resource/add', 1, 84, '/6/84/', 2, '', 0, 6, 0, 3, NULL, NULL, '');
INSERT INTO `t_sys_resource` VALUES (245, '添加', '/system/user/add', 1, 85, '/6/84/85/', 3, '', 0, 6, 1, NULL, NULL, NULL, NULL);
INSERT INTO `t_sys_resource` VALUES (246, '删除', '/system/user/delete', 1, 85, '/6/84/85/', 3, '', 0, 6, 1, NULL, NULL, NULL, NULL);
INSERT INTO `t_sys_resource` VALUES (247, '编辑', '/system/user/edit', 1, 85, '/6/84/85/', 3, '', 0, 6, 1, NULL, NULL, NULL, NULL);
INSERT INTO `t_sys_resource` VALUES (248, '添加', '/system/role/add', 1, 86, '/6/84/86/', 3, '', 0, 6, 1, NULL, NULL, NULL, NULL);
INSERT INTO `t_sys_resource` VALUES (249, '编辑', '/system/role/edit', 1, 86, '/6/84/86/', 3, '', 0, 6, 1, NULL, NULL, NULL, NULL);
INSERT INTO `t_sys_resource` VALUES (250, '删除', '/system/role/delete', 1, 86, '/6/84/86/', 3, '', 0, 6, 1, NULL, NULL, NULL, NULL);
INSERT INTO `t_sys_resource` VALUES (251, '添加', '/system/resource/add', 1, 87, '/6/84/87/', 3, '', 0, 6, 1, NULL, NULL, NULL, NULL);
INSERT INTO `t_sys_resource` VALUES (252, '编辑', '/system/resource/edit', 1, 87, '/6/84/87/', 3, '', 0, 6, 1, NULL, NULL, NULL, NULL);
INSERT INTO `t_sys_resource` VALUES (253, '删除', '/system/resource/delete', 1, 87, '/6/84/87/', 3, '', 0, 6, 1, NULL, NULL, NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for t_sys_role
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_role`;
CREATE TABLE `t_sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色组的唯一标示',
  `role_name` varchar(30) NOT NULL COMMENT '角色组名称',
  `description` varchar(80) DEFAULT NULL COMMENT '角色组描述',
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '角色状态,0默认为有效，1表示已被禁用不能使用。',
  `creator` varchar(32) NOT NULL COMMENT '角色组创建人',
  `createtime` bigint(20) NOT NULL COMMENT '创建时间',
  `last_update_creator` varchar(32) DEFAULT NULL COMMENT '此角色组上一次修改人',
  `last_update_createtime` bigint(20) DEFAULT NULL COMMENT '此角色上一次修改时间',
  `parent_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of t_sys_role
-- ----------------------------
BEGIN;
INSERT INTO `t_sys_role` VALUES (4, 'administrator', '超级管理员', 0, 'root', 1487596593093, 'root', 1530938189814, NULL);
INSERT INTO `t_sys_role` VALUES (5, '管理员', '', 0, 'root', 1487596610582, 'root', 1565061345057, NULL);
COMMIT;

-- ----------------------------
-- Table structure for t_sys_sub_manage
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_sub_manage`;
CREATE TABLE `t_sys_sub_manage` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL COMMENT '系统唯一key',
  `value` varchar(255) NOT NULL COMMENT '系统名称',
  `url` varchar(255) DEFAULT NULL COMMENT '系统访问路径',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='子系统管理表';

-- ----------------------------
-- Records of t_sys_sub_manage
-- ----------------------------
BEGIN;
INSERT INTO `t_sys_sub_manage` VALUES (6, 'system', '系统', 'http://localhost:9002/ddkc');
COMMIT;

-- ----------------------------
-- Table structure for t_sys_user
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_user`;
CREATE TABLE `t_sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '登录用户ID',
  `name` varchar(32) NOT NULL COMMENT '用户昵称',
  `email` varchar(128) DEFAULT NULL COMMENT '用户email号。用于保证用户唯一，且用于通过email进行帐号激活。',
  `password` varchar(32) NOT NULL COMMENT '密码',
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '状态',
  `creator` varchar(32) NOT NULL COMMENT '创建者',
  `createtime` bigint(20) NOT NULL COMMENT '创建时间',
  `login_Times` int(8) DEFAULT NULL COMMENT '登录次数',
  `last_Login_Time` bigint(20) DEFAULT NULL COMMENT '上次登录时间',
  `last_Login_Ip` varchar(20) DEFAULT NULL COMMENT '上次登录IP',
  `real_name` varchar(32) DEFAULT NULL COMMENT '用户真实姓名',
  `phone` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_sys_user
-- ----------------------------
BEGIN;
INSERT INTO `t_sys_user` VALUES (1, 'biao.chen', 'promise_adison@163.com', '96e79218965eb72c92a549dd5a330112', 0, 'root', 1487417957302, 508, 1530286505548, '0:0:0:0:0:0:0:1', '陈彪', '18317014696');
INSERT INTO `t_sys_user` VALUES (58, 'admin', 'trip@trip.com', '96e79218965eb72c92a549dd5a330112', 0, 'biao.chen', 1531069002899, 0, NULL, NULL, '管理员', '13800000000');
COMMIT;

-- ----------------------------
-- Table structure for t_trade_statistic
-- ----------------------------
DROP TABLE IF EXISTS `t_trade_statistic`;
CREATE TABLE `t_trade_statistic` (
  `Id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='交易流水统计';

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(512) DEFAULT NULL COMMENT '用户昵称',
  `password` varchar(128) DEFAULT NULL COMMENT '用户密码',
  `phone` varchar(11) DEFAULT NULL COMMENT '手机号',
  `avastar` varchar(512) DEFAULT NULL COMMENT '头像',
  `status` int(2) DEFAULT NULL COMMENT '状态',
  `create_time` datetime DEFAULT NULL COMMENT '注册时间',
  `sex` int(2) DEFAULT NULL COMMENT '性别。1表示男；2表示女',
  `open_id` varchar(128) DEFAULT NULL COMMENT '绑定的微信openid',
  `unionId` varchar(128) DEFAULT NULL COMMENT '绑定的微信唯一标识',
  `account` bigint(20) DEFAULT NULL COMMENT '资产。金币数量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- ----------------------------
-- Records of t_user
-- ----------------------------
BEGIN;
INSERT INTO `t_user` VALUES (7, '木头', NULL, '13700000000', 'http://cdn.benecess.com/photos/2018/07/25/349f7965-a7c7-4261-b3a8-40690b6349f2.jpg', 0, '2018-07-25 22:55:16', 1, 'obVNo1D-fkcbAGJPSVYYhXrUHjOg', NULL, 1);
INSERT INTO `t_user` VALUES (8, '用户96590539', NULL, '13800000000', 'http://cdn.benecess.com/default/default_avastar.png', 0, '2018-07-25 23:50:47', NULL, 'no.66085743937613658994448101372047', NULL, 1);
COMMIT;

-- ----------------------------
-- Table structure for t_withdraw
-- ----------------------------
DROP TABLE IF EXISTS `t_withdraw`;
CREATE TABLE `t_withdraw` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '商户提现表',
  `shop_id` int(11) DEFAULT NULL COMMENT '商户ID',
  `trans_number` varchar(128) DEFAULT NULL COMMENT '交易号',
  `with_draw_price` decimal(10,2) DEFAULT NULL COMMENT '提现金额',
  `charge_price` decimal(10,2) DEFAULT NULL COMMENT '手续费',
  `status` int(11) DEFAULT NULL COMMENT '提现状态。0表示提现中；1表示提现成功；2表示提现失败。',
  `fail_msg` varchar(512) DEFAULT NULL COMMENT '失败原因',
  `openid` varchar(512) DEFAULT NULL COMMENT '提现到账用户openID',
  `wechat_charge_price` decimal(10,2) DEFAULT NULL COMMENT '微信转账手续费',
  `wechat_trans_number` varchar(512) DEFAULT NULL COMMENT '微信交易流水号',
  `create_time` datetime DEFAULT NULL COMMENT '提现时间',
  `success_time` datetime DEFAULT NULL COMMENT '提现成功时间',
  PRIMARY KEY (`id`),
  KEY `fk_t_withdraw_t_shop` (`shop_id`),
  CONSTRAINT `fk_t_withdraw_t_shop` FOREIGN KEY (`shop_id`) REFERENCES `t_shop` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商户提现表';

SET FOREIGN_KEY_CHECKS = 1;