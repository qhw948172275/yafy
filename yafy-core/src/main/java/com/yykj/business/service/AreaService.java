package com.yykj.business.service;

import com.yykj.business.dao.AgreementMapper;
import com.yykj.business.dao.AreaAgreementMapper;
import com.yykj.business.dao.AreaMapper;
import com.yykj.business.dto.AreaDto;
import com.yykj.business.entity.Agreement;
import com.yykj.business.entity.Area;
import com.yykj.business.entity.AreaAgreement;
import com.yykj.business.response.AreaResonse;
import com.yykj.system.commons.StringUtils;
import com.yykj.system.commons.service.impl.AbstractBaseCrudService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author qhw
 * @Date 2019/11/20 0020 下午 17:08
 * @Version V1.0
 **/
@Service
public class AreaService extends AbstractBaseCrudService<Area,Integer> {
    @Autowired
    AreaMapper areaMapper;
    @Autowired
    AgreementMapper agreementMapper;
    @Autowired
    AreaAgreementMapper areaAgreementMapper;
    /**
     * description:查询套间列表
     * create by: qhw
     * create time: 2019/11/20 0020 下午 17:33
     */
    public List<AreaResonse> selectAreaListByUserId(Integer userId,String areaName,Integer bigLandlordId,String unitName) {
//        Example example=new Example(tClass);
//        Example.Criteria criteria=example.createCriteria();
//        criteria.andEqualTo("creatorId",userId).andEqualTo("status",0);
//        if(StringUtils.isNotEmpty(areaName)){
//            Example.Criteria criteria1=example.and();
//            StringBuilder stringBuilder=new StringBuilder("%").append(areaName).append("%");
//            criteria1.andLike("name",stringBuilder.toString());
//        }
//        if(StringUtils.isNotEmpty(unitName)){
//            Example.Criteria criteria1=example.and();
//            StringBuilder stringBuilder=new StringBuilder("%").append(unitName).append("%");
//            criteria1.andLike("unit",stringBuilder.toString());
//        }
//        if(bigLandlordId!=null){
//            criteria.andEqualTo("bigLandlordId",bigLandlordId);
//        }
//        example.setOrderByClause("id desc");
        areaName=StringUtils.isEmpty(areaName)?null:new StringBuilder("%").append(areaName).append("%").toString();
        unitName=StringUtils.isEmpty(unitName)?null:new StringBuilder("%").append(unitName).append("%").toString();
        List<AreaResonse> areaResonses=areaMapper.selectAreaListByUserId(userId,areaName,bigLandlordId,unitName);
        return areaResonses;
       // return mapper.selectByExample(example);
    }

    @Transactional
    public void save(AreaDto areaDto) {
        Area area=new Area();
        BeanUtils.copyProperties(areaDto,area);
        mapper.insert(area);
        Agreement agreement=new Agreement();
        BeanUtils.copyProperties(areaDto,agreement);
        agreementMapper.insert(agreement);
        AreaAgreement areaAgreement=new AreaAgreement();
        areaAgreement.setAgreementId(agreement.getId());
        areaAgreement.setAreaId(area.getId());
        areaAgreementMapper.insert(areaAgreement);

    }

    @Transactional
    public void updateByAreaDto(AreaDto areaDto) {
        if(areaDto.getAgreementId()==null){
            throw new RuntimeException("AgreementId 不能为 null");
        }
        Area area=new Area();
        BeanUtils.copyProperties(areaDto,area);
        mapper.updateByPrimaryKeySelective(area);
        Agreement agreement=new Agreement();
        BeanUtils.copyProperties(areaDto,agreement);
        agreement.setId(areaDto.getAgreementId());
        agreementMapper.updateByPrimaryKeySelective(agreement);
    }
}
