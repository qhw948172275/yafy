package com.yykj.ddkc.api.controller.shop;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.yykj.commons.HTTPUtils;
import com.yykj.commons.ServiceConstants;
import com.yykj.commons.XmlUtil;
import com.yykj.commons.wechat.AppConfig;
import com.yykj.commons.wechat.WXPayCore;
import com.yykj.ddkc.aop.OperateLogAnnotation;
import com.yykj.ddkc.api.BaseApiController;
import com.yykj.ddkc.api.annotation.ShopLoginRequired;
import com.yykj.ddkc.entity.Shop;
import com.yykj.ddkc.entity.ShopAccount;
import com.yykj.ddkc.entity.WithDraw;
import com.yykj.ddkc.response.OrderGoodsResponse;
import com.yykj.ddkc.response.OrderLogResponse;
import com.yykj.ddkc.service.OrderService;
import com.yykj.ddkc.service.ShopAccountService;
import com.yykj.ddkc.service.ShopService;
import com.yykj.ddkc.service.WithDrawService;
import com.yykj.system.commons.CalendarUtils;
import com.yykj.system.commons.JsonUtils;
import com.yykj.system.commons.NumberUtils;
import com.yykj.system.commons.RandomUtils;
import com.yykj.system.commons.result.JsonResult;
import com.yykj.system.commons.result.JsonResultUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(description = "商店信息管理")
@RestController
@RequestMapping(value = "api/shop/center")
public class ShopCenterController extends BaseApiController {
    @Autowired
    ShopService shopService;
    @Autowired
    AppConfig appConfig;
    @Autowired
    WithDrawService withDrawService;
    @Autowired
    OrderService orderService;
    
    
    @Autowired
    ShopAccountService shopAccountService;

    @ApiOperation(value = "获取商铺信息",response = Shop.class)
    @GetMapping(value = "getShopInfo")
    @ShopLoginRequired
    public JsonResult getShopInfo(HttpServletRequest request){
        try {
            Shop shop=shopService.getById(getShopIdByRequest(request));
            return JsonResultUtils.buildJsonOK(shop);
        }catch (Exception e){
            e.printStackTrace();
            return JsonResultUtils.buildJsonFailMsg(e.getMessage());
        }
    }

    /**
     * 修改M密码
     * @param request
     * @param oldPassword
     * @param newPassword
     * @return
     */
    @ShopLoginRequired
    @ApiOperation(value = "修改密码")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "旧密码",name = "oldPassword"),
            @ApiImplicitParam(value = "新密码",name = "newPassword")
    })
    @GetMapping(value = "modifyPassword")
    @OperateLogAnnotation(value = "商家修改密码")
    public JsonResult modifyPassword(HttpServletRequest request,@RequestParam String oldPassword,@RequestParam String newPassword){
            try {
            	String token=getToken(request,"shopToken");
                String shopAccountId = redisUtils.getStringForKey(ServiceConstants.SHOP_ACCOUNT_TOKEN_KEY+token);
                ShopAccount account = shopAccountService.getById(Integer.parseInt(shopAccountId));
                if(!account.getPassword().equals(oldPassword)) {
                	 return JsonResultUtils.buildJsonFail("原密码不正确");
                }
                account.setPassword(newPassword);
                shopAccountService.updateById(account);
                return JsonResultUtils.buildJsonOK();
            }catch (Exception e){
                e.printStackTrace();
                return JsonResultUtils.buildJsonFailMsg(e.getMessage());
            }
    }

    /**
     * 查询商家能提现的金额
     * @return
     */
    @ApiOperation(value = "查询商家能提现的金额")
    @GetMapping(value = "balance")
    @ShopLoginRequired
    public JsonResult balance(HttpServletRequest request){
        try {
            Shop shop=shopService.getById(getShopIdByRequest(request));
            BigDecimal balance= shop.getTotalAmount();
            ObjectNode json =JsonUtils.getMapperInstance().createObjectNode();
            json.putPOJO("balance",balance);
            json.putPOJO("soBalance",shop.getSoBalance());
            return JsonResultUtils.buildJsonOK(json);
        }catch (Exception e){
            e.printStackTrace();
            return JsonResultUtils.buildJsonFailMsg(e.getMessage());
        }
    }


    @ApiOperation(value = "商家提现")
    @PostMapping(value = "cashOut")
    @ShopLoginRequired
    @ApiImplicitParams({
            @ApiImplicitParam(name = "amount", value = "提现金额")
    })
    @OperateLogAnnotation(value = "商家提现")
    public JsonResult cashOut(@RequestParam Double amount,HttpServletRequest request){
        try {
            Shop shop=shopService.getById(getShopIdByRequest(request));
            if(shop.getStatus()==1){
                return JsonResultUtils.buildJsonFailMsg("该账号已锁定不能提现");
            }
            String transNumber=RandomUtils.getOrderNumber();//生成交易号
            WithDraw withDraw=new WithDraw();
            withDraw.setCreateTime(CalendarUtils.getDate());
            withDraw.setWithDrawPrice(BigDecimal.valueOf(amount));
            withDraw.setTransNumber(transNumber);
            withDraw.setStatus(0);//提现中
            withDraw.setShopId(getShopIdByRequest(request));
            withDraw.setOpenid(shop.getOpenid());
            withDraw.setWechatChargePrice(withDraw.getWithDrawPrice().multiply(new BigDecimal("0.006")));//计算手续费
            BigDecimal amountCal=new BigDecimal(amount);

            if(shop.getTotalAmount().compareTo(amountCal)==-1){
                return JsonResultUtils.buildJsonFail("提现金额过大");
            }
            Map<String,String> parm=new HashMap<>();
            String amountStr= String.valueOf(NumberUtils.doubleMultiply(amount,100,0));
            String amountaa=amountStr.substring(0,amountStr.length()-2);
            parm.put("mch_appid", appConfig.getAppid()); //公众账号appid
            parm.put("mchid", appConfig.getMerchantId()); //商户号
            parm.put("nonce_str", RandomUtils.getRandomCharAndNumr(8)); //随机字符串
            parm.put("partner_trade_no", transNumber); //商户订单号
            parm.put("openid", shop.getOpenid()); //用户openid
            parm.put("check_name", "NO_CHECK"); //校验用户姓名选项 OPTION_CHECK
            parm.put("amount",amountaa ); //转账金额
            parm.put("desc","提现" ); //企业付款描述信息
            parm.put("spbill_create_ip", ServiceConstants.MINIPROGRAM_SPILL_CREATE_IP); //Ip地址
            parm.put("sign",  WXPayCore.buildRequestMysign(WXPayCore.createLinkString(parm),appConfig.getPayKey()));


            String notifyContent = HTTPUtils.doRefund(ServiceConstants.SHOP_TRANSFERS,WXPayCore.mapToXml(parm),appConfig.getP12(),appConfig.getMerchantId());

           Map<String,String> refundResult= XmlUtil.xmlToMap(notifyContent);
            if("SUCCESS".equalsIgnoreCase(refundResult.get("result_code")) && "SUCCESS".equalsIgnoreCase(refundResult.get("return_code"))){

                withDraw.setStatus(1);//转账成功
                //成功到帐时间
                withDraw.setSuccessTime(CalendarUtils.stringToDate(refundResult.get("payment_time"),CalendarUtils.yyyy_MM_dd__HH_mm_ss));
                shop.setTotalAmount(shop.getTotalAmount().subtract(withDraw.getWithDrawPrice()));
                //更新可见余额
                shop.setSoBalance(shop.getSoBalance().subtract(withDraw.getWithDrawPrice()));

                shopService.updateById(shop);
            }else{
                withDraw.setStatus(2);//转账失败
                withDraw.setFailMsg(refundResult.get("return_msg"));//失败原因

            }
            withDrawService.insert(withDraw);
            return JsonResultUtils.buildJsonOK(withDraw);
        }catch (Exception e){
            e.printStackTrace();
            return JsonResultUtils.buildJsonFailMsg(e.getMessage());
        }
    }

    /**
     * 商家提现记录
     * @return
     */
    @ApiOperation(value = "商家提现记录")
    @ShopLoginRequired
    @ApiImplicitParams({    	
    	@ApiImplicitParam(value = "查询提现日期,如：2019-09-09",name = "cashOutDate")
    })
    @GetMapping(value = "cashOutLog")
    @OperateLogAnnotation(value = "商家查看提现记录")
    public JsonResult cashOutLog(String cashOutDate,HttpServletRequest request){
        try {

            List<WithDraw> withDraws=withDrawService.selectByDate(cashOutDate,getShopIdByRequest(request));
            return JsonResultUtils.buildJsonOK(withDraws);
        }catch (Exception e){
            e.printStackTrace();
            return JsonResultUtils.buildJsonFailMsg(e.getMessage());
        }
    }

    /**
     * 收入管理
     * @return
     */
    @ShopLoginRequired
    @ApiOperation(value = "收入管理")
    @GetMapping(value = "orderLog")
    @ApiImplicitParams({   
    	@ApiImplicitParam(value = "日期:2019-09-09")
    })
    @OperateLogAnnotation(value = "商家进入收入管理")
    public JsonResult orderLog(String orderLogDate,HttpServletRequest request){
        try {
            //统计订单
            OrderLogResponse orderLogResponse=orderService.selectByDate(orderLogDate,getShopIdByRequest(request));
            //查询订单详情
            List<OrderGoodsResponse> orderGoodsResponses=orderService.selectGoodsByDateOrder(orderLogDate,getShopIdByRequest(request));
            orderLogResponse.setOrderGoodsResponses(orderGoodsResponses);
            return JsonResultUtils.buildJsonOK(orderLogResponse);
        }catch (Exception e){
            e.printStackTrace();
            return JsonResultUtils.buildJsonFailMsg(e.getMessage());
        }
    }

    /**
     * 更新商家信息
     * @return
     */
    @PostMapping(value = "updateShop")
    @ShopLoginRequired
    @ApiOperation(value = "更新商家信息")
    public JsonResult updateShop(@RequestBody Shop shop){
        try {
            if(shop.getId()==null){
                return JsonResultUtils.buildJsonFailMsg("店铺ID不能为空");
            }
            shopService.updateById(shop);
            return JsonResultUtils.buildJsonOK();
        }catch (Exception e){
            e.printStackTrace();
            return JsonResultUtils.buildJsonFailMsg(e.getMessage());
        }
    }
}
