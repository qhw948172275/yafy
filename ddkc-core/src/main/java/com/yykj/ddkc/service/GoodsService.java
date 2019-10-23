package com.yykj.ddkc.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yykj.ddkc.dao.GoodsMapper;
import com.yykj.ddkc.dao.GoodsSearchRecordMapper;
import com.yykj.ddkc.entity.Goods;
import com.yykj.ddkc.entity.GoodsSearchRecord;
import com.yykj.ddkc.response.GoodsResponse;
import com.yykj.ddkc.response.GoodsSearchRecordResponse;
import com.yykj.system.commons.StringUtils;
import com.yykj.system.commons.service.impl.AbstractBaseCrudService;

import tk.mybatis.mapper.entity.Example;

@Service
public class GoodsService extends AbstractBaseCrudService<Goods,Integer> {

    @Autowired
    GoodsMapper goodsMapper;
    
    @Autowired
    GoodsSearchRecordMapper goodsSearchRecordMapper;

    public PageInfo<GoodsResponse> selectAllPagefor(Map<String, Object> map) throws RuntimeException {
        if(map.containsKey("pageNumber")){
            PageHelper.startPage(Integer.valueOf(String.valueOf(map.get("pageNumber"))), Integer.valueOf(String.valueOf(map.get("pageSize"))));
        }
        String goodsName= map.containsKey("goodsName")?"%"+map.get("goodsName")+"%":null;
        String shopName=map.containsKey("shopName")?"%"+map.get("shopName")+"%":null;
        Integer shopId=map.containsKey("shopId")?Integer.valueOf(String.valueOf(map.get("shopId"))):null;
        Integer goodsId=map.containsKey("goodsId")?Integer.valueOf(String.valueOf(map.get("goodsId"))):null;
        Integer createId=map.containsKey("createId")?(Integer) map.get("createId"):null;
        List<GoodsResponse> list= goodsMapper.selectAllPage(goodsName,shopName,shopId,goodsId,createId);
        return new PageInfo<>(list);
    }

    /**
     * 根据商品分类ID获取商品列表
     * @param shopCategoryId
     * @param shopId
     * @return
     */
    public List<Goods> selectByShopCategoryId(Integer shopCategoryId,Integer shopId){
        Example example=new Example(tClass);
        Example.Criteria criteria=example.createCriteria();
        criteria.andEqualTo("shopId",shopId).andEqualTo("categoryId",shopCategoryId);
        criteria.andNotEqualTo("status",2);//2表示删除
        return mapper.selectByExample(example);
    }
    /**
     * 根据分类ID+商户ID分页展示商品信息
    * @author chenbiao
    * @date 2019年8月14日 下午6:30:51
    * 参数说明 
    * 
    * @param shopCategoryId
    * @param shopId
    * @param page
    * @param limit
    * @return
     */
    public PageInfo<Goods> selectByPageByShopCategoryId(Integer shopId,Integer shopCategoryId,Integer page,Integer limit){
    	PageHelper.startPage(page, limit);
        Example example=new Example(tClass);
        Example.Criteria criteria=example.createCriteria();
        criteria.andEqualTo("shopId",shopId).andEqualTo("categoryId",shopCategoryId);
        criteria.andEqualTo("status",0);
        example.setOrderByClause("id desc");
        List<Goods> goods = mapper.selectByExample(example);
        return new PageInfo<Goods>(goods);
    }

    /**
     * 通过关键字搜索指定商家的商品
     * @param keyword
     * @param shopId
     * @return
     */
    public List<Goods> selectByKeyword(String keyword,Integer shopId) {
        Example example=new Example(tClass);
        Example.Criteria criteria=example.createCriteria();
        criteria.andEqualTo("shopId",shopId);
        criteria.andEqualTo("status",0);
        if(StringUtils.isNotEmpty(keyword)){
            Example.Criteria criteria1=example.and();
            criteria1.andLike("goodsName","%"+keyword+"%");
        }
        return mapper.selectByExample(example);
    }

    /**
     * 根据商品名称和店铺ID查询
     * @param goodsName
     * @param shopId
     * @return
     */
    public List<Goods> selectByShopIdAndName(String goodsName, Integer shopId,Integer goodsId) {
        Example example=new Example(tClass);
        Example.Criteria criteria=example.createCriteria();
        criteria.andEqualTo("goodsName",goodsName).andEqualTo("shopId",shopId);
        criteria.andNotEqualTo("status",2);//2表示删除
        if(goodsId!=null){
            criteria.andNotEqualTo("id",goodsId);
        }
        return mapper.selectByExample(example);

    }

    /**
     * 删除
     * @param shopId
     * @param categoryIds
     */
    public void updateByCategoryId(Integer shopId,List<Integer> categoryIds) {
        Example example=new Example(tClass);
        Example.Criteria criteria=example.createCriteria();
        criteria.andEqualTo("shopId",shopId).andIn("categoryId",categoryIds);
        Goods  goods=new Goods();
        goods.setStatus(2);
        mapper.updateByExampleSelective(goods,example);
    }
    
    /**
     * 添加前端用户店铺搜索商品记录
    * @author chenbiao
    * @date 2019年8月15日 下午4:50:15
    * 参数说明 
    * 
    * @param record
    * @return
     */
    public boolean addGoodsSearchRecord(GoodsSearchRecord record) {
    	return goodsSearchRecordMapper.insertSelective(record) > 0;
    }
    /**
     * 获取前10个指定商铺热门搜索关键词信息
    * @author chenbiao
    * @date 2019年8月15日 下午5:09:49
    * 参数说明 
    * 
    * @param shopId
    * @return
     */
    public List<GoodsSearchRecordResponse> selectHotKeyByShopId(Integer shopId){
    	return goodsSearchRecordMapper.selectTop10ByShopId(shopId);
    }

    /**
     * 根据IDs获取商品列表
     * @param ids
     * @return
     */
    public List<Goods> selectGoodsListByIds(List<Integer> ids){
        Example example=new Example(tClass);
        Example.Criteria criteria=example.createCriteria();
        criteria.andIn("id",ids);
        return mapper.selectByExample(example);
    }
}
