package com.yykj.system.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yykj.system.commons.service.impl.AbstractBaseCrudService;
import com.yykj.system.dao.SystemSubManageMapper;
import com.yykj.system.entity.SystemSubManage;
import com.yykj.system.service.SystemSubManageService;

import tk.mybatis.mapper.entity.Example;

/**
 * 子系统模块管理
 */
@Service
public class SystemSubManageServiceImpl extends AbstractBaseCrudService<SystemSubManage, Serializable> implements SystemSubManageService {

    @Resource
    private SystemSubManageMapper systemSubManageMapper;

    @Override
    public List<Map<String, Object>> selectAllTree() {
        return systemSubManageMapper.selectAllTree();
    }

    @Override
    public PageInfo<SystemSubManage> selectAllPage(Map<String, Object> map) throws RuntimeException {
        PageHelper.startPage(Integer.valueOf(String.valueOf(map.get("pageNumber"))), Integer.valueOf(String.valueOf(map.get("pageSize"))));
        Example example = new Example(SystemSubManage.class);
        Example.Criteria criteria = example.createCriteria();
        if (map.containsKey("name")) {
            criteria.andLike("name", map.get("name") + "%");
        }
        if (map.containsKey("value")) {
            criteria.andLike("value", map.get("value") + "%");
        }
        if (map.containsKey("url")) {
            criteria.andLike("url", map.get("url") + "%");
        }
        List<SystemSubManage> list = systemSubManageMapper.selectByExample(example);
        return new PageInfo<>(list);
    }

    @Override
    public int insert(SystemSubManage t) throws RuntimeException {
        boolean isSingle = isSingle(t);
        if (!isSingle) {
            throw new RuntimeException("数据库中已经存在,name:" + t.getName());
        }
        return systemSubManageMapper.insertSelective(t);
    }

    @Override
    public int updateById(SystemSubManage t) throws RuntimeException {
        boolean isSingle = isSingle(t);
        if (!isSingle) {
            throw new RuntimeException("数据库中已经存在,name:" + t.getName());
        }
        return systemSubManageMapper.updateByPrimaryKeySelective(t);
    }

    /**
     * 判断根据name查询,是否name没有重复
     *
     * @param t
     * @return
     */
    private boolean isSingle(SystemSubManage t) {
        Example example = new Example(SystemSubManage.class);
        example.createCriteria().andEqualTo("name", t.getName()).andNotEqualTo("id", t.getId());
        List<SystemSubManage> list = systemSubManageMapper.selectByExample(example);
        if (list.size() > 0) {
            return false;
        } else {
            return true;
        }
    }

    public int deleteList(List<Integer> ids) throws RuntimeException {
        if (ids == null || ids.isEmpty()) {
            return 0;
        }
        Example example = new Example(SystemSubManage.class);
        example.createCriteria().andIn("id", ids);
        return systemSubManageMapper.deleteByExample(example);
    }
}
