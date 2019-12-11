package com.yykj.business.dao;

import com.yykj.business.entity.RentManage;
import com.yykj.business.response.RentManageAreaResponse;
import com.yykj.business.response.RentManageRoomResponse;
import com.yykj.system.commons.service.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RentManageMapper extends MyMapper<RentManage> {
    /**
     * description:
     * create by: qhw
     * create time: 2019/11/17 0017 下午 20:56
     */
    List<RentManage> selectRentManage(@Param("areaId") Integer areaId);

    List<RentManageRoomResponse> selectRentManageService(@Param("userId") Integer userId);

    RentManageRoomResponse selectRentManageRoomResponse(@Param("rentManageId") Integer rentManageId,
                                                        @Param("roomId") Integer roomId);
    /**
     * description:查询套房租金信息
     * create by: qhw
     * create time: 2019/12/11 0011 下午 16:24
     */
    List<RentManageAreaResponse> selectRentRentManageAreaResponse(@Param("userId") Integer userId);

    RentManageAreaResponse selectOneRentManageAreaResponse(@Param("areaId") Integer areaId,
                                                           @Param("rentManageId") Integer rentManageId);
}