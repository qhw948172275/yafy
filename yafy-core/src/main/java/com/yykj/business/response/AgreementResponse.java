package com.yykj.business.response;

import com.yykj.business.entity.Agreement;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author qhw
 * @Date 2019/12/1 0001 下午 17:57
 * @Version V1.0
 **/
public class AgreementResponse extends Agreement {
    @ApiModelProperty("小区名称")
    private String areaName;
    @ApiModelProperty("0-提醒，1-不提醒")
    private Integer repo;

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Integer getRepo() {
        return repo;
    }

    public void setRepo(Integer repo) {
        this.repo = repo;
    }
}
