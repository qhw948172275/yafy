package com.yykj.ddkc;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.yykj.commons.ServiceConstants;
import com.yykj.ddkc.entity.Order;
import com.yykj.ddkc.service.OrderService;
import com.yykj.ddkc.task.DynamicSchedule;
import com.yykj.system.commons.CalendarUtils;

/**
 * 项目启动之后，执行相关业务操作。
 * 
 * <pre>
 * 1.项目启动之后，需要将数据库中未支付订单读取出来，开启相关任务监控超时的订单
 * </pre>
* @author chenbiao
* @date 2019年8月16日 下午4:16:21 
*
 */
@Component
public class AfterServiceStartedOperate implements CommandLineRunner {
	
	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	DynamicSchedule dynamicSchedule;

	@Override
	public void run(String... args) throws Exception {
		//订单超时监控
		reMonitorOrderByTimeOut();
		
		//订单支付成功，商户确认监控
		reMonitorOrderByShopApply();
		dynamicSchedule.executeAcomuntTask();
	}
	
	/**
	 * 重新监控订单超时
	* @author chenbiao
	* @date 2019年8月16日 下午5:22:39
	* 参数说明 
	*
	 */
	private void reMonitorOrderByTimeOut() {
		log.info("项目启动完成,开始将数据库当中未支付订单重新读取出来进行监控");
		List<Order> list = orderService.selectAllByNoPay();
		if(null != list && list.size() > 0) {
			for(Order order : list) {
				if((order.getCreateTime().getTime()+ServiceConstants.TIME_EXPIRE) < System.currentTimeMillis()) {
					//订单支付超时
					order.setStatus(4);
					order.setCannelTime(CalendarUtils.getDate());
					orderService.updateById(order);
				}else {
					//订单没有超时，则需要丢到任务线程池当中管理起来
					dynamicSchedule.executeForOnceByOrder(order.getId(), ((order.getCreateTime().getTime()+ServiceConstants.TIME_EXPIRE) - System.currentTimeMillis()));
				}
			}
		}
		log.info("未支付订单监控完成");
	}

	/**
	 * 订单支付成功，商户确认订单，
	* @author chenbiao
	* @date 2019年8月16日 下午5:23:40
	* 参数说明 
	*
	 */
	private void reMonitorOrderByShopApply() {
		log.info("项目启动完成,开始将数据库当中未确认订单重新读取出来进行监控");
		List<Order> list = orderService.selectAllByNoApply();
		if(null != list && list.size() > 0) {
			for(Order order : list) {
				if((order.getPayTime().getTime()+ServiceConstants.SHOP_APPLY_TIME_EXPIRE) < System.currentTimeMillis()) {
					//商家确认时间超时
					order.setStatus(4);
					order.setCannelTime(CalendarUtils.getDate());
					orderService.updateById(order);
					try {
						orderService.refund(order, "商户5分钟内没有确认，自动退款");
						log.info("订单["+order.getId()+"]，商户确认时间已过。自动退款！");
						//还原库存
						orderService.restoreByOrderId(order.getId());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else {
					//订单没有超时，则需要丢到任务线程池当中管理起来
					dynamicSchedule.executeForOnceByShopApply(order.getId(), ((order.getPayTime().getTime()+ServiceConstants.SHOP_APPLY_TIME_EXPIRE) - System.currentTimeMillis()));
				}
			}
		}
		log.info("未确认订单监控完成");
	}
}
