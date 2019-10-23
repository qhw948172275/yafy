package com.yykj.ddkc.platform;

import com.yykj.ddkc.entity.ShopCategory;
import com.yykj.ddkc.service.ShopCategoryService;
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
import java.util.Map;

@Controller
@RequestMapping(value = "platform/shopCategory")
public class ShopCategoryController extends BaseController {
    @Autowired
    ShopCategoryService shopCategoryService;

    @GetMapping(value = "")
    public String index(Integer shopId, Model model){
        model.addAttribute("shopId",shopId);
        return "ddkc/platform/shopCategory/index";
    }

    /**
     * 记载商品分类
     * @param request
     * @param keyword
     * @return
     */
    @GetMapping(value = "",params = "ajaxLoad")
    @ResponseBody
    public Object ajaxLoad(HttpServletRequest request,String keyword,Integer shopId){
        Map<String, Object> map = getMapByExample(request);
        if(StringUtils.isNotEmpty(keyword)){
            map.put("keyword",keyword);
        }
        map.put("shopId",shopId);
        return  buildPageResultByAsync(shopCategoryService.selectAllPage(map));
    }

    /**
     * 更改商品分类状态
     * @param shopCosId
     * @param status
     * @return
     */
    @ResponseBody
    @GetMapping(value = "changStatus")
    public JsonResult changStatus(Integer shopCosId,Integer status){
        try {
            ShopCategory shopCategory=new ShopCategory();
            shopCategory.setId(shopCosId);
            shopCategory.setStatus(status);
            shopCategoryService.updateById(shopCategory);
            return JsonResultUtils.buildJsonOK();
        }catch (Exception e){
            e.printStackTrace();
        }
        return JsonResultUtils.buildJsonFail();
    }
}
