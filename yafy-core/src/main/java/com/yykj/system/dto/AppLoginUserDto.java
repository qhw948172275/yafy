package com.yykj.system.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * @ClassName AppLoginUserDto
 * @Description: APP登录参数对象
 * @Author qhw
 * @Date 2019/11/5 0005 下午 16:50
 * @Version V1.0
 **/
public class AppLoginUserDto {
    @ApiModelProperty(value = "账号")
    private String username;
    @ApiModelProperty(value = "密码")
    private String password;
    @ApiModelProperty(value = "家长端必填，学校ID")
    private Integer schoolId;
    @ApiModelProperty(value = "必填：parent-client 家长端，teacher-client 教师端")
    private String appType;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }
}
