package com.yykj.ddkc.dao;

import com.yykj.ddkc.entity.Goods;
import com.yykj.ddkc.response.GoodsResponse;
import com.yykj.system.commons.service.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface GoodsMapper extends MyMapper<Goods> {
    /**
     * 查询所有商品
     * @param goodsName
     * @param shopName
     * @param shopId
     * @return
     */
    List<GoodsResponse> selectAllPage(@Param("goodsName") String goodsName, @Param("shopName") String shopName
            , @Param("shopId") Integer shopId,@Param("goodsId")Integer goodsId,@Param("createId")Integer createId);
    
    /**
     * 减去库存 
    * @author chenbiao
    * @date 2019年8月16日 下午5:38:07
    * 参数说明 
    * 
    * @param id
    * @param count
    * @return
     */
    @Update(value = "update t_goods set stock = stock - #{count} where id = #{id}")
    Integer updateStockBySubtract(@Param("id")Integer id,@Param("count")Integer count);
    
    /**
     * 增加库存
    * @author chenbiao
    * @date 2019年8月16日 下午5:38:37
    * 参数说明 
    * 
    * @param id
    * @param count
    * @return
     */
    @Update(value = "update t_goods set stock = stock + #{count} where id = #{id}")
    Integer updateStockByAdd(@Param("id")Integer id,@Param("count")Integer count);

    /**
     * 根据商品ID更新商品销售额
     * @param goodsId
     * @param counts
     */
    void updateSalesCount(@Param("goodsId") Integer goodsId,@Param("counts") Long counts);
}