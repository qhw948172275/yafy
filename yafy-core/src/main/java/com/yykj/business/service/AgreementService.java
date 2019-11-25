package com.yykj.business.service;

import com.yykj.business.dao.AgreementMapper;
import com.yykj.business.entity.Agreement;
import com.yykj.system.commons.SystemConstants;
import com.yykj.system.commons.service.impl.AbstractBaseCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
}
