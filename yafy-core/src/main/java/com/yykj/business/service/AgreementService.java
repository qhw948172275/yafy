package com.yykj.business.service;

import com.yykj.business.dao.AgreementMapper;
import com.yykj.business.entity.Agreement;
import com.yykj.system.commons.service.impl.AbstractBaseCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
     * description: TODO 合同记录
     * create by: qhw
     * create time: 2019/11/17 0017 下午 22:51
     */
    public List<Agreement> selectAgreementByAreaId(Integer areaId) {
        return agreementMapper.selectAgreementByAreaId(areaId);
    }
}
