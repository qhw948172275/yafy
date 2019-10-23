ALTER TABLE `ddkc`.`t_shop_account` 
ADD COLUMN `open_id` varchar(512) NULL COMMENT 'openID，用于提现' AFTER `name`;