package com.yykj.ddkc.api.controller.shop;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.yykj.commons.ServiceConstants;
import com.yykj.ddkc.aop.OperateLogAnnotation;
import com.yykj.ddkc.api.BaseApiController;
import com.yykj.ddkc.api.annotation.ShopLoginRequired;
import com.yykj.ddkc.entity.Order;
import com.yykj.ddkc.response.OrderDetailResponse;
import com.yykj.ddkc.response.ShopOrderResponse;
import com.yykj.ddkc.service.OrderService;
import com.yykj.ddkc.task.DynamicSchedule;
import com.yykj.system.commons.CalendarUtils;
import com.yykj.system.commons.JsonUtils;
import com.yykj.system.commons.NumberUtils;
import com.yykj.system.commons.SystemConstants;
import com.yykj.system.commons.result.JsonResult;
import com.yykj.system.commons.result.JsonResultUtils;
import com.yykj.ddkc.response.OrderProcessResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Api(description = "订单管理")
@RestController(value = "orderControllerByShop")
@RequestMapping(value = "api/shop/order")
public class OrderController extends BaseApiController {
    @Autowired
    OrderService orderService;
    @Autowired
    DynamicSchedule dynamicSchedule;
    /**
     * 查看店家全部订单
     * @param request
     * @return
     */
    @ApiImplicitParam(value = "不传默认全部，1表示待配送；2表示配送中；3表示已完成",name = "status")
    @ApiOperation(value = "全部订单,sumPrice--收入金额",response = ShopOrderResponse.class)
    @GetMapping(value = "index")
    @ShopLoginRequired
    @OperateLogAnnotation(value = "商家查看全部订单")
    public JsonResult index(HttpServletRequest request,Integer status){
        try {
            ObjectNode json= JsonUtils.getMapperInstance().createObjectNode();
            Double sumPrice=0D;
            List<ShopOrderResponse> shopOrderResponses=orderService.selectShopOrderResponse(getShopIdByRequest(request),status,null);
            for(ShopOrderResponse shopOrderResponse:shopOrderResponses){
                if(shopOrderResponse.getStatus()==3){
                    sumPrice+=shopOrderResponse.getTotalPrice().doubleValue();
                }
            }

            json.putPOJO("shopOrderResponses",shopOrderResponses);
            json.put("sumPrice",NumberUtils.scaleNumber(sumPrice));
            return JsonResultUtils.buildJsonOK(json);
        }catch (Exception e){
            e.printStackTrace();
            return JsonResultUtils.buildJsonFailMsg(e.getMessage());
        }
    }



    /**
     * 订单详情
     * @param orderId
     * @return
     */
    @ApiOperation(value = "订单详情",response = OrderDetailResponse.class)
    @GetMapping(value = "orderDetail")
    @ShopLoginRequired
    @ApiImplicitParam(value = "订单ID",name="orderId")
    @OperateLogAnnotation(value = "商家查看订单详情")
    public JsonResult orderDetail(@RequestParam Integer orderId){
        try {
            OrderDetailResponse orderDetailResponse=orderService.selectOrderDetailResponseByOrderId(orderId);
            if(orderDetailResponse==null){
                orderDetailResponse =new OrderDetailResponse();
            }
            return JsonResultUtils.buildJsonOK(orderDetailResponse);
        }catch (Exception e){
            e.printStackTrace();
            return JsonResultUtils.buildJsonFailMsg(e.getMessage());
        }
    }

    /**
     * 商家点击接单
     * @return
     */
    @GetMapping(value = "receipt")
    @ShopLoginRequired
    @ApiImplicitParam(value = "订单ID",name = "orderId")
    @ApiOperation(value = "商家点击接单")
    @OperateLogAnnotation(value = "商家点击接单")
    public JsonResult receipt(@RequestParam Integer orderId){
        try {
            Order order=new Order();
            order.setId(orderId);
            order.setReceiptTime(CalendarUtils.getDate());
            order.setStatus(2);
            orderService.updateById(order);
            //订单默认完成监控
            dynamicSchedule.executeForOrserFinishStatus(order.getId(), ServiceConstants.ORDER_FINISH_TIME_EXPIRE);
            return JsonResultUtils.buildJsonOK();
        }catch (Exception e){
            e.printStackTrace();
            return JsonResultUtils.buildJsonFailMsg(e.getMessage());
        }
    }
}
