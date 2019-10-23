package com.yykj.ddkc.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yykj.ddkc.dao.OperateLogMapper;
import com.yykj.ddkc.entity.OperateLog;
import com.yykj.system.commons.service.impl.AbstractBaseCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

@Service
public class OperateLogService extends AbstractBaseCrudService<OperateLog,Integer> {

    @Autowired
    OperateLogMapper operateLogMapper;

    /**
     * 查询全商家的操作日志
     * @param map
     * @return
     * @throws RuntimeException
     */
    public PageInfo<OperateLog> selectAllPage(Map<String, Object> map) throws RuntimeException {
        PageHelper.startPage(Integer.valueOf(String.valueOf(map.get("pageNumber"))), Integer.valueOf(String.valueOf(map.get("pageSize"))));
        Example example=new Example(tClass);
        if(map.containsKey("keyword")){
            Example.Criteria criteria=example.createCriteria();
            criteria.andLike("realName","%"+map.get("keyword")+"%").orLike("account","%"+map.get("keyword")+"%");
        }
        example.setOrderByClause("operate_time desc");
        List<OperateLog> list= mapper.selectByExample(example);
        return new PageInfo<>(list);
    }
}
