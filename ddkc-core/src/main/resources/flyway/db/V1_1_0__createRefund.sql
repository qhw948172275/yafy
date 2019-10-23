DROP TABLE IF EXISTS `t_refund`;
CREATE TABLE `t_refund` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `numbers` varchar(200) DEFAULT NULL COMMENT '退款序列号',
  `order_id` int(20) DEFAULT NULL,
  `type` tinyint(3) DEFAULT NULL COMMENT '类型 0:未发货退款 1:已发货退款',
  `remark` text COMMENT '描述',
  `amount` double(10,2) DEFAULT NULL COMMENT '退款金额',
  `refund_time` bigint(20) DEFAULT NULL COMMENT '退款时间',
  `status` tinyint(3) DEFAULT NULL COMMENT '状态 0:待退款 1:退款成功 2:退款失败',
  `fail_reason` varchar(500) DEFAULT NULL COMMENT '失败原因',
  `create_time` bigint(20) DEFAULT NULL,
  `sys_user_id` int(11) DEFAULT NULL COMMENT '操作员id',
  `fail_code` varchar(100) DEFAULT NULL COMMENT '失败code',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;