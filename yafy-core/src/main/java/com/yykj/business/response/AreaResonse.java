package com.yykj.business.response;

import com.yykj.business.entity.Area;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author qhw
 * @Date 2019/12/1 0001 下午 16:02
 * @Version V1.0
 **/
public class AreaResonse extends Area {
    @ApiModelProperty("大房东名称")
    private String bigLandlordName;

    public String getBigLandlordName() {
        return bigLandlordName;
    }

    public void setBigLandlordName(String bigLandlordName) {
        this.bigLandlordName = bigLandlordName;
    }
}
