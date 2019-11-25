package com.yykj.business.dao;

import com.yykj.business.entity.RentManage;
import com.yykj.system.commons.service.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RentManageMapper extends MyMapper<RentManage> {
    /**
     * description: TODO 根据套房ID查询缴费记录
     * create by: qhw
     * create time: 2019/11/17 0017 下午 20:56
     */
    List<RentManage> selectRentManage(@Param("areaId") Integer areaId);
}