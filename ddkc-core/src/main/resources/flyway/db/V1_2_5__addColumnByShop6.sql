ALTER TABLE `ddkc`.`t_order` 
ADD COLUMN `prepay_id` varchar(512) NULL COMMENT '预支付ID' AFTER `refund_time`;