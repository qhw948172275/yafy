package com.yykj.system.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yykj.system.dao.SystemDepartmentMapper;
import com.yykj.system.entity.SystemDepartment;
import com.yykj.system.service.SystemDepartmentService;

import tk.mybatis.mapper.entity.Example;

/**
 * 资源文件管理接口
 *
 * @author kimi
 * @dateTime 2012-3-20 上午10:39:37
 */
@Service
public class SystemDepartmentServiceImpl implements SystemDepartmentService {

    @Resource
    private SystemDepartmentMapper systemDepartmentMapper;

    @Override
    public List<SystemDepartment> getAllList() {
        return systemDepartmentMapper.selectAll();
    }

    @Override
    public List<SystemDepartment> getAllListByParentId(Integer parentId) {
        return getListByParentId(parentId, null);
    }

    @Override
    public List<SystemDepartment> getValidListByParentId(Integer parentId) {
        return getListByParentId(parentId, 1);
    }

    @Override
    public SystemDepartment getById(Integer id) {
        return systemDepartmentMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<SystemDepartment> getAllListByName(String name) {
        return getByName(name, null);
    }

    @Override
    public List<SystemDepartment> getValidListByName(String name) {
        return getByName(name, 1);
    }

    private List<SystemDepartment> getByName(String name, Integer status) {
        SystemDepartment systemDepartment = new SystemDepartment();
        systemDepartment.setName(name);
        if (status == null) {
            systemDepartment.setStatus(status);
        }
        return systemDepartmentMapper.select(systemDepartment);
    }

    private List<SystemDepartment> getListByParentId(Integer parentId, Integer status) {
        Example example = new Example(SystemDepartment.class);
        Example.Criteria criteria = example.createCriteria().andEqualTo("parentid", parentId);
        if (status != null) {
            criteria.andEqualTo("status", status);
        }

        return systemDepartmentMapper.selectByExample(example);
    }

    @Override
    public Object insert(SystemDepartment systemDepartment) {
        if (systemDepartment != null) {
            systemDepartmentMapper.insert(systemDepartment);
        }
        return systemDepartment;
    }

    @Override
    public Object insertList(List<SystemDepartment> systemDepartments) {
        if (systemDepartments != null && !systemDepartments.isEmpty()) {
            systemDepartmentMapper.insertList(systemDepartments);
        }
        return systemDepartments;
    }

    @Override
    public Object update(SystemDepartment systemDepartment) {
        if (systemDepartment != null && systemDepartment.getId() != null) {
            systemDepartment.setGmtCreate(null);
            systemDepartmentMapper.updateByPrimaryKey(systemDepartment);
        }
        return systemDepartment;
    }
}
