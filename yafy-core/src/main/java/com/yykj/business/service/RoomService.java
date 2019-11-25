package com.yykj.business.service;

import com.yykj.business.entity.Room;
import com.yykj.system.commons.service.impl.AbstractBaseCrudService;
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
    /**
     * description:房间列表
     * create by: qhw
     * create time: 2019/11/20 0020 下午 17:38
     */
    public List<Room> selectRoomListByUserId(Integer userId) {
        Example example=new Example(tClass);
        Example.Criteria criteria=example.createCriteria();
        criteria.andEqualTo("status",0).andEqualTo("creator_id",userId);
        example.setOrderByClause("id desc");
        return mapper.selectByExample(example);
    }
}
