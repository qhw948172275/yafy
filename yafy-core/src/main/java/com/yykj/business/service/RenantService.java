package com.yykj.business.service;

import com.yykj.business.dao.RenantMapper;
import com.yykj.business.dao.RoomMapper;
import com.yykj.business.dto.RenantDto;
import com.yykj.business.entity.Renant;
import com.yykj.business.entity.Room;
import com.yykj.system.commons.service.impl.AbstractBaseCrudService;
import com.yykj.system.entity.SysUser;
import io.swagger.annotations.ApiModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
