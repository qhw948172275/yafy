package com.yykj.ddkc.platform;

import com.yykj.ddkc.response.OrderDetailResponse;
import com.yykj.ddkc.service.OrderService;
import com.yykj.system.commons.StringUtils;
import com.yykj.system.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller(value = "platformOrderController")
@RequestMapping(value = "platform/order")
public class OrderController extends BaseController {
    @Autowired
    OrderService orderService;

    /**
     * 进入订单管理主页
     * @return
     */
    @GetMapping(value = "index")
    public String index(){
        return "ddkc/platform/order/index";
    }

    /**
     * 异步加载订单数据
     * @return
     */
    @ResponseBody
    @GetMapping(value = "ajaxLoad")
    public Object ajaxLoad(HttpServletRequest request,String shopName,Integer shopId,String dateStr,Integer status){
        Map<String, Object> map = getMapByExample(request);
        Boolean isSuperAdmin=checkingSuperAdministrator(request);
        if(!isSuperAdmin){
            map.put("createId",getLoginUserId(request));
        }
        if(StringUtils.isNotEmpty(shopName)){
            map.put("shopName",shopName);
        }
        if(shopId!=null){
            map.put("shopId",shopId);
        }
        if(StringUtils.isNotEmpty(dateStr)){
            String[] dateStrs=dateStr.split(" - ");
            map.put("startTime",dateStrs[0]);
            map.put("endTime",dateStrs[1]);
        }
        if(status!=null){
            map.put("status",status);
        }
        return  buildPageResultByAsync(orderService.selectAllPagefor(map));
    }

    /**
     * 订单详情
     * @return
     */
    @GetMapping(value = "detail")
    public String orderDetail(Model model,Integer orderId){
        OrderDetailResponse orderDetailResponse=orderService.selectOrderDetailResponseByOrderId(orderId);
        model.addAttribute("orderDetailResponse",orderDetailResponse);
        return "ddkc/platform/order/info";
    }

    /**
     *
     * 平台流水
     * @return
     */
    @GetMapping(value = "platformTurnover")
    public String  platformTurnover(){

        return "ddkc/platform/platformTurnover/index";
    }

    /**
     * 平台流水异步加载数据
     * @return
     */
    @GetMapping(value = "platformAjaxLod")
    @ResponseBody
    public Object platformAjaxLod(HttpServletRequest request,String dateStr){
        Map<String, Object> map = getMapByExample(request);
        Boolean isSuperAdmin=checkingSuperAdministrator(request);
        if(!isSuperAdmin){
            map.put("createId",getLoginUserId(request));
        }
        if(StringUtils.isNotEmpty(dateStr)){
            String[] dates=dateStr.split(" - ");
            map.put("startTime",dates[0]);
            map.put("endTime",dates[1]);
        }
        return buildPageResultByAsync(orderService.selectAllPageByDate(map));
    }

    /**
     * 商家流水
     * @return
     */
    @GetMapping(value = "shopTurnover")
    public String shopTurnover(String dateStr,Model model){
        model.addAttribute("dateStr",dateStr);
        return "ddkc/platform/shopTurnover/index";
    }

    /**
     * 商家收入流水数据异步加载
     * @param request
     * @param dateStr
     * @return
     */
    @ResponseBody
    @GetMapping(value = "shopAjaxLoad")
    public Object shopAjaxLoad(HttpServletRequest request,String dateStr){
        Map<String, Object> map = getMapByExample(request);
        Boolean isSuperAdmin=checkingSuperAdministrator(request);
        if(!isSuperAdmin){
            map.put("createId",getLoginUserId(request));
        }
        if(StringUtils.isNotEmpty(dateStr)){
            String[] dates=dateStr.split(" - ");
            map.put("startTime",dates[0]);
            map.put("endTime",dates[1]);
        }
        return buildPageResultByAsync(orderService.selectShopPageByDate(map));
    }
}
