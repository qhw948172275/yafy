package com.yykj.ddkc.service;

import com.github.pagehelper.PageHelper;
import com.yykj.ddkc.entity.SearchRecord;
import com.yykj.system.commons.service.impl.AbstractBaseCrudService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class SearchRecordService extends AbstractBaseCrudService<SearchRecord,Integer> {

    /**
     * 根据商铺ID查询搜索记录
     * @param shopId
     * @return
     */
    public List<SearchRecord> selectByShopId(Integer shopId) {
        PageHelper.startPage(0,15);//只查询15条记录
        Example example=new Example(tClass);
        Example.Criteria criteria=example.createCriteria();
        criteria.andEqualTo("shopId",shopId).andEqualTo("status",0);
        example.setOrderByClause(" id desc");
        return mapper.selectByExample(example);
    }

    /**
     * 删除指定商店ID和搜索内容的记录
     * @param content
     * @param shopId
     */
    public void delByContent(String content,Integer shopId){
        Example example=new Example(tClass);
        Example.Criteria criteria=example.createCriteria();
        criteria.andEqualTo("content",content).andEqualTo("shopId",shopId);
        SearchRecord searchRecord=new SearchRecord();
        searchRecord.setStatus(1);
        mapper.updateByExampleSelective(searchRecord,example);
    }

    /**
     * 清除商家搜索记录
     * @param searchRecord
     */
    public void delSearchRecord(SearchRecord searchRecord) {
        Example example=new Example(tClass);
        Example.Criteria criteria=example.createCriteria();
        criteria.andEqualTo("shopId",searchRecord.getShopId());

        mapper.updateByExampleSelective(searchRecord,example);
    }
}
