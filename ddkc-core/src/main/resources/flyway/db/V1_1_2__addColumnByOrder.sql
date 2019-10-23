ALTER TABLE `ddkc`.`t_order`
ADD COLUMN `freight` decimal(10, 2) NULL COMMENT '配送费' AFTER `cannel_time`,
ADD COLUMN `platform_cost` decimal(10, 2) NULL COMMENT '平台费用' AFTER `freight`,
ADD COLUMN `total_goods_price` decimal(10, 2) NULL COMMENT '商品总价' AFTER `platform_cost`,
ADD COLUMN `shop_balance` decimal(10, 2) NULL COMMENT '商家余额' AFTER `total_goods_price`;