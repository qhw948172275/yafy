package com.yykj.business.dao;

import com.yykj.business.entity.Agreement;
import com.yykj.system.commons.service.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AgreementMapper extends MyMapper<Agreement> {
    /**
     * description: TODO 合同记录
     * create by: qhw
     * create time: 2019/11/17 0017 下午 22:52
     */
    List<Agreement> selectAgreementByAreaId(@Param("areaId") Integer areaId);
}