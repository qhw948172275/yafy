package com.yykj.system.service;

import java.util.List;

import com.yykj.system.entity.SystemDepartment;

/**
 * 系统部门管理接口
 * 
 * @author hanfeng
 * @dateTime 2018/2/4 14:27
 */
public interface SystemDepartmentService {

    /**
     * 获取所有的部门列表
     * @return
     */
    List<SystemDepartment> getAllList();

    /**
     * 根据父parentid 获取部门列表，包含删除的部门
     * @param parentId
     * @return
     */
    List<SystemDepartment> getAllListByParentId(Integer parentId);

    /**
     * 查询有效部门
     * @param parentId
     * @return
     */
    List<SystemDepartment> getValidListByParentId(Integer parentId);

    /**
     * 根据id查询部门
     * @param id
     * @return
     */
    SystemDepartment getById(Integer id);

    /**
     * 根据name查询部门
     * @param name
     * @return
     */
    List<SystemDepartment> getAllListByName(String name) ;

    /**
     * 根据name查询有效部门
     * @param name
     * @return
     */
    List<SystemDepartment> getValidListByName(String name) ;

    /**
     * 插入系统部门
     * @param systemDepartment {@link cn.hlhdj.duoji.system.entity.SystemDepartment}
     * @return
     */
    Object insert(SystemDepartment systemDepartment);

    /**
     * 批量插入系统部门
     * @param systemDepartments {@link cn.hlhdj.duoji.system.entity.SystemDepartment}
     * @return
     */
    Object insertList(List<SystemDepartment> systemDepartments);

    /**
     * 更新系统部门
     * @param systemDepartment {@link cn.hlhdj.duoji.system.entity.SystemDepartment}
     * @return
     */
    Object update(SystemDepartment systemDepartment);


}
