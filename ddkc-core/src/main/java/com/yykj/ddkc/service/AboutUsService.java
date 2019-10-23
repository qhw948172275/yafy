package com.yykj.ddkc.service;

import com.yykj.ddkc.entity.AboutUs;
import com.yykj.system.commons.service.impl.AbstractBaseCrudService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

@Service
public class AboutUsService extends AbstractBaseCrudService<AboutUs,Integer> {

    public AboutUs selectByType(Integer type) {
        Example example=new Example(tClass);
        Example.Criteria criteria=example.createCriteria();
        criteria.andEqualTo("types",type);
        return mapper.selectOneByExample(example);
    }
}
