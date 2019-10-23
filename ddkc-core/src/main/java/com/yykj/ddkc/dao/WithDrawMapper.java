package com.yykj.ddkc.dao;

import com.yykj.ddkc.entity.WithDraw;
import com.yykj.ddkc.response.PlatformWithDrawResponse;
import com.yykj.ddkc.response.ShopWithDrawResponse;
import com.yykj.ddkc.response.WithDrawResponse;
import com.yykj.system.commons.service.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WithDrawMapper extends MyMapper<WithDraw> {
    /**
     * 根据时间和商家ID查询提现记录
     * @param cashOutDate
     * @param shopId
     * @return
     */
    List<WithDraw> selectByDate(@Param("cashOutDate") String cashOutDate,@Param("shopId") Integer shopId);

    /**
     * 根据时间查询商家提现
     * @return
     */
    List<WithDrawResponse> selectAllByDateStr(@Param("startTime")String startTime, @Param("endTime")String endTime
                                                ,@Param("createId")Integer createId);

    /**
     * 根据时间查询商家提现详情
     * @param startTime
     * @param endTime
     * @param shopName
     * @param shopId
     * @return
     */
    List<ShopWithDrawResponse> selectShopWithDraw(@Param("startTime") String startTime,@Param("endTime") String endTime,
                                                 @Param("shopName") String shopName,@Param("shopId") Integer shopId);

    /**
     * 查询指定一段时间的平台提现统计
     * @param startTime
     * @param endTime
     * @return
     */
    List<PlatformWithDrawResponse> selectPlatformWithDrawResponse(@Param("startTime") String startTime,@Param("endTime") String endTime
                                                                    ,@Param("createId")Integer createId);
}