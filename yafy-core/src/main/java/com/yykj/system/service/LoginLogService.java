package com.yykj.system.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yykj.system.commons.service.impl.AbstractBaseCrudService;
import com.yykj.system.dao.SysLoginLogMapper;
import com.yykj.system.entity.SysLoginLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

@Service
public class LoginLogService extends AbstractBaseCrudService<SysLoginLog,Integer> {

    @Autowired
    SysLoginLogMapper sysLoginLogMapper;

    /**
     * create by: tf
     * description: 分页搜索登陆日志
     * create time: 2019/11/1 10:48
     */
    public PageInfo<SysLoginLog> selectAllResponsePage(Map<String, Object> map) throws RuntimeException {
        PageHelper.startPage(Integer.valueOf(String.valueOf(map.get("page"))), Integer.valueOf(String.valueOf(map.get("limit"))));
        String name = map.containsKey("name") ? ("%"+map.get("name")+"%") : null;
        String realName =  map.containsKey("realName") ? ("%"+map.get("realName")+"%") : null;
        Integer schoolId =  map.containsKey("schoolId") ? (Integer) map.get("schoolId") : null;
        Example example = new Example(SysLoginLog.class);
        Example.Criteria criteria = example.createCriteria();
        if(name != null){
            criteria.andLike("userName",name);
        }
        if(realName != null){
            criteria.andLike("realmName",realName);
        }
        if(schoolId != null){
            criteria.andEqualTo("schoolId",schoolId);
        }
        example.setOrderByClause("login_time desc "); // 按照时间倒序
        List<SysLoginLog> sysLoginLogs = sysLoginLogMapper.selectByExample(example);
        return new PageInfo<>(sysLoginLogs);
    }
}
