package com.yykj.business.response;

import io.swagger.annotations.ApiModelProperty;

/**
 * @Author qhw
 * @Date 2019/12/3 0003 下午 18:46
 * @Version V1.0
 **/
public class AgreementRenantResponse extends AgreementResponse {
    @ApiModelProperty("租客名称")
    private String renantName;
    @ApiModelProperty("租客电话")
    private String renantPhone;

    public String getRenantName() {
        return renantName;
    }

    public void setRenantName(String renantName) {
        this.renantName = renantName;
    }

    public String getRenantPhone() {
        return renantPhone;
    }

    public void setRenantPhone(String renantPhone) {
        this.renantPhone = renantPhone;
    }
}
