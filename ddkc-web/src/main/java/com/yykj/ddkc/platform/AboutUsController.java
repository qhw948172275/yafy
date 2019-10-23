package com.yykj.ddkc.platform;

import com.yykj.ddkc.entity.AboutUs;
import com.yykj.ddkc.service.AboutUsService;
import com.yykj.system.commons.result.JsonResult;
import com.yykj.system.commons.result.JsonResultUtils;
import com.yykj.system.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "platform/aboutUs")
public class AboutUsController extends BaseController {
    @Autowired
    AboutUsService aboutUsService;

    @GetMapping(value = "")
    public String index(Integer type, Model model){
       model.addAttribute("type",type);
       AboutUs aboutUs=aboutUsService.selectByType(type);
        model.addAttribute("aboutUs",aboutUs);
       return "ddkc/platform/aboutUs/info";
    }

    /**
     * 保存协议
     * @param aboutUs
     * @return
     */
    @PostMapping(value = "save")
    @ResponseBody
    public JsonResult save(AboutUs aboutUs){
        try {
              if(aboutUs.getId()==null){
                  aboutUsService.insert(aboutUs);
              }else{
                  aboutUsService.updateById(aboutUs);
              }
            return JsonResultUtils.buildJsonOK();
        }catch (Exception e){
            e.printStackTrace();
        }
        return JsonResultUtils.buildJsonFail();
    }
}
