package com.yykj.system.service;

import com.yykj.system.dto.RoleResourceDto;
import com.yykj.system.commons.RandomUtils;
import com.yykj.system.commons.StringUtils;
import com.yykj.system.commons.SystemConstants;
import com.yykj.system.commons.service.impl.AbstractBaseCrudService;
import com.yykj.system.dao.SysRoleMapper;
import com.yykj.system.dao.SysRoleResourceMapper;
import com.yykj.system.entity.SysRole;
import com.yykj.system.entity.SysRoleResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

@Service
public class SysRoleService extends AbstractBaseCrudService<SysRole, Integer> {
    @Autowired
    private SysRoleResourceMapper systemRoleResourceMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;


    public List<SysRole> getAllRole(Integer schoolId, String sreach, Integer status, List<String> roleCodes) {
        Example example = new Example(tClass);
        Example.Criteria criteria = example.createCriteria();
        if (schoolId != null && schoolId != 0) {
            criteria.andEqualTo("schoolId", schoolId);
        }
        if (StringUtils.isNotEmpty(sreach)) {
            criteria.andLike("roleName", "%" + sreach + "%");
        }
        if (status != null) {
            criteria.andEqualTo("status", status);
        }
        if (roleCodes != null) {
            criteria.andIn("roleCode", roleCodes);
        }
        return mapper.selectByExample(example);
    }

    public SysRole getByCode(String code) {
        Example example = new Example(tClass);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("roleCode", code);
        return mapper.selectByExample(example).get(0);
    }

    /**
     * 生成角色代码
     *
     * @return
     */
    public String createRoleCode(String param) {
        String roleCode = RandomUtils.getAlphaCode(param);
        Example example = new Example(tClass);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("roleCode", roleCode);
        List<SysRole> systemRoles = mapper.selectByExample(example);
        if (systemRoles.size() > 0) {
            createRoleCode(param);
        }
        return roleCode;
    }


    /**
     * @param roleId
     * @param resourceIds
     * @param type        0:插入，1：更新
     */
    public void updateOrInsertSystemRoleResource(Integer roleId, List<Integer> resourceIds, int type) {
        if (type == 0) {
            insertSystemRoleResourceList(roleId, resourceIds);
        } else if (type == 1) {
            if (deleteSystemRoleResourceByRoleId(roleId) >= 0) {
                insertSystemRoleResourceList(roleId, resourceIds);
            }
        }
    }

    /**
     * 删除指定角色ID的角色资源关系对应表
     *
     * @param roleId
     * @return
     */
    public int deleteSystemRoleResourceByRoleId(Integer roleId) {
        Example example = new Example(SysRoleResource.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("roleId", roleId);
        return systemRoleResourceMapper.deleteByExample(example);
    }

    /**
     * 批量插入角色资源关系表
     *
     * @param roleId
     * @param resourceIds
     */
    public void insertSystemRoleResourceList(Integer roleId, List<Integer> resourceIds) {
        List<SysRoleResource> systemRoleResources = new ArrayList<>();
        SysRoleResource systemRoleResource;
        for (Integer resourceId : resourceIds) {
            systemRoleResource = new SysRoleResource();
            systemRoleResource.setResourceId(resourceId);
            systemRoleResource.setRoleId(roleId);
            systemRoleResources.add(systemRoleResource);
        }
        systemRoleResourceMapper.insertList(systemRoleResources);
    }

    /**
     * 查询当前角色拥有的资源ID
     *
     * @param roleId
     * @return
     */
    public List<SysRoleResource> selectRoleResourceIds(Integer roleId) {
        Example example = new Example(SysRoleResource.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("roleId", roleId);
        return systemRoleResourceMapper.selectByExample(example);
    }

    /**
     * 根据角色代码查询角色列表
     *
     * @param roleCodes
     * @return
     */
    public List<SysRole> selectByRoleCode(List<String> roleCodes) {
        Example example = new Example(tClass);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("roleCode", roleCodes);
        return mapper.selectByExample(example);
    }

    /**
     * 根据当前用户ID获取对应角色信息
     *
     * @return
     */
    public List<SysRole> selectByUserId(Integer userId, Integer status) {
        return sysRoleMapper.selectByUserId(userId, status);
    }

    /**
     * 角色保存
     *
     * @param roleResourceDto
     */
    @Transactional()
    public void save(RoleResourceDto roleResourceDto) throws Exception {
        if (this.checkRoleName(roleResourceDto.getRoleName(), roleResourceDto.getSchoolId(), roleResourceDto.getId())) {
            throw new Exception("角色名已存在");
        }
        List<SysRoleResource> sysRoleResources;

        if (roleResourceDto.getId() == null || roleResourceDto.getId() < 1) {
            roleResourceDto.setId(null);
            sysRoleMapper.insert(roleResourceDto);
            sysRoleResources = getSysRoleResources(roleResourceDto);

        } else {
            sysRoleMapper.updateByPrimaryKeySelective(roleResourceDto);
            SysRoleResource sysRoleResource = new SysRoleResource();
            sysRoleResource.setRoleId(roleResourceDto.getId());
            systemRoleResourceMapper.delete(sysRoleResource);
            sysRoleResources = getSysRoleResources(roleResourceDto);

        }
        systemRoleResourceMapper.insertList(sysRoleResources);
    }

    /**
     * 构建角色与菜单的关系
     *
     * @param roleResourceDto
     * @return
     */
    private List<SysRoleResource> getSysRoleResources(RoleResourceDto roleResourceDto) {
        List<Integer> resourceIds = roleResourceDto.getResourceIds();
        SysRoleResource sysRoleResource;
        List<SysRoleResource> sysRoleResources = new ArrayList<>(resourceIds.size());
        for (Integer resourceId : resourceIds) {
            sysRoleResource = new SysRoleResource();
            sysRoleResource.setResourceId(resourceId);
            sysRoleResource.setRoleId(roleResourceDto.getId());
            sysRoleResources.add(sysRoleResource);
        }
        return sysRoleResources;
    }

    /**
     * 校验角色名称
     *
     * @return 存在--true,反之 false
     */
    public boolean checkRoleName(String roleName, Integer schoolId, Integer roleId) {
        Example example = new Example(tClass);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("roleName", roleName).andEqualTo("status", 0)
                .andEqualTo("schoolId", schoolId);
        if (roleId != null) {
            criteria.andNotEqualTo("id", roleId);
        }
        List<SysRole> sysRoles = mapper.selectByExample(example);
        if (sysRoles.size() > 0) {
            return true;
        }
        return false;
    }

    /**
     * 根据角色名称查找角色
     *
     * @param schoolRoleName
     * @return
     */
    public SysRole findRoleByName(String schoolRoleName,Integer schoolId) {
        Example example = new Example(tClass);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("roleName", schoolRoleName);
        if(!schoolRoleName.equals(SystemConstants.SCHOOL_DEFAULT_ADMIN_NAME)){
            if(schoolId>0){
                criteria.andEqualTo("schoolId", schoolId);
            }
        }
        criteria.andEqualTo("status",0);
        return mapper.selectOneByExample(example);
    }

    /**
     * 查询除了校管理员的所有平台角色
     *
     * @return
     */
    public List<SysRole> selectPlatformRole() {
        Example example = new Example(tClass);
        Example.Criteria criteria = example.createCriteria();
        criteria.andNotEqualTo("roleName", SystemConstants.SCHOOL_DEFAULT_ADMIN_NAME).andEqualTo("schoolId", 0);
        criteria.andEqualTo("status",0);
        example.setOrderByClause("id desc");
        return mapper.selectByExample(example);
    }

    /**
     * create by: tf
     * description: 获取用户角色
     * create time: 2019/11/1 13:41
     */
    public String selectRoleNamesByUserId(Integer userId) {
        return sysRoleMapper.selectRoleNamesByUserId(userId);
    }


    /**
     * 根据用户获取角色列表
     * @param schoolId
     * @return
     */
    public List<SysRole> selectByUserSchoolId(Integer schoolId) {
        Example example=new Example(tClass);
        Example.Criteria criteria=example.createCriteria();
        criteria.andEqualTo("status",0);
        criteria.andEqualTo("schoolId",schoolId);
        if(schoolId==0){
            criteria.andNotEqualTo("roleName", SystemConstants.SCHOOL_DEFAULT_ADMIN_NAME);
        }
        example.setOrderByClause("id desc");
        return mapper.selectByExample(example);
    }
}
