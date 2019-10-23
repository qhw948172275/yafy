ALTER TABLE `ddkc`.`t_order` 
ADD COLUMN `cannel_time` datetime NULL COMMENT '取消时间' AFTER `discount_price`;