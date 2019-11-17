package com.yykj.system.commons.enums;

/**
 * 通用状态枚举
 */
public enum StateEnum {

    ENABLE(0,"正常"),DISABLED(1,"禁用"),DELETE(2,"删除");

    private Integer state;

    private String remark;

    StateEnum(int state,String remark) {
        this.state = state;
        this.remark = remark;
    }

    public Integer getState() {
        return state;
    }

    public String getRemark() {
        return remark;
    }
}
