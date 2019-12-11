package com.yykj.business.service;

import com.yykj.business.dao.RenantMapper;
import com.yykj.business.dao.RoomMapper;
import com.yykj.business.dao.RoomRenantMapper;
import com.yykj.business.dto.RenantDto;
import com.yykj.business.dto.RenantpDto;
import com.yykj.business.entity.Renant;
import com.yykj.business.entity.Room;
import com.yykj.business.entity.RoomRenant;
import com.yykj.system.commons.service.impl.AbstractBaseCrudService;
import com.yykj.system.entity.SysUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Author qhw
 * @Date 2019/11/20 0020 下午 15:17
 * @Version V1.0
 **/
@Service
public class RenantService extends AbstractBaseCrudService<Renant,Integer> {
    @Autowired
    RenantMapper renantMapper;
    @Autowired
    RoomMapper roomMapper;
    @Autowired
    RoomRenantMapper roomRenantMapper;

    /**
     * description:添加普通租客信息
     * create by: qhw
     * create time: 2019/11/20 0020 下午 16:09
     */
    @Transactional
    public void save(RenantDto renantDto, SysUser sysUer) {
        Renant renant=new Renant(renantDto.getName(),renantDto.getPhone(),renantDto.getIdCard());
        this.mapper.insert(renant);
        Room room=new Room(renantDto.getAreaId(),sysUer.getId(),renantDto.getKeyPath(),(byte)1,1,renantDto.getType());
        roomMapper.insert(room);
    }
    /**
     * description:查询该用户下的所有租客
     * create by: qhw
     * create time: 2019/11/25 0025 下午 22:12
     */
    public List<Renant> selectAllListByUserId(Integer userId) {
        Example example=new Example(tClass);
        Example.Criteria criteria=example.createCriteria();
        criteria.andEqualTo("creatorId",userId).andEqualTo("status",0);
        example.setOrderByClause("id desc");
        return mapper.selectByExample(example);
    }

    @Transactional
    public void save(RenantpDto renant) {
        Renant renant1=new Renant();
        BeanUtils.copyProperties(renant,renant1);
        mapper.insert(renant1);
        RoomRenant roomRenant=new RoomRenant();
        roomRenant.setRenantId(renant1.getId());
        roomRenant.setRoomId(renant.getRoomId());
        roomRenantMapper.insert(roomRenant);
        Room room=roomMapper.selectByPrimaryKey(renant.getRoomId());
        room.setTenantNember(room.getTenantNember()+1);
        roomMapper.updateByPrimaryKeySelective(room);
    }
}
