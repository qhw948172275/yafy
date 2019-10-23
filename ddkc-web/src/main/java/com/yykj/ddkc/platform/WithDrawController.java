package com.yykj.ddkc.platform;

import com.yykj.ddkc.service.WithDrawService;
import com.yykj.system.commons.StringUtils;
import com.yykj.system.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping(value = "platform/withDraw")
public class WithDrawController extends BaseController {
    @Autowired
    WithDrawService withDrawService;

    /**
     * 提现
     * @return
     */
    @GetMapping(value = "index")
    public String index(){

        return "ddkc/platform/withDraw/index";
    }

    /**
     * 异步加载提现详情
     * @param request
     * @return
     */
    @GetMapping(value = "ajaxLoad")
    @ResponseBody
    public Object ajaxLoad(HttpServletRequest request,String dateStr){
        Map<String, Object> map = getMapByExample(request);
        if(StringUtils.isNotEmpty(dateStr)){
            String[] dates=dateStr.split(" - ");
            map.put("startTime",dates[0]);
            map.put("endTime",dates[1]);
        }
        Boolean isSuperAdmin=checkingSuperAdministrator(request);
        if(!isSuperAdmin){
            map.put("createId",getLoginUserId(request));
        }
        return buildPageResultByAsync(withDrawService.selectAll(map));
    }

    /**
     * 商家提现详情
     * @param dateStr
     * @param model
     * @return
     */
    @GetMapping(value = "detail")
   public String detail(String dateStr, Model model){
        model.addAttribute("dateStr",dateStr);
        return "ddkc/platform/withDraw/detail";
   }

    /**
     * 异步加载商家提现详情数据
     * @param request
     * @param dateStr
     * @param shopName
     * @param shopId
     * @return
     */
   @ResponseBody
   @GetMapping(value = "detailAjaxLoad")
   public Object detailAjaxLoad(HttpServletRequest request,String dateStr,String shopName,Integer shopId){
       Map<String, Object> map = getMapByExample(request);
       if(StringUtils.isNotEmpty(dateStr)){
           String[] dates=dateStr.split(" - ");
           map.put("startTime",dates[0]);
           map.put("endTime",dates[1]);
       }
       if(StringUtils.isNotEmpty(shopName)){
           map.put("shopName",shopName);
       }
       if(shopId != null){
           map.put("shopId",shopId);
       }
       return buildPageResultByAsync(withDrawService.selectShopWithDraw(map));
   }
}

