package com.yykj.business.dao;

import com.yykj.business.entity.Area;
import com.yykj.business.response.AreaResonse;
import com.yykj.system.commons.service.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AreaMapper extends MyMapper<Area> {

    List<AreaResonse> selectAreaListByUserId(@Param("userId") Integer userId,@Param("areaName") String areaName,
                                            @Param("bigLandlordId") Integer bigLandlordId,@Param("unitName") String unitName);
}