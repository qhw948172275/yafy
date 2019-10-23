package com.yykj.system.constants;

import java.io.Serializable;

public class SystemConfig implements Serializable{


    private static final long serialVersionUID = 1651914339773442144L;
    /**
     * controller层参数 调用{@link JsonResultUtils}的方法中有接口调用的方法是否输出错误日志到页面
     * @see JsonResultUtils#returnBootTable(cn.hlhdj.duoji.common.controller.CallbackPageInfo, boolean)
     * @see JsonResultUtils#returnAjax(cn.hlhdj.duoji.common.controller.CallbackAjax, boolean, java.lang.String, java.lang.String)
     */
    public final static boolean IS_OUT_ERROR = true;


}
