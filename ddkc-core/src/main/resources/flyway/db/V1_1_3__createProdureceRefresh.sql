DROP PROCEDURE IF EXISTS refresh_balance;
delimiter $$
CREATE PROCEDURE refresh_balance(in dateStr VARCHAR(200))
BEGIN
DECLARE row_sumPrice DOUBLE;

declare row_shop_id int ;
declare done int default 0;
DECLARE
	cur_test CURSOR FOR
	SELECT
		too.shop_id ,sum(too.shop_balance) sumPrice  from t_order too where DATE_FORMAT(too.take_over_time,'%Y-%m-%d')=dateStr
		group by too.shop_id;

  declare continue HANDLER for not found set done = 1;

OPEN cur_test;

FETCH cur_test INTO row_shop_id,row_sumPrice;

 while done<>1 do
   UPDATE t_shop ts set ts.total_amount=ts.total_amount+row_sumPrice where ts.id=row_shop_id;
	select row_sumPrice;

	fetch cur_test into row_shop_id,row_sumPrice;
end while;

CLOSE cur_test;

END $$
delimiter ;