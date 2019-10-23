package com.yykj.ddkc.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yykj.ddkc.dao.WithDrawMapper;
import com.yykj.ddkc.entity.WithDraw;
import com.yykj.ddkc.response.PlatformWithDrawResponse;
import com.yykj.ddkc.response.ShopWithDrawResponse;
import com.yykj.ddkc.response.WithDrawResponse;
import com.yykj.system.commons.StringUtils;
import com.yykj.system.commons.service.impl.AbstractBaseCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class WithDrawService extends AbstractBaseCrudService<WithDraw,Integer> {
    @Autowired
    WithDrawMapper withDrawMapper;
    /**
     * 根据时间和商家ID查询提现记录
     * @param cashOutDate
     * @param shopId
     * @return
     */
    public List<WithDraw> selectByDate(String cashOutDate, Integer shopId) {
        cashOutDate= StringUtils.isNotEmpty(cashOutDate)?cashOutDate:null;
      return   withDrawMapper.selectByDate(cashOutDate,shopId);
    }

    /**
     * 根据时间查询商家提现
     * @param map
     * @return
     */
    public PageInfo<WithDrawResponse> selectAll(Map<String,Object> map){
        PageHelper.startPage(Integer.valueOf(String.valueOf(map.get("pageNumber"))), Integer.valueOf(String.valueOf(map.get("pageSize"))));
        String startTime=map.containsKey("startTime")?(String)map.get("startTime"):null;
        String endTime=map.containsKey("endTime")?(String)map.get("endTime"):null;
        Integer createId=map.containsKey("createId")?(Integer) map.get("createId"):null;
        List<WithDrawResponse> withDraws=withDrawMapper.selectAllByDateStr(startTime,endTime,createId);
        return new PageInfo<>(withDraws);
    }
    /**
     * 根据时间查询商家提现详情
     * @param map
     * @return
     */
    public PageInfo<ShopWithDrawResponse> selectShopWithDraw(Map<String,Object> map){
        PageHelper.startPage(Integer.valueOf(String.valueOf(map.get("pageNumber"))), Integer.valueOf(String.valueOf(map.get("pageSize"))));
        String startTime=map.containsKey("startTime")?(String)map.get("startTime"):null;
        String endTime=map.containsKey("endTime")?(String)map.get("endTime"):null;
        String shopName=map.containsKey("shopName")?"%"+map.get("shopName")+"%":null;
        Integer shopId=map.containsKey("shopId")?(Integer)map.get("shopId"):null;
        List<ShopWithDrawResponse> withDraws=withDrawMapper.selectShopWithDraw(startTime,endTime,shopName,shopId);
        return new PageInfo<>(withDraws);
    }

    /**
     * 查询指定一段时间的平台提现统计
     * @param startTime
     * @param endTime
     * @return
     */
    public List<PlatformWithDrawResponse> selectPlatformWithDrawResponse(String startTime, String endTime,Map<String,Object> map) {
        Integer createId=map.containsKey("createId")?(Integer) map.get("createId"):null;
        return withDrawMapper.selectPlatformWithDrawResponse(startTime,endTime,createId);
    }
}
