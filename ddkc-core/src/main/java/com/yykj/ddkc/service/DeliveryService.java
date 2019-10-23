package com.yykj.ddkc.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.yykj.ddkc.entity.Delivery;
import com.yykj.system.commons.service.impl.AbstractBaseCrudService;

import tk.mybatis.mapper.entity.Example;

/**
 * 配送设置业务实现
 * 
* @author chenbiao
* @date 2019年8月16日 上午10:15:47 
*
 */
@Service
public class DeliveryService extends AbstractBaseCrudService<Delivery, Integer> {
	
	
	/**
	 * 根据type获取对应设置
	 * 
	* @author chenbiao
	* @date 2019年8月16日 上午10:16:37
	* 参数说明 
	* 
	* @param type
	* @return
	 */
	public Delivery getByType(Integer type) {
		Example example = new Example(tClass);
		example.createCriteria().andEqualTo("type", type);
		List<Delivery> list = mapper.selectByExample(example);
		if(null != list && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	
}
