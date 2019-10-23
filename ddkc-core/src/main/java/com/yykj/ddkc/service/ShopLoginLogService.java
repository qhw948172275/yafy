package com.yykj.ddkc.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yykj.ddkc.dao.ShopLoginLogMapper;
import com.yykj.ddkc.entity.ShopLoginLog;
import com.yykj.ddkc.response.ShopLoginLogResponse;
import com.yykj.system.commons.service.impl.AbstractBaseCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

@Service
public class ShopLoginLogService extends AbstractBaseCrudService<ShopLoginLog,Integer> {

    @Autowired
    ShopLoginLogMapper shopLoginLogMapper;


    public PageInfo<ShopLoginLogResponse> selectAllPageFor(Map<String, Object> map) throws RuntimeException {
        PageHelper.startPage(Integer.valueOf(String.valueOf(map.get("pageNumber"))), Integer.valueOf(String.valueOf(map.get("pageSize"))));
        String keyword=map.containsKey("keyword")?"%"+map.get("keyword")+"%":null;
        List<ShopLoginLogResponse> list= shopLoginLogMapper.selectAllPage(keyword);
        return new PageInfo<>(list);
    }

}
