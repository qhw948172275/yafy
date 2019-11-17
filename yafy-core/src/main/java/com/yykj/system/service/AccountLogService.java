package com.yykj.system.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yykj.system.commons.service.impl.AbstractBaseCrudService;
import com.yykj.system.dao.AccountLogMapper;
import com.yykj.system.entity.AccountLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

@Service
public class AccountLogService extends AbstractBaseCrudService<AccountLog,Integer> {

    @Autowired
    AccountLogMapper accountLogMapper;

    /**
     * create by: tf
     * description: 分页搜索操作日志
     * create time: 2019/11/1 14:20
     */
    public PageInfo<AccountLog> selectAllResponsePage(Map<String, Object> map) throws RuntimeException {
        PageHelper.startPage(Integer.valueOf(String.valueOf(map.get("page"))), Integer.valueOf(String.valueOf(map.get("limit"))));
        String keyword = map.containsKey("keyword") ? ("%"+map.get("keyword")+"%") : null;
        Integer schoolId =  map.containsKey("schoolId") ? (Integer) map.get("schoolId") : null;
        Example example = new Example(AccountLog.class);
        Example.Criteria criteria = example.createCriteria();
        if(schoolId != null){
            criteria.andEqualTo("schoolId",schoolId);
        }
        if(keyword != null){
            Example.Criteria and = example.and();
            and.andLike("realName",keyword);
            and.orLike("account",keyword);
        }
        example.setOrderByClause("account_time desc "); // 按照时间倒序
        List<AccountLog> accountLogs = accountLogMapper.selectByExample(example);
        return new PageInfo<>(accountLogs);
    }
}
