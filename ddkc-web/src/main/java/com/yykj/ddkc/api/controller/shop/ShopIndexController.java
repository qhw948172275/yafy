package com.yykj.ddkc.api.controller.shop;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.yykj.ddkc.aop.OperateLogAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yykj.ddkc.api.BaseApiController;
import com.yykj.ddkc.api.annotation.ShopLoginRequired;
import com.yykj.ddkc.entity.Goods;
import com.yykj.ddkc.entity.SearchRecord;
import com.yykj.ddkc.entity.Shop;
import com.yykj.ddkc.entity.ShopCategory;
import com.yykj.ddkc.service.GoodsService;
import com.yykj.ddkc.service.SearchRecordService;
import com.yykj.ddkc.service.ShopCategoryService;
import com.yykj.ddkc.service.ShopService;
import com.yykj.system.commons.CalendarUtils;
import com.yykj.system.commons.StringUtils;
import com.yykj.system.commons.result.JsonResult;
import com.yykj.system.commons.result.JsonResultUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(description = "商铺首页管理")
@RestController
@RequestMapping(value = "api/shop")
public class ShopIndexController extends BaseApiController {
    @Autowired
    ShopService shopService;
    @Autowired
    ShopCategoryService shopCategoryService;
    @Autowired
    GoodsService goodsService;
    @Autowired
    SearchRecordService searchRecordService;
    /**
     * 获取店铺信息
     * @param request
     * @return
     */
    @ApiOperation(value = "获取店铺信息",response = Shop.class)
    @GetMapping(value = "index")
    @ShopLoginRequired
    @OperateLogAnnotation(value = "进入店铺主页")
    public JsonResult index(HttpServletRequest request){
        try {
            Shop shop=shopService.getById(getShopIdByRequest(request));
            return JsonResultUtils.buildJsonOK(shop);
        }catch (Exception e){
            e.printStackTrace();
            return JsonResultUtils.buildJsonFailMsg(e.getMessage());
        }
    }

    /**
     * 获取类别列表
     * @param request
     * @return
     */
    @ApiOperation(value = "类别列表",response = ShopCategory.class)
    @GetMapping(value = "categoryList")
    @ShopLoginRequired
    public JsonResult categoryList(HttpServletRequest request){
        try {
            List<ShopCategory> shopCategoryList=shopCategoryService.selectByShopId(getShopIdByRequest(request));
            return JsonResultUtils.buildJsonOK(shopCategoryList);
        }catch (Exception e){
            e.printStackTrace();
            return JsonResultUtils.buildJsonFailMsg(e.getMessage());
        }
    }

    /**
     * 根据商品分类ID获取商品列表
     * @param shopCategoryId
     * @return
     */
    @ApiImplicitParam(value = "商品分类ID",name="shopCategoryId")
    @ApiOperation(value = "商品列表",response =Goods.class)
    @GetMapping(value = "goodsList")
    @ShopLoginRequired
    public JsonResult goodsList(@RequestParam Integer shopCategoryId,HttpServletRequest request){
        try {
            if(shopCategoryId==null){
                return JsonResultUtils.buildJsonOK();
            }
            List<Goods> goodsList=goodsService.selectByShopCategoryId(shopCategoryId,getShopIdByRequest(request));
            return JsonResultUtils.buildJsonOK(goodsList);
        }catch (Exception e){
            e.printStackTrace();
            return JsonResultUtils.buildJsonFailMsg(e.getMessage());
        }
    }

    /**
     * 根据商铺ID查询搜索记录
     * @return
     */
    @ApiOperation(value = "搜索历史",response = SearchRecord.class)
    @GetMapping(value = "searchRecord")
    @ShopLoginRequired
    public JsonResult searchRecord(HttpServletRequest request){
        try {
            List<SearchRecord> searchRecordList=searchRecordService.selectByShopId(getShopIdByRequest(request));
            return JsonResultUtils.buildJsonOK(searchRecordList);
        }catch (Exception e){
            e.printStackTrace();
            return JsonResultUtils.buildJsonFailMsg(e.getMessage());
        }
    }

    /**
     *清除商家搜索记录
     * @return
     */
    @ApiOperation(value = "清除商家搜索记录")
    @GetMapping(value = "delSearchRecord")
    @ShopLoginRequired
    @OperateLogAnnotation(value = "清除搜索记录")
    public JsonResult delSearchRecord(HttpServletRequest request){
        try {
            SearchRecord searchRecord=new SearchRecord();
            searchRecord.setStatus(1);
            searchRecord.setShopId(getShopIdByRequest(request));
            searchRecordService.delSearchRecord(searchRecord);
            return JsonResultUtils.buildJsonOK();
        }catch (Exception e){
            e.printStackTrace();
            return JsonResultUtils.buildJsonFailMsg(e.getMessage());
        }
    }

    /**
     * 关键字搜索本店铺的商品
     * @param keyword
     * @return
     */
    @ApiImplicitParam(value = "关键字",name="keyword")
    @ApiOperation(value = "关键字搜索本店铺的商品",response =Goods.class )
    @GetMapping(value = "searchResult")
    @ShopLoginRequired
    @OperateLogAnnotation(value = "搜索本店铺的商品")
    public JsonResult searchResult(HttpServletRequest request,String keyword){
        try {
            List<Goods> goodsList=goodsService.selectByKeyword(keyword,getShopIdByRequest(request));
            if(StringUtils.isNotEmpty(keyword)){//更新搜索历史
                SearchRecord searchRecord=new SearchRecord();
                searchRecord.setShopId(getShopIdByRequest(request));
                searchRecord.setContent(keyword);
                searchRecord.setStatus(0);
                //先禁用同搜索内容的记录在插入新的
                searchRecordService.delByContent(keyword,getShopIdByRequest(request));
                searchRecordService.insert(searchRecord);
            }

            return JsonResultUtils.buildJsonOK(goodsList);
        }catch (Exception e){
            e.printStackTrace();
            return JsonResultUtils.buildJsonFailMsg(e.getMessage());
        }
    }

    /**
     * 保存新增分类
     * @param shopCategory
     * @return
     */
    @PostMapping(value = "addCategory")
    @ApiOperation(value = "新增保存分类-不传ID，編輯保存分类-传ID")
    @ShopLoginRequired
    @OperateLogAnnotation(value = "保存新增商品分类")
    public JsonResult addCategory(@RequestBody ShopCategory shopCategory,HttpServletRequest request){
        try {
            if(StringUtils.isEmpty(shopCategory.getName())){
                return JsonResultUtils.buildJsonFailMsg("分类名称不能为空");
            }
            if(shopCategory.getId()==null){
                if(shopCategoryService.selectByShopIdAndName(shopCategory.getName(),getShopIdByRequest(request),0,null).size()>0){
                    return JsonResultUtils.buildJsonFailMsg("该分类商品已经存在");
                }
                shopCategory.setShopId(getShopIdByRequest(request));
                shopCategory.setStatus(0);
                shopCategory.setCreateTime(CalendarUtils.getDate());
                shopCategoryService.insert(shopCategory);
            }else{
                if(shopCategoryService.selectByShopIdAndName(shopCategory.getName(),getShopIdByRequest(request),0,shopCategory.getId()).size()>0){
                    return JsonResultUtils.buildJsonFailMsg("该分类商品已经存在");
                }

                shopCategoryService.updateById(shopCategory);
            }
            return JsonResultUtils.buildJsonOK();
        }catch (Exception e){
            e.printStackTrace();
            return JsonResultUtils.buildJsonFailMsg(e.getMessage());
        }
    }

    /**
     * 删除商品分类及下面的商品
     * @param categoryIdStr
     * @return
     */
    @ApiImplicitParam(value = "分类ID集合,多个以 , 连接",name = "categoryIdStr")
    @ApiOperation(value = "删除商品分类及下面的商品")
    @GetMapping(value = "delCategory")
    @ShopLoginRequired
    @OperateLogAnnotation(value = "删除商品分类及下面的商品")
    public JsonResult delCategory(@RequestParam String categoryIdStr,HttpServletRequest request){
        try {
            List<Integer> categoryIdList=new ArrayList<>();
            if(StringUtils.isNotEmpty(categoryIdStr)){
              String[] categorys=categoryIdStr.split(",");

              for(int i=0;i<categorys.length;i++){
                  categoryIdList.add(Integer.valueOf(categorys[i]));
              }
            }else{
                return JsonResultUtils.buildJsonFailMsg("分类ID为空");
            }
            ShopCategory shopCategory=new ShopCategory();
            shopCategory.setStatus(1);
            shopCategoryService.updateByIds(categoryIdList,shopCategory);
            //删除该分类的商品
            goodsService.updateByCategoryId(getShopIdByRequest(request),categoryIdList);
            return JsonResultUtils.buildJsonOK();
        }catch (Exception e){
            e.printStackTrace();
            return JsonResultUtils.buildJsonFailMsg(e.getMessage());
        }
    }


    /**
     * 保存新增加的商品
     * @param goods
     * @param request
     * @return
     */
    @ApiOperation(value = "新增保存加的商品--不传ID,编辑后保存-传ID")
    @PostMapping(value = "addGoods")
    @ShopLoginRequired
    @OperateLogAnnotation(value = "添加商品")
    public JsonResult addGoods(@RequestBody Goods goods,HttpServletRequest request){
        try {
            if(goods.getId()==null||goods.getId()<=0){
                if(goodsService.selectByShopIdAndName(goods.getGoodsName(),getShopIdByRequest(request),null).size()>0){
                    return JsonResultUtils.buildJsonFailMsg("该商品名称已存在");
                }
                goods.setStatus(0);
                goods.setCreateTime(CalendarUtils.getDate());
                goods.setShopId(getShopIdByRequest(request));
                goodsService.insert(goods);
            }else{
                if(goodsService.selectByShopIdAndName(goods.getGoodsName(),getShopIdByRequest(request),goods.getId()).size()>0){
                    return JsonResultUtils.buildJsonFailMsg("该商品名称已存在");
                }
                goods.setUpateTime(CalendarUtils.getDate());
                goodsService.updateById(goods);
            }
            return JsonResultUtils.buildJsonOK();
        }catch (Exception e){
            e.printStackTrace();
            return JsonResultUtils.buildJsonFailMsg(e.getMessage());
        }
    }

    /**
     * 编辑商品信息
     * @param goodsId
     * @return
     */
    @ApiOperation(value = "编辑商品信息",response = Goods.class)
    @ApiImplicitParam(value = "商品ID",name = "goodsId")
    @ShopLoginRequired
    @GetMapping(value = "goodsEdit")
    @OperateLogAnnotation(value = "编辑商品信息")
    public JsonResult goodsEdit(@RequestParam Integer goodsId){
        try {
                Goods goods=goodsService.getById(goodsId);
            return JsonResultUtils.buildJsonOK(goods);
        }catch (Exception e){
            e.printStackTrace();
            return JsonResultUtils.buildJsonFailMsg(e.getMessage());
        }
    }

    /**
     * 商品上下架
     * @param goodsId
     * @param status
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(value = "商品ID",name = "goodsId"),
            @ApiImplicitParam(value = "0-上架，1-下架",name = "status")
    })
    @ApiOperation(value = "商品上下架")
    @GetMapping(value = "changeGoods")
    @ShopLoginRequired
    @OperateLogAnnotation(value = "商品上下架")
    public JsonResult changeGoods(@RequestParam Integer goodsId,@RequestParam Integer status){
            try {
                Goods goods=new Goods();
                goods.setId(goodsId);
                goods.setStatus(status);
                goodsService.updateById(goods);
                return JsonResultUtils.buildJsonOK();
            }catch (Exception e){
                e.printStackTrace();
                return JsonResultUtils.buildJsonFailMsg(e.getMessage());
            }
    }



}
