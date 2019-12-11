CREATE TABLE `yafy`.`r_rent_manage_room`  (
  `id` int(11) NOT NULL,
  `room_id` int(11) NULL COMMENT '小房间ID',
  `rent_manage_id` int(11) NULL COMMENT '租金管理ID',
  PRIMARY KEY (`id`)
);