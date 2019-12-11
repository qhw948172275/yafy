package com.yykj.business.dto;

import com.yykj.business.entity.RentManage;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author qhw
 * @Date 2019/12/11 0011 下午 16:41
 * @Version V1.0
 **/
public class RentManageAreaDto extends RentManage {
    @ApiModelProperty("套房ID")
    private Integer areaId;

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }
}
