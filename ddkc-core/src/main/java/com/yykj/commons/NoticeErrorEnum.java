package com.yykj.commons;
/**
 * 通知错误枚举信息
* @author chenbiao
* @date 2019年9月23日 下午8:34:23 
*
 */
public enum NoticeErrorEnum {
	
	
	_10000("10000","参数异常"),
	_10001("10001","手机号格式不正确"),
	_10002("10002","模板不存在"),
	_10003("10003","模板变量不正确"),
	_10004("10004","变量中含有敏感词"),
	_10005("10005","变量名称不匹配"),
	_10006("10006","短信长度过长"),
	_10007("10007","手机号查询不到归属地"),
	_10008("10008","产品错误"),
	_10009("10009","价格错误"),
	_10010("10010","重复调用"),
	_99999("99999","系统错误"),
	_00000("00000","调用成功");
	
	
	
	
    // 成员变量  
    private String code;  
    private String msg;  
    // 构造方法  
    private NoticeErrorEnum(String code, String msg) {  
        this.code = code;  
        this.msg = msg;
    }  
    // 普通方法  
    public static String getMsgByCode(String code) {  
        for (NoticeErrorEnum c : NoticeErrorEnum.values()) {  
            if (c.getCode().equals(code)) {  
                return c.msg;  
            }  
        }  
        return null;  
    }  
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}  


}
