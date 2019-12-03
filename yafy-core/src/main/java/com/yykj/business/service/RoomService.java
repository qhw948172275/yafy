package com.yykj.business.service;

import com.yykj.business.dao.RoomMapper;
import com.yykj.business.entity.Room;
import com.yykj.business.response.RoomAreaRepsonse;
import com.yykj.system.commons.StringUtils;
import com.yykj.system.commons.SystemConstants;
import com.yykj.system.commons.service.impl.AbstractBaseCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Author qhw
 * @Date 2019/11/20 0020 下午 16:44
 * @Version V1.0
 **/
@Service
public class RoomService extends AbstractBaseCrudService<Room,Integer> {
    @Autowired
    RoomMapper roomMapper;
    /**
     * description:房间列表
     * create by: qhw
     * create time: 2019/11/20 0020 下午 17:38
     */
    public List<RoomAreaRepsonse> selectRoomListByUserId(Integer userId,String areaName,String unitName,Integer areaId) {
//        Example example=new Example(tClass);
//        Example.Criteria criteria=example.createCriteria();
//        criteria.andEqualTo("status",0).andEqualTo("creatorId",userId);
//        example.setOrderByClause("id desc");
//        return mapper.selectByExample(example);
        areaName= StringUtils.isEmpty(areaName)?null:new StringBuilder(SystemConstants.PEAC_SG).append(areaName).append(SystemConstants.PEAC_SG).toString();
        unitName= StringUtils.isEmpty(unitName)?null:new StringBuilder(SystemConstants.PEAC_SG).append(unitName).append(SystemConstants.PEAC_SG).toString();

        return roomMapper.selectRoomListByUserId(userId,areaName,unitName,areaId);
    }
}
