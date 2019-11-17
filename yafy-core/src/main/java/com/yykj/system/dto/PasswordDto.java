package com.yykj.system.dto;


/**
 * @ClassName PasswordDto
 * @Description: TODO
 * @Author qhw
 * @Date 2019/11/7 0007 上午 9:57
 * @Version V1.0
 **/
public class PasswordDto {
    private String oldPassword;
    private String newPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
