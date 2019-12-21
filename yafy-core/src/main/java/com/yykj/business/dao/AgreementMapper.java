package com.yykj.business.dao;

import com.yykj.business.entity.Agreement;
import com.yykj.business.response.AgreementRenantResponse;
import com.yykj.business.response.AgreementResponse;
import com.yykj.system.commons.service.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AgreementMapper extends MyMapper<Agreement> {
    /**
     * description: 合同记录
     * create by: qhw
     * create time: 2019/11/17 0017 下午 22:52
     */
    List<Agreement> selectAgreementByAreaId(@Param("areaId") Integer areaId);

    List<AgreementResponse> selectAgreementResponse(@Param("userId") Integer userId,@Param("areaId") Integer areaId,
                                                    @Param("areaName") String areaName,@Param("startTime") String startTime,
                                                    @Param("endTime") String endTime);

    Integer selectCheckAgreement(@Param("areaId") Integer areaId,@Param("userId")Integer userId);

    List<Agreement> selectAgreement(@Param("areaId") Integer areaId,@Param("userId") Integer userId);

    List<AgreementRenantResponse> selectAgreementRenantResponse(@Param("userId") Integer userId, @Param("areaId") Integer areaId,
                                                                @Param("areaName") String areaName, @Param("startTime") String startTime,
                                                                @Param("endTime") String endTime);

    Integer selectCheckAgreementRenant(@Param("renantId") Integer renantId,@Param("userId") Integer userId);
}