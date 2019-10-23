CREATE TABLE `ddkc`.`t_operate_log`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(255) NULL COMMENT '账号',
  `real_name` varchar(255) NULL COMMENT '姓名真实',
  `shop_id` int(11) NULL COMMENT '商家ID',
  `operate_time` datetime(0) NULL COMMENT '操作时间',
  `content` varchar(255) NULL COMMENT '操作内容',
  PRIMARY KEY (`id`)
);