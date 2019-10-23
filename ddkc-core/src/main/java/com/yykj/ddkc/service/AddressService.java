package com.yykj.ddkc.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yykj.ddkc.entity.Address;
import com.yykj.system.commons.SystemConstants;
import com.yykj.system.commons.service.impl.AbstractBaseCrudService;

import tk.mybatis.mapper.entity.Example;

/**
 * 会员地址信息管理
 * 
* @author chenbiao
* @date 2019年8月12日 下午2:22:38 
*
 */
@Service
public class AddressService extends AbstractBaseCrudService<Address, Integer>{
	
	/**
	 * 根据会员ID获取会员默认收货地址信息
	* @author chenbiao
	* @date 2019年8月12日 下午2:25:17
	* 参数说明 
	* 
	* @param memberId
	* @return
	 */
	public Address getDefaultAddressByMemberId(Integer memberId) {
		Example example = new Example(tClass);
		example.createCriteria().andEqualTo("status", SystemConstants.STATUS_OK).andEqualTo("memberId", memberId).andEqualTo("def", SystemConstants.STATUS_NO);
		List<Address> list = mapper.selectByExample(example);
		if(null != list && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	/**
	 * 根据ID+会员ID获取收货地址信息
	* @author chenbiao
	* @date 2019年8月16日 下午12:11:44
	* 参数说明 
	* 
	* @param memberId
	* @param id
	* @return
	 */
	public Address getByMemberIdAndId(Integer memberId,Integer id) {
		Example example = new Example(tClass);
		example.createCriteria().andEqualTo("status", SystemConstants.STATUS_OK).andEqualTo("memberId", memberId).andEqualTo("id", id);
		List<Address> list = mapper.selectByExample(example);
		if(null != list && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 分页展示用户收货地址列表
	* @author chenbiao
	* @date 2019年8月12日 下午2:26:24
	* 参数说明 
	* 
	* @param memberId
	* @param page
	* @param limit
	* @return
	 */
	public PageInfo<Address> getAddressListByMemberId(Integer memberId,Integer page,Integer limit){
		if(null != page && null != limit) {
			PageHelper.startPage(page, limit);
		}
		Example example = new Example(tClass);
		example.createCriteria().andEqualTo("status", SystemConstants.STATUS_OK)
			.andEqualTo("memberId", memberId);
		example.setOrderByClause("id desc");
		List<Address> list = mapper.selectByExample(example);
		return new PageInfo<Address>(list);
	}
	/**
	 * 添加或保存会员收货地址
	* @author chenbiao
	* @date 2019年8月12日 下午2:43:51
	* 参数说明 
	* 
	* @param address
	* @return
	 */
	@Transactional
	public boolean saveOrUpdate(Address address) {
		if(address.getDef()==null){
			address.setDef(0);
		}
		if(address.getDef() == 1) {
			//如果当前收货地址是默认收货地址，则当前用户下其他的收货地址都需要设置为不是默认收货地址
			Address address2 = new Address();
			address2.setDef(0);
			Example example = new Example(tClass);
			example.createCriteria().andEqualTo("memberId",address.getMemberId()).andEqualTo("def", 1);
			mapper.updateByExampleSelective(address2, example);
		}
		
		if(null != address.getId()) {
			//修改操作
			return mapper.updateByPrimaryKeySelective(address) > 0;
		}else {
			return mapper.insertSelective(address) > 0;
		}
	}

}
