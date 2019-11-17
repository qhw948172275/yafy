package com.yykj.system.service;

import com.yykj.system.commons.service.impl.AbstractBaseCrudService;
import com.yykj.system.dao.DictionaryMapper;
import com.yykj.system.entity.Dictionary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 *  @author: 王晓
 *  @Date: 2019/11/1 10:04
 *  @Description: 系统字典管理
 */
@Service
public class DictionaryService extends AbstractBaseCrudService<Dictionary, Integer> {

    @Autowired
    DictionaryMapper dictionaryMapper;


    public List<Dictionary> getDataWithList(String typeCode, String label, String typeValue) {
        Example example =new Example(tClass);
        Example.Criteria criteria=example.createCriteria();
        if(typeCode != null && typeCode != ""){
            criteria.andEqualTo("typeCode",typeCode);
        }
        if(label != null && label != ""){
            criteria.andEqualTo("label",label);
        }

        if(typeValue != null && typeValue != ""){
            criteria.andEqualTo("typeValue",typeValue);
        }
        example.setOrderByClause("create_time desc");
        return mapper.selectByExample(example);
    }

    public List<Dictionary> getRemoteByType(String typeCode) {
        Example example =new Example(tClass);
        Example.Criteria criteria=example.createCriteria();
        if(typeCode != null && typeCode != ""){
            criteria.andEqualTo("typeCode",typeCode);
        }
        return mapper.selectByExample(example);
    }
}
