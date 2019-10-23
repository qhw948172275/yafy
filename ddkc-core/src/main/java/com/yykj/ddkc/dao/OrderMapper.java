package com.yykj.ddkc.dao;

import com.yykj.ddkc.entity.Order;
import com.yykj.ddkc.response.*;
import com.yykj.system.commons.service.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderMapper extends MyMapper<Order> {
    /**
     * 查询订单流程
     * @param orderId
     * @return
     */
    OrderProcessResponse selectOrderProcessResponseByOrderId(@Param("orderId") Integer orderId);

    /**
     * 查询订单详情
     * @param orderId
     * @return
     */
    OrderDetailResponse selectOrderDetailResponseByOrderId(@Param("orderId") Integer orderId);

    /**
     * 根据日期查询订单并统计订单数量和收入
     * @param orderLogDate
     * @return
     */
    OrderLogResponse selectByDate(@Param("orderLogDate") String orderLogDate,@Param("shopId")Integer shopId);

    /**
     * 根据订单日期查询订单上的全部商品
     * @param orderLogDate
     * @param shopId
     * @return
     */
    List<OrderGoodsResponse> selectGoodsByDateOrder(@Param("orderLogDate") String orderLogDate,@Param("shopId") Integer shopId);

    /**
     * 后台订单主页面搜索
     * @param shopName
     * @param shopId
     * @param startTime
     * @param endTime
     * @param status
     * @return
     */
    List<Order> selectOrderForPlatform(@Param("shopName") String shopName,@Param("shopId") Integer shopId,
                                       @Param("startTime") String startTime,@Param("endTime") String endTime,
                                       @Param("status") Integer status,@Param("createId")Integer createId);

    /**
     * 根据日期统计订单量和营业额
     * @param startTime
     * @param endTime
     * @return
     */
    List<OrderCountResponse> selectOrderMapperByDate(@Param("startTime")String startTime,@Param("endTime") String endTime
                                                    ,@Param("createId")Integer createId);

    /**
     * 商家订单流水统计
     * @param startTime
     * @param endTime
     * @return
     */
    List<ShopOrderCountResponse>  selectShopOrderCountResponseByDate(@Param("startTime")String startTime,@Param("endTime") String endTime
                                                                    ,@Param("createId")Integer createId);

    /**
     * 查询指定月的流水统计
     * @param newTime
     * @return
     */
    List<PlatformCostCensus> selectPlatformCostCensus(@Param("newTime") String newTime,@Param("createId")Integer createId);

    /**
     * 查询上月订单统计
     * @param upMonth
     * @return
     */
    OrderPieResponse selectOrderPieResponse(@Param("upMonth") String upMonth,@Param("createId")Integer createId);

    /**
     * 查询商家的订单
     * @param shopId
     * @return
     */
    List<ShopOrderResponse> selectShopOrderResponse(@Param("shopId") Integer shopId,@Param("status")Integer status,@Param("merId")Integer merId);
}