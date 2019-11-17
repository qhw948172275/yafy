package com.yykj.system.service;

import com.yykj.system.commons.service.impl.AbstractBaseCrudService;
import com.yykj.system.dao.SysResourceMapper;
import com.yykj.system.entity.SysResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysResourceService extends AbstractBaseCrudService<SysResource,Integer> {
      @Autowired
    SysResourceMapper sysResourceMapper;

    /**
     * 根据用户ID查询权限菜单
     * @param userId
     * @return
     */
    public List<SysResource> selectByUserId(Integer userId) {
        return sysResourceMapper.selectByUserId(userId);
    }

    /**
     * 软删除资源信息
     * @param resourceId
     */
    public void deleteByResourceId(Integer resourceId) {
        SysResource sysResource=new SysResource();
        sysResource.setId(resourceId);
        sysResource.setStatus(1);
        this.mapper.updateByPrimaryKeySelective(sysResource);
    }
}
