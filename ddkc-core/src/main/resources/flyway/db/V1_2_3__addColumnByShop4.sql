ALTER TABLE `ddkc`.`t_shop_account` 
ADD COLUMN `name` varchar(256) NULL COMMENT '名称' AFTER `creator_id`;