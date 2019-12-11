package com.yykj.business.service;

import com.yykj.business.dao.RentAreaMapper;
import com.yykj.business.dao.RentManageMapper;
import com.yykj.business.dao.RentManageRoomMapper;
import com.yykj.business.dto.RentManageAreaDto;
import com.yykj.business.dto.RentManageRoomDto;
import com.yykj.business.entity.RentArea;
import com.yykj.business.entity.RentManage;
import com.yykj.business.entity.RentManageRoom;
import com.yykj.business.response.RentManageAreaResponse;
import com.yykj.business.response.RentManageRoomResponse;
import com.yykj.system.commons.service.impl.AbstractBaseCrudService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName RentManageService
 * @Description: TODO
 * @Author qhw
 * @Date 2019/11/17 0017 下午 20:54
 * @Version V1.0
 **/
@Service
public class RentManageService extends AbstractBaseCrudService<RentManage,Integer> {
    @Autowired
    RentManageMapper rentManageMapper;
    @Autowired
    RentManageRoomMapper rentManageRoomMapper;
    @Autowired
    RentAreaMapper rentAreaMapper;
    /**
     * description:
     * create by: qhw
     * create time: 2019/11/17 0017 下午 20:55
     */
    public List<RentManage> selectRentManage(Integer areaId) {
      return   rentManageMapper.selectRentManage(areaId);
    }

    public List<RentManageRoomResponse> selectRentManageService(Integer userId) {
        return rentManageMapper.selectRentManageService(userId);
    }
    /**
     * description:小房间合同保存
     * create by: qhw
     * create time: 2019/12/11 0011 下午 15:31
     */
    @Transactional
    public void save(RentManageRoomDto rentManageRoomDto) {
        RentManage rentManage=new RentManage();
        BeanUtils.copyProperties(rentManageRoomDto,rentManage);
        if(rentManage.getId()==null){
            mapper.insert(rentManage);
            RentManageRoom rentManageRoom=new RentManageRoom();
            rentManageRoom.setRentManageId(rentManage.getId());
            rentManageRoom.setRoomId(rentManageRoomDto.getRoomId());
            rentManageRoomMapper.insert(rentManageRoom);
        }else{
            mapper.updateByPrimaryKeySelective(rentManage);
        }

    }

    /**
     * description:获取小房间单个缴费信息
     * create by: qhw
     * create time: 2019/12/11 0011 下午 15:58
     */
    public RentManageRoomResponse selectRentManageRoomResponse(Integer rentManageId, Integer roomId) {
        return rentManageMapper.selectRentManageRoomResponse(rentManageId,roomId);
    }

    public List<RentManageAreaResponse> selectRentRentManageAreaResponse(Integer userId) {
        return rentManageMapper.selectRentRentManageAreaResponse(userId);
    }
    /**
     * description:套房缴费信息
     * create by: qhw
     * create time: 2019/12/11 0011 下午 16:51
     */
    @Transactional
    public void saveArea(RentManageAreaDto rentManage) {
        RentManage rentManage1=new RentManage();
        BeanUtils.copyProperties(rentManage,rentManage1);
        if(rentManage1.getId()==null){
            mapper.insert(rentManage1);
            RentArea rentArea=new RentArea();
            rentArea.setAreaId(rentManage.getAreaId());
            rentArea.setRentManageId(rentManage1.getId());
            rentAreaMapper.insert(rentArea);
        }else{
            mapper.updateByPrimaryKeySelective(rentManage1) ;
        }
    }

    public RentManageAreaResponse selectOneRentManageAreaResponse(Integer areaId, Integer rentManageId) {
        return rentManageMapper.selectOneRentManageAreaResponse(areaId,rentManageId);
    }
}
