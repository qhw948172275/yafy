package com.yykj.ddkc.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.yykj.ddkc.entity.Member;
import com.yykj.system.commons.service.impl.AbstractBaseCrudService;

import tk.mybatis.mapper.entity.Example;

/**
 * 会员业务接口
 * 
* @author chenbiao
* @date 2019年8月12日 下午12:00:09 
*
 */
@Service
public class MemberService extends AbstractBaseCrudService<Member, Integer> {
	
	/**
	 * 根据用户唯一标识查询用户基本信息
	* @author chenbiao
	* @date 2019年8月12日 下午12:05:09
	* 参数说明 
	* 
	* @param openId
	* @return
	 */
	public Member getByOpenId(String openId) {
		Example example = new Example(tClass);
		example.createCriteria().andEqualTo("openId", openId);
		List<Member> list = mapper.selectByExample(example);
		if(null != list && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	

}
