package com.yykj.ddkc.platform;

import com.yykj.commons.wechat.Wxaqrcode;
import com.yykj.ddkc.entity.Shop;
import com.yykj.ddkc.entity.ShopAccount;
import com.yykj.ddkc.service.ShopAccountService;
import com.yykj.ddkc.service.ShopService;
import com.yykj.system.commons.CalendarUtils;
import com.yykj.system.commons.MD5Password;
import com.yykj.system.commons.StringUtils;
import com.yykj.system.commons.SystemConstants;
import com.yykj.system.commons.result.JsonResult;
import com.yykj.system.commons.result.JsonResultUtils;
import com.yykj.system.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping(value = "platform/shop")
public class ShopController extends BaseController {
    @Autowired
    ShopService shopService;
    @Autowired
    Wxaqrcode wxaqrcode;
    
    @Autowired
    ShopAccountService shopAccountService;

    @GetMapping(value = "")
    public String index(){
        return "ddkc/platform/shop/index";
    }

    /**
     *获取商家列表数据
     * @return
     */
    @GetMapping(value = "",params = "ajaxLoad")
    @ResponseBody
    public Object ajaxLoad(HttpServletRequest request,String keyword) {
        Map<String, Object> map = getMapByExample(request);
          if(StringUtils.isNotEmpty(keyword)){
              map.put("keyword",keyword);
          }
        Boolean isSuperAdmin=checkingSuperAdministrator(request);
        if(!isSuperAdmin){
            map.put("createId",getLoginUserId(request));
        }
      return   buildPageResultByAsync(shopService.selectAllPage(map));
    }

    /**
     * 跳转商铺添加页面
     * @return
     */
    @GetMapping(value = "add")
    public String add(){
        return "ddkc/platform/shop/info";
    }

    /**
     * 保存商铺信息
     * @param shop
     * @param request
     * @return
     */
    @PostMapping(value = "save")
    @ResponseBody
    public JsonResult save(Shop shop,HttpServletRequest request){
        try {
            if(shop.getId()==null){
                shop.setCreateId(getLoginUserId(request));
                shop.setCreator(getLoginUser(request).getUser().getRealName());
                shop.setCreateTime(CalendarUtils.getDate());
                shop.setBussStartTime("00:00");
                shop.setBussEndTime("24:00");
                shop.setPassword(MD5Password.encryptPassword("12345678"));
                shopService.insert(shop);
                shop.setWxaqrcode(wxaqrcode.getFileName(shop.getId()));//设置店铺二维码
                shopService.updateById(shop);
                //添加商户账号
                ShopAccount account = new ShopAccount();
                account.setName(shop.getContact());
                account.setAccount(shop.getPhone());
                account.setCreateTime(CalendarUtils.getDate());
                account.setCreatorId(getLoginUserId(request));
                account.setIsMaster(SystemConstants.STATUS_NO);
                account.setIsNotice(SystemConstants.STATUS_NO);
                account.setPassword(shop.getPhone());
                account.setStatus(SystemConstants.STATUS_OK);
                account.setShopId(shop.getId());
                shopAccountService.insert(account);
            }else {
                if(StringUtils.isEmpty(shop.getWxaqrcode())){
                    shop.setWxaqrcode(wxaqrcode.getFileName(shop.getId()));//设置店铺二维码
                }
               shopService.updateById(shop);
            }
            return JsonResultUtils.buildJsonOK();
        }catch (Exception e){
            e.printStackTrace();
            return JsonResultUtils.buildJsonFailMsg(e.getMessage());
        }

    }
    /**
     * 编辑商铺
     * @param shopId
     * @param model
     * @return
     */
    @GetMapping(value = "edit")
    public String edit(Integer shopId,Model model){
        Shop shop=shopService.getById(shopId);
        model.addAttribute("shop",shop);
        return "ddkc/platform/shop/info";
    }

    /**
     * 更改商铺状态
     * @param status
     * @param shopId
     * @return
     */
    @GetMapping(value = "changStatus")
    @ResponseBody
    public JsonResult changStatus(Integer status,Integer shopId){
        try {
            Shop shop=new Shop();
            shop.setId(shopId);
            shop.setStatus(status);
            shopService.updateById(shop);
            return JsonResultUtils.buildJsonOK();
        }catch (Exception e){
            e.printStackTrace();
        }
        return JsonResultUtils.buildJsonFail();
    }

    /**
     * 删除商铺
     * @param shopId
     * @return
     */
    @GetMapping(value = "delShop")
    @ResponseBody
    public JsonResult delShop(@RequestParam Integer shopId){
        try {
            Shop shop=new Shop();
            shop.setId(shopId);
            shop.setStatus(2);//删除
            shopService.updateById(shop);
            return JsonResultUtils.buildJsonOK();
        }catch (Exception e){
            e.printStackTrace();
            return JsonResultUtils.buildJsonFailMsg(e.getMessage());
        }
    }

}
