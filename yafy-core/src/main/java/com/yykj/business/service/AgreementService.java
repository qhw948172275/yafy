package com.yykj.business.service;

import com.yykj.business.dao.AgreementMapper;
import com.yykj.business.dao.AreaAgreementMapper;
import com.yykj.business.dto.AgreementBigRepsonse;
import com.yykj.business.entity.Agreement;
import com.yykj.business.entity.AreaAgreement;
import com.yykj.business.response.AgreementResponse;
import com.yykj.system.commons.StringUtils;
import com.yykj.system.commons.SystemConstants;
import com.yykj.system.commons.service.impl.AbstractBaseCrudService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @ClassName AgreementService
 * @Description: TODO
 * @Author qhw
 * @Date 2019/11/17 0017 下午 22:49
 * @Version V1.0
 **/
@Service
public class AgreementService extends AbstractBaseCrudService<Agreement,Integer> {
    @Autowired
    AgreementMapper agreementMapper;
    @Autowired
    AreaAgreementMapper areaAgreementMapper;
    /**
     * description:  合同记录
     * create by: qhw
     * create time: 2019/11/17 0017 下午 22:51
     */
    public List<Agreement> selectAgreementByAreaId(Integer areaId) {
        return agreementMapper.selectAgreementByAreaId(areaId);
    }

    /**
     * description:根据用户查询合同列表
     * create by: qhw
     * create time: 2019/11/25 0025 下午 22:30
     */
    public List<Agreement> selectAllListByUserId(Integer userId) {
        Example example=new Example(tClass);
        Example.Criteria criteria=example.createCriteria();
        criteria.andEqualTo("creatorId",userId).andEqualTo("status", SystemConstants.STATUS_OK.byteValue());
        example.setOrderByClause("id desc");
        return mapper.selectByExample(example);
    }

    public List<AgreementResponse> selectAgreementResponse(Integer userId,Integer areaId,String areaName,String startTime,String endTime){

        areaName=StringUtils.isEmpty(areaName)?null:new StringBuilder("%").append(areaName).append("%").toString();
        startTime=StringUtils.isEmpty(startTime)?null:startTime;
        endTime=StringUtils.isEmpty(endTime)?null:endTime;
        return agreementMapper.selectAgreementResponse(userId,areaId,areaName,startTime,endTime);
    }
    /**
     * description:合同续期
     * create by: qhw
     * create time: 2019/12/1 0001 下午 19:55
     */
    @Transactional
    public void addAgr(AgreementBigRepsonse agreementBigRepsonse) {
       if(this.selectCheckAgreement(agreementBigRepsonse.getAreaId(),agreementBigRepsonse.getCreatorId())>0){
           throw new RuntimeException("该套房有合同没有结束");
        }
        Agreement agreement=new Agreement();
        BeanUtils.copyProperties(agreementBigRepsonse,agreement);
        mapper.insert(agreement);
        AreaAgreement areaAgreement=new AreaAgreement();
        areaAgreement.setAgreementId(agreement.getId());
        areaAgreement.setAreaId(agreementBigRepsonse.getAreaId());
        areaAgreementMapper.insert(areaAgreement);
    }
    /**
     * description:查询是否存在没有结束的合同
     * create by: qhw
     * create time: 2019/12/1 0001 下午 20:03
     */
    public Integer selectCheckAgreement(Integer areaId,Integer userId){
        if(areaId==null){
            throw new RuntimeException("套房ID不能为空");
        }
        return agreementMapper.selectCheckAgreement(areaId,userId);
    }


    public List<Agreement> selectAgreement(Integer areaId,Integer userId){
        return agreementMapper.selectAgreement(areaId,userId);
    }
}
