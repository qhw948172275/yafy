package com.yykj.business.dao;

import com.yykj.business.entity.BigLandlord;
import com.yykj.business.response.BigLandlordDetailResponse;
import com.yykj.business.response.BigLandlordResponse;
import com.yykj.system.commons.service.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BigLandlordMapper extends MyMapper<BigLandlord> {
    /**
     * description: TODO 查询大房东信息列表
     * create by: qhw
     * create time: 2019/11/17 0017 下午 20:06
     */
    List<BigLandlordResponse> selectBigLandlordDto(@Param("userId") Integer userId, @Param("status")Integer status);
    /**
     * description: TODO 大房东信息详情
     * create by: qhw
     * create time: 2019/11/17 0017 下午 21:08
     */
    BigLandlordDetailResponse selectBigLandlordDtoByAreaId(@Param("areaId") Integer areaId);
}