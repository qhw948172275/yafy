package com.yykj.system.controller;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.yykj.ddkc.response.*;
import com.yykj.ddkc.service.OrderService;
import com.yykj.ddkc.service.ShopService;
import com.yykj.ddkc.service.WithDrawService;
import com.yykj.system.commons.CalendarUtils;
import com.yykj.system.commons.JsonUtils;
import com.yykj.system.commons.result.JsonResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.yykj.system.entity.SystemResource;
import com.yykj.system.response.SystemUserExt;

@Controller("systemIndexController")
public class IndexController extends BaseController {

    private static Logger log = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    OrderService orderService;
    @Autowired
    ShopService shopService;
    @Autowired
    WithDrawService withDrawService;

    /**
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @author kimi
     * @dateTime 2012-3-13 下午7:59:06
     */
    @RequestMapping(value = {"","/system", "/system/index"})
    protected String index(final HttpServletRequest request, final HttpServletResponse response) {
        SystemUserExt user = super.getLoginUser(request);
        if (null != user) {
            if (null == request.getSession().getAttribute("auth")) {
                List<SystemResource> resources = systemResourceService.getResourceListByUserId(user.getUser().getId());
                log.debug("the resources is :" + resources.size());
                log.debug(JSON.toJSONString(resources));
                request.getSession().setAttribute("auth", resources);
            }

            log.debug("________________" + user.getUsername());
            return "/system/index";
        }
        return "/system/login";
    }


    /**
     * @return
     * @throws Exception
     * @author kimi
     * @dateTime 2012-3-13 下午7:59:06
     */
    @RequestMapping(value = "/system/main")
    protected ModelAndView main(Model model,HttpServletRequest request) throws Exception {
        /**
         * 查询今天的订单量，成交额
         */
        String nowDate=CalendarUtils.dateToString(CalendarUtils.getDate(),CalendarUtils.yyyy_MM_dd);
        Map<String,Object> map=new HashMap<>();
        map.put("startTime",nowDate);
        map.put("endTime",nowDate);
        Boolean isSuperAdmin=checkingSuperAdministrator(request);
        if(!isSuperAdmin){
            map.put("createId",getLoginUserId(request));
        }
        List<OrderCountResponse> orderCountResponses=orderService.selectAllPageByDate(map).getList();
        if(orderCountResponses.size()>0){
            model.addAttribute("orderCountResponse",orderCountResponses.get(0));
        }else{
            model.addAttribute("orderCountResponse",new OrderCountResponse(0));
        }

        /**
         * 查询商家数量
         */

        Integer shopCount=shopService.selectCount(map);
        model.addAttribute("shopCount",shopCount);
        /**
         * 查询上月订单统计
         */
        Date date=CalendarUtils.addMonthByDate(new Date(),-1) ;
        String upMonth=CalendarUtils.dateToString(date,CalendarUtils.yyyy_MM);

        OrderPieResponse orderPieResponse=orderService.selectOrderPieResponse(upMonth,map);
        if(orderPieResponse==null){
            orderPieResponse=new OrderPieResponse(0);
        }
        model.addAttribute("orderPieResponse",orderPieResponse);
        /**
         * 本周提现流水
         */
        String endTime=CalendarUtils.dateToString(CalendarUtils.getDate(),CalendarUtils.yyyy_MM_dd);//现在日期
        String startTime=CalendarUtils.addDayStrsByDateStr(endTime,-7,CalendarUtils.yyyy_MM_dd);//后7天日期
        List<PlatformWithDrawResponse> drawResponses=withDrawService.selectPlatformWithDrawResponse(startTime,endTime,map);
        model.addAttribute("drawResponses",drawResponses);
        return new ModelAndView("system/main");
    }

    /**
     *
     * @param type 0-上月，1-下月
     * @param oldTime 传入当前月份
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "platformCostCensus")
    public Object platformCostCensus(Integer type,String oldTime,HttpServletRequest request){
        Map<String,Object> map=new HashMap<>();
        Boolean isSuperAdmin=checkingSuperAdministrator(request);
        if(!isSuperAdmin){
            map.put("createId",getLoginUserId(request));
        }
        /**
         * 统计平台月抽成
         */

        ObjectNode json= getDaysAndTime(type,oldTime);

        List<Integer> dayList=new ArrayList<>();//横坐标
        List<Double>  amountList=new ArrayList<>();//每天对应的值

        Integer days=Integer.valueOf(json.get("days").toString());
        String newTime=json.path("newTime").textValue();

        List<PlatformCostCensus> amounts=orderService.selectPlatformCostCensus(newTime,map);
        Map<Integer,Double> amountMap=new HashMap<>();
        for(PlatformCostCensus platformCostCensus:amounts){
            amountMap.put(platformCostCensus.getDateDay(),platformCostCensus.getAmount().doubleValue());
        }
        for(int i=1;i<=days;i++){
            dayList.add(i);
            if(amountMap.containsKey(i)){
                amountList.add(amountMap.get(i)) ;
            }else{
                amountList.add(0d) ;
            }
        }
       json.putPOJO("dayList",dayList);
        json.putPOJO("amountList",amountList);
        return JsonResultUtils.buildJsonOK(json);
    }

    /**
     * 获取要查询的日期和天数
     * @param type
     * @param oldTime
     * @return
     */
    private ObjectNode getDaysAndTime(Integer type,String oldTime){
        StringBuffer newTime=new StringBuffer();
        Integer days;
        ObjectNode json= JsonUtils.getMapperInstance().createObjectNode();
            if(type==0){
               String[] times=oldTime.split("-");
                if(Integer.valueOf(times[1])-1<=0){
                  newTime.append(Integer.valueOf(times[0])-1);
                  newTime.append("-");
                  newTime.append("12");
               }else{
                  newTime.append(times[0]);
                  newTime.append("-");
                  if(Integer.valueOf(times[1])-1<10){
                      newTime.append("0");
                  }
                  newTime.append(Integer.valueOf(times[1])-1);
            }
           }else if(type==1){
               String[] times=oldTime.split("-");
              if(Integer.valueOf(times[1])+1>12){
                  newTime.append(Integer.valueOf(times[0])+1);
                  newTime.append("-");
                  newTime.append("0");
                  newTime.append("1");
               }else{
                  newTime.append(times[0]);
                  newTime.append("-");
                  if(Integer.valueOf(times[1])+1<10){
                      newTime.append("0");
                  }
                  newTime.append(Integer.valueOf(times[1])+1);
            }

        }else{
               newTime.append(CalendarUtils.dateToString(CalendarUtils.getDate(),CalendarUtils.yyyy_MM));
            }
        days=CalendarUtils.getDayOfMonthForDate(CalendarUtils.stringToDate(newTime.toString(),CalendarUtils.yyyy_MM));
        json.put("days",days);
        json.put("newTime",newTime.toString());
        return json;
    }

    /**
     * 商家数量柱状图统计
     * @return
     */
    @ResponseBody
    @GetMapping(value = "histogram")
    public Object histogram(HttpServletRequest request){
        ObjectNode json=JsonUtils.getMapperInstance().createObjectNode();
        Map<String,Object> paramMap=new HashMap<>();
        Boolean isSuperAdmin=checkingSuperAdministrator(request);
        if(!isSuperAdmin){
            paramMap.put("createId",getLoginUserId(request));
        }
        Integer days=CalendarUtils.getDayOfMonthForDate(CalendarUtils.getDate());
        String newDate=CalendarUtils.dateToString(CalendarUtils.getDate(),CalendarUtils.yyyy_MM);
        List<ShopHistogramResponse> shopHistogramResponses=shopService.selectShopHistogramResponse(newDate,paramMap);
        Map<Integer,Integer> map=new HashMap<>();

        List<Integer> dayList=new ArrayList<>();
        List<Integer> shopCountList=new ArrayList<>();

        for(ShopHistogramResponse shopHistogramResponse:shopHistogramResponses){
            map.put(shopHistogramResponse.getDayDate(),shopHistogramResponse.getShopCount());
        }
        for(int i=1;i<=days;i++){
            dayList.add(i);
            if(map.containsKey(i)){
                shopCountList.add(map.get(i));
            }else {
                shopCountList.add(0);
            }
        }
        json.putPOJO("dayList",dayList);
        json.putPOJO("shopCountList",shopCountList);
        return JsonResultUtils.buildJsonOK(json);
    }
}
