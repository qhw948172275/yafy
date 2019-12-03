package com.yykj.business.dao;

import com.yykj.business.entity.Room;
import com.yykj.business.response.RoomAreaRepsonse;
import com.yykj.system.commons.service.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoomMapper extends MyMapper<Room> {
    /**
     * description:查询小房价列表
     * create by: qhw
     * create time: 2019/12/3 0003 下午 16:46
     */
    List<RoomAreaRepsonse> selectRoomListByUserId(@Param("userId") Integer userId, @Param("areaName") String areaName,
                                                  @Param("unitName") String unitName,@Param("areaId") Integer areaId);
}