ALTER TABLE `ddkc`.`t_shop`
MODIFY COLUMN `cert` varchar(256) NULL  COMMENT '营业执照编号' AFTER `name`,
ADD COLUMN `annex_address` text NULL COMMENT '上传附件地址' AFTER `buss_end_time`;