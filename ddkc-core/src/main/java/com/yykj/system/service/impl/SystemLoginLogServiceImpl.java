package com.yykj.system.service.impl;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yykj.system.commons.CalendarUtils;
import com.yykj.system.commons.ConvertUtils;
import com.yykj.system.commons.PageTools;
import com.yykj.system.dao.SystemLoginLogMapper;
import com.yykj.system.entity.SystemLoginLog;
import com.yykj.system.entity.SystemUser;
import com.yykj.system.service.SystemUserLoginLogService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;


/**
 * 用户登录日志记录
 * 
 * @author kimi
 * @dateTime 2013-9-30 上午11:32:00
 */
@Service
public class SystemLoginLogServiceImpl implements SystemUserLoginLogService {
	@Autowired
	protected SystemLoginLogMapper userLoginLogMapper;

	/**
	 * @author kimi
	 * @dateTime 2013-10-8 上午10:40:49
	 * @param user
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean addUserLoginLog(SystemUser user, String failRemark) throws Exception {
		if (null != user) {
			SystemLoginLog userLoginLog = new SystemLoginLog();
			userLoginLog.setUserName(user.getName());
			userLoginLog.setRealmName(user.getRealName());
			userLoginLog.setUserId(user.getId());
			userLoginLog.setLoginTime(Calendar.getInstance().getTime());
			userLoginLog.setLoginIp(user.getLastLoginIp());
//			JSONObject ipInfo = ipSeeker.getIpInfo(user.getLastLoginIp());
//			if (null != ipInfo) {
//				userLoginLog.setLoginProvince(ipInfo.getString(IpUtilInter.PROVINCE_EN));
//				userLoginLog.setLoginAddress(ipInfo.getString(IpUtilInter.ADDRESS_DETAIL));
//			}
			if (null != failRemark) {
				// 1代表登录失败。并且附加上失败原因
				userLoginLog.setStatus(1);
				userLoginLog.setRemark(failRemark);
			} else {
				// 0代表登录成功
				userLoginLog.setStatus(0);
			}
			if (userLoginLogMapper.insertSelective(userLoginLog) > 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @author kimi
	 * @dateTime 2013-9-30 上午11:44:15
	 */
	@Override
	public long selectCountByUserLoginLog(SystemLoginLog userLoginLog, String startTime, String endTime) throws Exception {
		return this.userLoginLogMapper.selectCountByExample(buildExample(userLoginLog, startTime, endTime));
	}

	/**
	 * @author kimi
	 * @dateTime 2013-9-30 上午11:44:18
	 *           (non-JSDoc)
	 */
	@Override
	public List<SystemLoginLog> selectUserLoginLog(SystemLoginLog userLoginLog, String startTime, String endTime) throws Exception {
		return selectUserLoginLog(userLoginLog, startTime, endTime, null);
	}

	/**
	 * @author kimi
	 * @dateTime 2013-9-30 上午11:44:21
	 *           (non-JSDoc)
	 */
	@Override
	public List<SystemLoginLog> selectUserLoginLog(SystemLoginLog userLoginLog, String startTime, String endTime, PageTools page)
			throws Exception {
		return selectUserLoginLog(userLoginLog, startTime, endTime, null, page);
	}

	/**
	 * @author kimi
	 * @dateTime 2013-9-30 上午11:44:25
	 *           (non-JSDoc)
	 */
	@Override
	public List<SystemLoginLog> selectUserLoginLog(SystemLoginLog userLoginLog, String startTime, String endTime, String[] orderBy,
			PageTools page) throws Exception {
		Example Example = buildExample(userLoginLog, startTime, endTime);
		if (null != orderBy && orderBy.length > 0) {
			if (null != page) {
				Example.setOrderByClause(ConvertUtils.convertArrayToString(orderBy, ",") + " limit "
						+ page.getLimitFrom() + "," + page.getLimitTo());
			} else {
				Example.setOrderByClause(ConvertUtils.convertArrayToString(orderBy, ","));
			}
		} else {
			if (null != page) {
				Example.setOrderByClause(" id desc limit " + page.getLimitFrom() + "," + page.getLimitTo());
			}
		}
		return this.userLoginLogMapper.selectByExample(Example);
	}

	/**
	 * @author kimi
	 * @dateTime 2013-9-30 上午11:44:28
	 *           (non-JSDoc)
	 */
	@Override
	public boolean deleUserLoginLog(int uid) throws Exception {
		if (this.userLoginLogMapper.deleteByPrimaryKey(uid) > 0) {
			return true;
		}
		return false;
	}

	/**
	 * @author kimi
	 * @dateTime 2013-9-30 上午11:44:31
	 *           (non-JSDoc)
	 */
	@Override
	public boolean deleUserLoginLog(Integer[] uid) throws Exception {
		if (null != uid && uid.length > 0) {
			Example example = new Example(SystemLoginLog.class);
			example.createCriteria().andIn("userId", Arrays.asList(uid));
			if (this.userLoginLogMapper.deleteByExample(example) > 0) {
				return true;
			}
		}
		return false;
	}

	public void insert(SystemLoginLog loginLog) {
		userLoginLogMapper.insert(loginLog);
	}

	private Example buildExample(SystemLoginLog userLoginLog, String startTime, String endTime) {
		Example example = new Example(SystemLoginLog.class);
		if (userLoginLog != null) {
			Criteria criter = example.createCriteria();
			if (null != userLoginLog.getUserName() && !"".equals(userLoginLog.getUserName())) {
				criter.andEqualTo("userName", userLoginLog.getUserName().trim());
			}
			if (null != userLoginLog.getStatus()) {
				criter.andEqualTo("status", userLoginLog.getStatus());
			}
			if (null != userLoginLog.getLoginAddress() && !"".equals(userLoginLog.getLoginAddress())) {
				criter.andLike("loginAddress", "%" + userLoginLog.getLoginAddress().trim() + "%");
			}
			if (null != startTime && !"".equals(startTime)) {
				criter.andGreaterThanOrEqualTo("loginTime", CalendarUtils.stringToDate(startTime+" 00:00:00", CalendarUtils.yyyy_MM_dd__HH_mm_ss));
			}
			if (null != endTime && !"".equals(endTime)) {
				criter.andLessThanOrEqualTo("loginTime", CalendarUtils.stringToDate(endTime+" 23:59:59", CalendarUtils.yyyy_MM_dd__HH_mm_ss));
			}
		}
		example.setOrderByClause("id desc");
		return example;
	}
}
