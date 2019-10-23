package com.yykj.commons.wechat;

import cn.binarywang.wx.miniapp.api.WxMaQrcodeService;
import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.WxMaConfig;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import com.yykj.commons.upyun.UpYunUtils;
import com.yykj.system.commons.SystemConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
@Component
public class Wxaqrcode {

    @Autowired
    AppConfig appConfig;

    /**
     * 获取二维码地址
     * @param shopId
     * @return
     * @throws Exception
     */
    public String getFileName(Integer shopId) throws Exception {
        WxMaConfig wxMaConfig=new WxMaDefaultConfigImpl();
        ((WxMaDefaultConfigImpl) wxMaConfig).setAppid(appConfig.getAppid());
        ((WxMaDefaultConfigImpl) wxMaConfig).setSecret(appConfig.getAppsecret());
        WxMaService wxMaService=new WxMaServiceImpl();
        wxMaService.setWxMaConfig(wxMaConfig);
        WxMaQrcodeService wxMaQrcodeService=wxMaService.getQrcodeService();
        File file= wxMaQrcodeService.createQrcode("pages/buyers/index/index?id="+shopId,430);

        String fileUrl= UpYunUtils.uploadFile(file, SystemConstants.UPLOAD_DIR);
        return  SystemConstants.VISIT_DIR+fileUrl;
    }

}
