package com.yykj.ddkc.dao;

import com.yykj.ddkc.entity.ShopLoginLog;
import com.yykj.ddkc.response.ShopLoginLogResponse;
import com.yykj.system.commons.service.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShopLoginLogMapper extends MyMapper<ShopLoginLog> {
    /**
     * 查询商家登录日志
     * @param keyword
     * @return
     */
    List<ShopLoginLogResponse> selectAllPage(@Param("keyword") String keyword);
}