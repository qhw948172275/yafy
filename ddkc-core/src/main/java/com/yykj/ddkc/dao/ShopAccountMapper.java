package com.yykj.ddkc.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.yykj.ddkc.entity.ShopAccount;
import com.yykj.system.commons.service.MyMapper;

public interface ShopAccountMapper extends MyMapper<ShopAccount> {
	
	@Select(value = "select tsa.* from t_shop_account tsa where tsa.status = 0 and tsa.is_notice = 1 and tsa.shop_id = #{shopId} ")
	List<ShopAccount> selectByShopId(@Param("shopId") Integer shopId);
	
}