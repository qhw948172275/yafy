package com.yykj.business.service;

import com.yykj.business.entity.Area;
import com.yykj.system.commons.StringUtils;
import com.yykj.system.commons.service.impl.AbstractBaseCrudService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Author qhw
 * @Date 2019/11/20 0020 下午 17:08
 * @Version V1.0
 **/
@Service
public class AreaService extends AbstractBaseCrudService<Area,Integer> {
    /**
     * description:查询套间列表
     * create by: qhw
     * create time: 2019/11/20 0020 下午 17:33
     */
    public List<Area> selectAreaListByUserId(Integer userId,String areaName,Integer bigLandlordId) {
        Example example=new Example(tClass);
        Example.Criteria criteria=example.createCriteria();
        criteria.andEqualTo("creatorId",userId).andEqualTo("status",0);
        if(StringUtils.isNotEmpty(areaName)){
            Example.Criteria criteria1=example.and();
            StringBuilder stringBuilder=new StringBuilder("%").append(areaName).append("%");
            criteria1.andLike("name",stringBuilder.toString());
        }
        if(bigLandlordId!=null){
            criteria.andEqualTo("bigLandlordId",bigLandlordId);
        }
        example.setOrderByClause("id desc");
        return mapper.selectByExample(example);
    }
}
