SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_goods_search_record
-- ----------------------------
DROP TABLE IF EXISTS `t_goods_search_record`;
CREATE TABLE `t_goods_search_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `shop_id` int(11) DEFAULT NULL COMMENT '店铺ID',
  `content` varchar(255) DEFAULT NULL COMMENT '搜索内容',
  `status` int(2) DEFAULT NULL COMMENT '状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

SET FOREIGN_KEY_CHECKS = 1;

ALTER TABLE `ddkc`.`t_order`
ADD COLUMN `receipt_time` datetime(0) NULL COMMENT '商家接单时间' AFTER `contact_address`,
ADD COLUMN `take_over_time` datetime(0) NULL COMMENT '收货时间' AFTER `receipt_time`,
ADD COLUMN `discount_price` decimal(10,2) NULL COMMENT '优惠价格' AFTER `take_over_time`;
ALTER TABLE `ddkc`.`t_order`
CHANGE COLUMN `order_id` `order_number` varchar(128) NULL DEFAULT NULL COMMENT '订单号' AFTER `id`;