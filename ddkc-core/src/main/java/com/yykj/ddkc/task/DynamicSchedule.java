package com.yykj.ddkc.task;

import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.yykj.ddkc.entity.Shop;
import com.yykj.ddkc.service.ShopService;
import com.yykj.system.commons.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.stereotype.Component;

import com.yykj.ddkc.entity.Order;
import com.yykj.ddkc.service.OrderService;
import com.yykj.system.commons.CalendarUtils;

@Component
public class DynamicSchedule {

	private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private ThreadPoolTaskScheduler threadPoolTaskScheduler;
	
	@Autowired
	private OrderService orderService;
	@Autowired
	ShopService shopService;

	@Bean
	public ThreadPoolTaskScheduler threadPoolTaskScheduler() {

		ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
		taskScheduler.setPoolSize(10);
		taskScheduler.setThreadNamePrefix("order-Scheduled-");
		taskScheduler.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		// 调度器shutdown被调用时等待当前被调度的任务完成
		taskScheduler.setWaitForTasksToCompleteOnShutdown(true);
		// 等待时长
		taskScheduler.setAwaitTerminationSeconds(60);
		return taskScheduler;

	}
	/**
	 * 定时延迟执行任务，只执行一次
	 * 
	* @author chenbiao
	* @date 2019年8月16日 下午3:53:02
	* 参数说明 
	*
	 */
	public void executeForOnceByOrder(int orderId, long delay) {
		threadPoolTaskScheduler.schedule(() ->{
			monitorOrderTimeOut(orderId);
		}, new Date(System.currentTimeMillis()+delay));
	}
	/**
	 * 订单支付成功之后，5分钟内商户不接单则自动退款
	* @author chenbiao
	* @date 2019年8月16日 下午5:13:45
	* 参数说明 
	* 
	* @param orderId
	* @param delay
	 */
	public void executeForOnceByShopApply(int orderId, long delay) {
		threadPoolTaskScheduler.schedule(() ->{
			monitorOrderShopApply(orderId);
		}, new Date(System.currentTimeMillis()+delay));
	}

	/**
	 * 延迟重复执行任务调度
	 * 
	* @author chenbiao
	* @date 2019年8月16日 下午3:51:52
	* 参数说明 
	* 
	* @param task
	* @param delay
	* @param period
	 */
	public void executePeriodicTask(Runnable task, long delay, long period) {

		PeriodicTrigger periodicTrigger = new PeriodicTrigger(period, TimeUnit.MICROSECONDS);
		periodicTrigger.setFixedRate(true);
		periodicTrigger.setInitialDelay(delay);

		threadPoolTaskScheduler.schedule(task, periodicTrigger);
	}

	/**
	 * 每天00:00刷新商家的余额
	 */
	public void executeAcomuntTask() {

		//CronTrigger cronTrigger=new CronTrigger("0 0 0 * * ? ");
		CronTrigger cronTrigger=new CronTrigger("0 5 0 * * ?");
		threadPoolTaskScheduler.schedule(()->{
			refreshBalance();
		}, cronTrigger);
	}

	/**
	 * 刷新商家余额
	 */
	public void refreshBalance(){
		String dateStr=CalendarUtils.dateToString(CalendarUtils.getDateByDays(-8),CalendarUtils.yyyy_MM_dd);
		//String dateStr=CalendarUtils.dateToString(CalendarUtils.getDate(),CalendarUtils.yyyy_MM_dd);
		shopService.updateAllBalance(dateStr);
		log.info("----更新商家余额-----日期："+dateStr);
	}

	/**
	 * 监控订单是否超时
	 * 
	* @author chenbiao
	* @date 2019年8月16日 下午4:00:06
	* 参数说明 
	* 
	* @param orderId
	 */
	private void monitorOrderTimeOut(int orderId) {
		Order order = orderService.getById(orderId);
		if(order.getStatus() == 0) {
			//支付有效期到了，但是订单还未支付，订单自动取消
			order.setStatus(4);
			order.setCannelTime(CalendarUtils.getDate());
			orderService.updateById(order);
			log.info("未支付订单["+order.getId()+"]，支付有效期时间已过。自动超时，支付取消！");
			//还原商品库存
			orderService.restoreByOrderId(orderId);
		}
	}
	/**
	 * 监控订单在有效时间内是否被确认。没有被确认情况下，需要做退款操作，并且还原库存
	* @author chenbiao
	* @date 2019年8月16日 下午5:46:45
	* 参数说明 
	* 
	* @param orderId
	 */
	public void monitorOrderShopApply(int orderId) {
		Order order = orderService.getById(orderId);
		if(order.getStatus() == 1) {
			try {
				//订单支付成功之后，5分钟过去了，商户并没有接单，则需要自动退款
				order.setStatus(4);
				order.setCannelTime(CalendarUtils.getDate());
				orderService.updateById(order);
				orderService.refund(order, "商户5分钟内没有确认，自动退款");
				log.info("订单["+order.getId()+"]，商户确认时间已过。自动退款！");
				//还原库存
				orderService.restoreByOrderId(orderId);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}


	/**
	 * 成功接单2小时后默认已送达
	 * 参数说明
	 * @param orderId
	 * @param delay
	 */
	public void executeForOrserFinishStatus(int orderId, long delay) {
		threadPoolTaskScheduler.schedule(() ->{
			monitorOrderFinishStatus(orderId);
		}, new Date(System.currentTimeMillis()+delay));
	}

	/**
	 * 更新订单的状态为已完成
	 */
   private void monitorOrderFinishStatus(Integer orderId){
	   Order order = orderService.getById(orderId);
	   if(order.getStatus().intValue()==2){//2表示待收货
		   order.setStatus(3);//已完成
		   order.setTakeOverTime(CalendarUtils.getDate());//默认收货时间
		   orderService.updateById(order);

		   //更新商家可见余额
		   Shop shop=shopService.getById(order.getShopId());
		   Double soBalance= NumberUtils.doubleAdd(shop.getSoBalance().doubleValue(),order.getTotalPrice().doubleValue());
		   shop.setSoBalance(new BigDecimal(soBalance));
		   shopService.updateById(shop);
	   }
   }
}
