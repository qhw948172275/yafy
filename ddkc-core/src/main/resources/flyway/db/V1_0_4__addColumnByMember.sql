ALTER TABLE `ddkc`.`t_member`
ADD COLUMN `open_id` varchar(255) NULL COMMENT '用户唯一标识' AFTER `id`;