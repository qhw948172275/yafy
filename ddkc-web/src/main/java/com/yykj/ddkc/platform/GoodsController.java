package com.yykj.ddkc.platform;

import com.github.pagehelper.PageInfo;
import com.yykj.ddkc.entity.Goods;
import com.yykj.ddkc.entity.Member;
import com.yykj.ddkc.response.GoodsResponse;
import com.yykj.ddkc.service.GoodsService;
import com.yykj.system.commons.StringUtils;
import com.yykj.system.commons.result.JsonResult;
import com.yykj.system.commons.result.JsonResultUtils;
import com.yykj.system.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "platform/goods")
public class GoodsController extends BaseController {
    @Autowired
    GoodsService goodsService;

    @GetMapping(value = "")
    public String index(Integer shopId, Model model){
        if(shopId!=null){
            model.addAttribute("shopId",shopId);
        }else{
            model.addAttribute("shopId",-1);
        }
        return "ddkc/platform/goods/index";
    }

    /**
     * 展示商品数据
     * @param request
     * @param shopId
     * @param goodsName
     * @param shopName
     * @return
     */
    @GetMapping(value = "",params = "ajaxLoad")
    @ResponseBody
    public Object ajaxLoad(HttpServletRequest request,Integer shopId,String goodsName,String shopName){
        Map<String, Object> map = getMapByExample(request);
        Boolean isSuperAdmin=checkingSuperAdministrator(request);
        if(!isSuperAdmin){
           map.put("createId",getLoginUserId(request));
        }
        if(StringUtils.isNotEmpty(goodsName)){
            map.put("goodsName",goodsName);
        }
        if(StringUtils.isNotEmpty(shopName)){
            map.put("shopName",shopName);
        }
        if(shopId!=-1){
            map.put("shopId",shopId);
        }

        return  buildPageResultByAsync(goodsService.selectAllPagefor(map));
    }

    /**
     * 更改商品状态
     * @param goodsId
     * @param status
     * @return
     */
    @GetMapping(value = "changStatus")
    @ResponseBody
    public JsonResult changStatus(Integer goodsId,Integer status){
        try {

            Goods goods=new Goods();
            goods.setId(goodsId);
            goods.setStatus(status);
            goodsService.updateById(goods);
            return JsonResultUtils.buildJsonOK();
        }catch (Exception e){
            e.printStackTrace();
        }
        return JsonResultUtils.buildJsonFail();
    }

    /**
     * 图片详情
     * @param goodsId
     * @param model
     * @return
     */
    @GetMapping(value = "detail")
   public String detail(Integer goodsId,Model model){
        Map<String,Object> map=new HashMap<>();
        map.put("goodsId",goodsId);
        PageInfo<GoodsResponse> goodsResponsePage=goodsService.selectAllPagefor(map);
        List<GoodsResponse> goodsResponses=goodsResponsePage.getList();
        model.addAttribute("goodsResponse",goodsResponses.get(0));
        return "ddkc/platform/goods/info";
   }
}
