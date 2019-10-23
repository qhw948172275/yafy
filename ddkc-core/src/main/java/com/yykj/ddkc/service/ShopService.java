package com.yykj.ddkc.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.yykj.ddkc.response.ShopHistogramResponse;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yykj.ddkc.dao.ShopMapper;
import com.yykj.ddkc.entity.Delivery;
import com.yykj.ddkc.entity.Shop;
import com.yykj.ddkc.response.ShopResponse;
import com.yykj.system.commons.StringUtils;
import com.yykj.system.commons.service.impl.AbstractBaseCrudService;

import tk.mybatis.mapper.entity.Example;

@Service
public class ShopService extends AbstractBaseCrudService<Shop,Integer> {
	
	@Autowired 
	ShopMapper shopMapper;

    /**
     * 查询商铺
     * @param map
     * @return
     * @throws RuntimeException
     */
    public PageInfo<Shop> selectAllPage(Map<String, Object> map) throws RuntimeException {
        PageHelper.startPage(Integer.valueOf(String.valueOf(map.get("pageNumber"))), Integer.valueOf(String.valueOf(map.get("pageSize"))));
        Example example = new Example(tClass);
        Example.Criteria criteria=example.createCriteria();
        criteria.andNotEqualTo("status",2);//2表示删除
        if(map.containsKey("keyword")){

            criteria.andLike("name","%"+map.get("keyword")+"%").orEqualTo("id",map.get("keyword"));
        }
        if(map.containsKey("createId")){
            Example.Criteria criteria1= example.and();
            criteria1.andEqualTo("createId",map.get("createId"));
        }
        List<Shop> list= mapper.selectByExample(example);
        return new PageInfo<>(list);
    }

    /**
     * 根据电话号码查询商家
     * @param phone
     * @return
     */
    public List<Shop> selectByPhone(String phone) {
        Example example=new Example(tClass);
        Example.Criteria criteria=example.createCriteria();
        criteria.andEqualTo("phone",phone);
        return mapper.selectByExample(example);
    }
    
    /**
     * 根据经纬度查询附近商家信息
    * @author chenbiao
    * @date 2019年8月14日 上午10:30:42
    * 参数说明 
    * 
    * @param lat
    * @param lng
     * @param delivery 
    * @return
     */
    public List<ShopResponse> selectByLatAndLng(BigDecimal lat,BigDecimal lng, Delivery delivery){
    	return shopMapper.selectByLatAndLng(lat, lng,delivery.getVal());
    }
    /**
     * 前端关键字搜索商铺信息
    * @author chenbiao
    * @date 2019年8月14日 上午10:45:05
    * 参数说明 
    * 
    * @param keyword
    * @return
     */
    public List<ShopResponse> selectByKeyword(BigDecimal lat,BigDecimal lng,String keyword){
    	return shopMapper.selectByKeyword(lat,lng,(StringUtils.isNotEmpty(keyword) ? ("%"+keyword+"%") : null),null);
    }
    /**
     * 根据ID获取店铺信息
    * @author chenbiao
    * @date 2019年8月14日 下午4:16:38
    * 参数说明 
    * 
    * @param lat
    * @param lng
    * @param id
    * @return
     */
    public ShopResponse selectResponseById(BigDecimal lat,BigDecimal lng,Integer id){
    	List<ShopResponse> list = shopMapper.selectByKeyword(lat,lng,null,id);
    	if(CollectionUtils.isNotEmpty(list)) {
    		return list.get(0);
    	}
    	return null;
    }

    /**
     * 更新商家余额
     * @param dateStr
     * @return
     */
   public void  updateAllBalance(String dateStr){
         shopMapper.updateAllBalance(dateStr);
    }

    /**
     * 查询商家数量
     * @return
     */
    public Integer selectCount(Map<String,Object> map) {
        Example example=new Example(tClass);
        Example.Criteria criteria=example.createCriteria();
        criteria.andEqualTo("status",0);
        if(map.containsKey("createId")){
            criteria.andEqualTo("createId",map.get("createId")) ;
        }
        return mapper.selectCountByExample(example);
    }

    /**
     * 统计本月新建的商家
     * @param newDate
     * @return
     */
    public List<ShopHistogramResponse> selectShopHistogramResponse(String newDate,Map<String,Object> map) {
        Integer createId=map.containsKey("createId")?(Integer) map.get("createId"):null;
        return shopMapper.selectShopHistogramResponse(newDate,createId);
    }
    
    public List<Shop> selectByIdNotInShopAccountByIsMaster(){
    	return shopMapper.selectByIdNotInShopAccountByIsMaster();
    }
}
