DROP TABLE IF EXISTS `t_delivery`;
CREATE TABLE `t_delivery` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '配送设置ID',
  `type` int(2) DEFAULT NULL COMMENT '配送类型。0表示商家配送费每单多少元；1表示平台抽成每单多少元；2表示配送范围周围多少米；',
  `val` varchar(10) DEFAULT NULL COMMENT '配送类型对应值',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='配送设置表';