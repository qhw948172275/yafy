package com.yykj.system.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.formula.functions.T;

import com.github.pagehelper.PageInfo;
import com.yykj.system.commons.service.BaseCrudService;
import com.yykj.system.entity.SystemSubManage;

/**
 * 子系统管理接口
 */
public interface SystemSubManageService extends BaseCrudService<SystemSubManage,Serializable> {

    /**
     * 查询列表 , tree
     * @return
     */
    List<Map<String ,Object>> selectAllTree();

    /**
     * 分页查询
     *
     * @param map 查询参数
     * @return PageInfo<T>
     */
    PageInfo<SystemSubManage> selectAllPage(Map<String, Object> map) throws RuntimeException;

    int insert(SystemSubManage obj) throws RuntimeException;

    /**
     * 根据id更新, 先查询,在更新
     * @param obj {@link T}
     * @return
     * @throws RuntimeException
     */
    int updateById(SystemSubManage obj) throws RuntimeException;

    /**
     * 根据id批量删除
     * @param ids
     * @return
     * @throws RuntimeException
     */
    int deleteList(List<Integer> ids) throws RuntimeException;
}
