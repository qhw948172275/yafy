CREATE TABLE `ddkc`.`t_search_record`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `shop_id` int(11) NULL COMMENT '店铺ID',
  `content` varchar(255) NULL COMMENT '搜索内容',
  `status` int(1) NULL COMMENT '状态',
  PRIMARY KEY (`id`)
);