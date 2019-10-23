ALTER TABLE `ddkc`.`t_shop`
ADD COLUMN `estate` varchar(255) NULL COMMENT '所在小区' AFTER `annex_address`;