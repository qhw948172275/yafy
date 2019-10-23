ALTER TABLE `ddkc`.`t_goods`
MODIFY COLUMN `status` int(2) NULL DEFAULT NULL COMMENT '状态。0表示上架；1表示下架,2表示删除' AFTER `total_sales_price`;