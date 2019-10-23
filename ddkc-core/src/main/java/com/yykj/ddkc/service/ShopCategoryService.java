package com.yykj.ddkc.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yykj.ddkc.entity.ShopCategory;
import com.yykj.system.commons.service.impl.AbstractBaseCrudService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

@Service
public class ShopCategoryService extends AbstractBaseCrudService<ShopCategory,Integer> {


    @Override
    public PageInfo<ShopCategory> selectAllPage(Map<String, Object> map) throws RuntimeException {
        PageHelper.startPage(Integer.valueOf(String.valueOf(map.get("pageNumber"))), Integer.valueOf(String.valueOf(map.get("pageSize"))));
        Example example = new Example(tClass);
        Example.Criteria criteria=example.createCriteria();
        criteria.orEqualTo("shopId",map.get("shopId"));
        if(map.containsKey("keyword")){
            Example.Criteria criteria1=example.and();
            criteria1.orLike("name","%"+map.get("keyword")+"%");
        }
        List<ShopCategory> list= mapper.selectByExample(example);
        return new PageInfo<>(list);
    }

    /**
     * 根据店铺ID查询类别
     * @param shopId
     * @return
     */
    public List<ShopCategory> selectByShopId(Integer shopId) {
        Example example=new Example(tClass);
        Example.Criteria criteria=example.createCriteria();
        criteria.andEqualTo("shopId",shopId).andEqualTo("status",0);
        return mapper.selectByExample(example);
    }

    /**
     * 根据分类名称，商店ID和状态搜索
     * @return
     */
    public List<ShopCategory> selectByShopIdAndName(String categoryName,Integer shopId,Integer status,Integer categoryId){
        Example example=new Example(tClass);
        Example.Criteria criteria=example.createCriteria();
        criteria.andEqualTo("name",categoryName).andEqualTo("shopId",shopId)
                .andEqualTo("status",status);
        if(categoryId!=null){
            criteria.andNotEqualTo("id",categoryId);
        }
        return mapper.selectByExample(example);
    }

    /**
     * 批量更新商品分类
     */
    public void updateByIds(List<Integer> ids,ShopCategory shopCategory){
        Example example=new Example(tClass);
        Example.Criteria criteria=example.createCriteria();
        criteria.andIn("id",ids);
        mapper.updateByExampleSelective(shopCategory,example);
    }
}
