package com.yykj.business.dto;

import com.yykj.business.entity.Agreement;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author qhw
 * @Date 2019/12/1 0001 下午 19:51
 * @Version V1.0
 **/
public class AgreementBigRepsonse extends Agreement {
    @ApiModelProperty("套房ID")
    private Integer areaId;

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }
}
