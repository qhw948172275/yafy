ALTER TABLE `yafy`.`t_renant`
ADD COLUMN `creator_id` int(11) NULL COMMENT '创建人ID' AFTER `status`,
ADD COLUMN `create_time` datetime(0) NULL COMMENT '创建时间' AFTER `creator_id`;