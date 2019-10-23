package com.yykj.ddkc.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yykj.ddkc.dao.ShopAccountMapper;
import com.yykj.ddkc.entity.ShopAccount;
import com.yykj.system.commons.SystemConstants;
import com.yykj.system.commons.service.impl.AbstractBaseCrudService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * 商户账号业务管理
 * 
* @author chenbiao
* @date 2019年9月23日 下午5:53:03 
*
 */
@Service
public class ShopAccountService extends AbstractBaseCrudService<ShopAccount,Integer >{
	
	@Autowired
	ShopAccountMapper shopAccountMapper;
	
	/**
	 * 分页展示商户账号信息
	* @author chenbiao
	* @date 2019年9月23日 下午5:58:34
	* 参数说明 
	* 
	* @param map
	* @return
	* @throws RuntimeException
	 */
    public PageInfo<ShopAccount> selectByShopIdPage(Map<String, Object> map) throws RuntimeException {
        PageHelper.startPage(Integer.valueOf(String.valueOf(map.get("pageNumber"))), Integer.valueOf(String.valueOf(map.get("pageSize"))));
        Example example = new Example(tClass);
        example.createCriteria().andEqualTo("shopId", map.get("shopId")).andNotEqualTo("status", 2);
        example.setOrderByClause("id desc ");
        List<ShopAccount> list= mapper.selectByExample(example);
        return new PageInfo<>(list);
    }
    /**
     * 添加商户账号
    * @author chenbiao
    * @date 2019年9月23日 下午6:25:27
    * 参数说明 
    * 
    * @param t
    * @return
    * @throws RuntimeException
     */
    @Override
    public int insert(ShopAccount t) throws RuntimeException {
    	int i;
        if((i=mapper.insertSelective(t)) > 0) {
        	if(t.getIsMaster() == 1) {
        		ShopAccount sa = new ShopAccount();
        		sa.setIsMaster(SystemConstants.STATUS_OK);
        		Example example = new Example(ShopAccount.class);
        		example.createCriteria().andEqualTo("shopId", t.getShopId()).andNotEqualTo("id", t.getId());
        		mapper.updateByExampleSelective(sa, example);
        	}
        }
        return i;
    }

    /**
     * 判断账号是否存在
     * 
    * @author chenbiao
    * @date 2019年9月23日 下午6:33:16
    * 参数说明 
    * 
    * @param account
    * @return
     */
    public boolean validateExists(String account,Integer id) {
    	Example example = new Example(ShopAccount.class);
    	Criteria cri = example.createCriteria();
		cri.andEqualTo("account", account).andNotEqualTo("status", 2);
		if(null != id) {
			cri.andNotEqualTo("id", id);
		}
		return mapper.selectCountByExample(example) > 0;
    }
    /**
     * 删除商户账号
     * 
    * @author chenbiao
    * @date 2019年9月23日 下午6:40:20
    * 参数说明 
    * 
    * @param account
    * @return
    * @throws RuntimeException
     */
    public int delete(ShopAccount account) throws RuntimeException {
    	return mapper.updateByPrimaryKeySelective(account);
    }
    /**
     * 批量添加商户账号
    * @author chenbiao
    * @date 2019年9月23日 下午7:35:22
    * 参数说明 
    * 
    * @param accounts
     */
    public void insertList(List<ShopAccount> accounts) {
    	shopAccountMapper.insertList(accounts);
    }
    /**
     * 设置通知
    * @author chenbiao
    * @date 2019年9月25日 下午9:39:26
    * 参数说明 
    * 
    * @param shopId
     */
    public void settingNoticeByShopAccount(ShopAccount account) {
    	if(account.getStatus() == 0) { //启用状态下
    		if(account.getIsNotice() == 1) {
        		//如果当前账户是接收电话提醒的账户，那么当前店铺下，所有的其他账户都不允许接收电话提醒
        		ShopAccount account2 = new ShopAccount();
        		account2.setIsNotice(0);
        		Example example = new Example(ShopAccount.class);
        		example.createCriteria().andEqualTo("shopId", account.getShopId()).andEqualTo("status", SystemConstants.STATUS_OK).andNotEqualTo("id",account.getId());
        		shopAccountMapper.updateByExampleSelective(account2, example);
        	}else {
        		//如果当前账户不是电话提醒接收的账户，那么就需要查询当前店铺下是否有接收电话提醒的账户，如果没有则默认主账号用于接收电话提醒
        		Example example = new Example(ShopAccount.class);
        		example.createCriteria().andEqualTo("shopId", account.getShopId()).andEqualTo("status", SystemConstants.STATUS_OK).andEqualTo("isNotice", SystemConstants.STATUS_NO);
        		List<ShopAccount> list = shopAccountMapper.selectByExample(example);
        		if(null == list || list.size() == 0) {
        			//当前店铺没有用于接收电话提醒的账户，则默认主账号进行电话提醒
        			ShopAccount account2 = new ShopAccount();
        			account2.setIsNotice(1);
        			example.clear();
        			example = new Example(ShopAccount.class);
        			example.createCriteria().andEqualTo("shopId", account.getShopId()).andEqualTo("status", SystemConstants.STATUS_OK).andEqualTo("isMaster", SystemConstants.STATUS_NO);
        			shopAccountMapper.updateByExampleSelective(account2, example);
        		}
        	}
    	}else {//禁用或删除状态下
    		//如果当前账户不是电话提醒接收的账户，那么就需要查询当前店铺下是否有接收电话提醒的账户，如果没有则默认主账号用于接收电话提醒
    		Example example = new Example(ShopAccount.class);
    		example.createCriteria().andEqualTo("shopId", account.getShopId()).andEqualTo("status", SystemConstants.STATUS_OK).andEqualTo("isNotice", SystemConstants.STATUS_NO);
    		List<ShopAccount> list = shopAccountMapper.selectByExample(example);
    		if(null == list || list.size() == 0) {
    			//当前店铺没有用于接收电话提醒的账户，则默认主账号进行电话提醒
    			ShopAccount account2 = new ShopAccount();
    			account2.setIsNotice(1);
    			example.clear();
    			example = new Example(ShopAccount.class);
    			example.createCriteria().andEqualTo("shopId", account.getShopId()).andEqualTo("status", SystemConstants.STATUS_OK).andEqualTo("isMaster", SystemConstants.STATUS_NO);
    			shopAccountMapper.updateByExampleSelective(account2, example);
    		}
    	}
    }
    
    public ShopAccount getByAccount(String name) {
    	Example example = new Example(ShopAccount.class);
    	example.createCriteria().andEqualTo("account", name);
    	List<ShopAccount> accounts = shopAccountMapper.selectByExample(example);
    	if(null != accounts && accounts.size() > 0) {
    		return accounts.get(0);
    	}
    	return null;
    }
}
