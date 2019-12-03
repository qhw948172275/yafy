package com.yykj.business.dto;

import com.yykj.business.entity.Agreement;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author qhw
 * @Date 2019/12/3 0003 下午 18:09
 * @Version V1.0
 **/
public class AgreementRenantDto extends Agreement {
    @ApiModelProperty("租客ID")
    private Integer renantId;

    public Integer getRenantId() {
        return renantId;
    }

    public void setRenantId(Integer renantId) {
        this.renantId = renantId;
    }
}
