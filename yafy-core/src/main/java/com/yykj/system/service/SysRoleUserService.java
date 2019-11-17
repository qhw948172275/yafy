package com.yykj.system.service;

import com.yykj.system.commons.service.impl.AbstractBaseCrudService;
import com.yykj.system.entity.SysRoleUser;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class SysRoleUserService extends AbstractBaseCrudService<SysRoleUser,Integer> {
    /**
     * 根据用户IdH查询角色信息
     * @param userId
     * @return
     */
    public List<SysRoleUser> selectByUserId(Integer userId) {
        Example example=new Example(tClass);
        Example.Criteria criteria=example.createCriteria();
        criteria.andEqualTo("uid",userId);
        return mapper.selectByExample(example);
    }
}
