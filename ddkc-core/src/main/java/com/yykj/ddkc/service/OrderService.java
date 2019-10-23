package com.yykj.ddkc.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.yykj.commons.NoticeErrorEnum;
import com.yykj.commons.NoticeUtils;
import com.yykj.commons.ServiceConstants;
import com.yykj.commons.wechat.AppConfig;
import com.yykj.commons.wechat.WechatUtils;
import com.yykj.ddkc.dao.CartMapper;
import com.yykj.ddkc.dao.GoodsMapper;
import com.yykj.ddkc.dao.MemberMapper;
import com.yykj.ddkc.dao.OrderDetailsMapper;
import com.yykj.ddkc.dao.OrderMapper;
import com.yykj.ddkc.dao.RefundMapper;
import com.yykj.ddkc.dao.ShopAccountMapper;
import com.yykj.ddkc.dao.ShopAccountNoticeLogMapper;
import com.yykj.ddkc.dao.ShopMapper;
import com.yykj.ddkc.dao.WechatNotifyMapper;
import com.yykj.ddkc.entity.Member;
import com.yykj.ddkc.entity.Order;
import com.yykj.ddkc.entity.OrderDetails;
import com.yykj.ddkc.entity.Refund;
import com.yykj.ddkc.entity.Shop;
import com.yykj.ddkc.entity.ShopAccount;
import com.yykj.ddkc.entity.ShopAccountNoticeLog;
import com.yykj.ddkc.entity.WechatNotify;
import com.yykj.ddkc.response.CartResponse;
import com.yykj.ddkc.response.OrderCountResponse;
import com.yykj.ddkc.response.OrderDetailResponse;
import com.yykj.ddkc.response.OrderGoodsResponse;
import com.yykj.ddkc.response.OrderLogResponse;
import com.yykj.ddkc.response.OrderPieResponse;
import com.yykj.ddkc.response.OrderProcessResponse;
import com.yykj.ddkc.response.PlatformCostCensus;
import com.yykj.ddkc.response.ShopOrderCountResponse;
import com.yykj.ddkc.response.ShopOrderResponse;
import com.yykj.ddkc.task.DynamicSchedule;
import com.yykj.system.commons.CalendarUtils;
import com.yykj.system.commons.JsonUtils;
import com.yykj.system.commons.RandomUtils;
import com.yykj.system.commons.SystemConstants;
import com.yykj.system.commons.service.impl.AbstractBaseCrudService;

import cn.binarywang.wx.miniapp.api.WxMaMsgService;
import cn.binarywang.wx.miniapp.bean.WxMaTemplateData;
import cn.binarywang.wx.miniapp.bean.WxMaTemplateMessage;
import me.chanjar.weixin.common.error.WxErrorException;
import tk.mybatis.mapper.entity.Example;

/**
 * 订单业务实现
 *
 * @author chenbiao
 * @date 2019年8月15日 下午7:13:52
 *
 */
@Service
public class OrderService extends AbstractBaseCrudService<Order, Integer> {
	
	@Autowired
	OrderMapper orderMapper;
	
	@Autowired
	ShopMapper shopMapper;
	
	@Autowired
	OrderDetailsMapper orderDetailsMapper;
	
	@Autowired
	DynamicSchedule dynamicSchedule;
	
	@Autowired
	WechatNotifyMapper wechatNotifyMapper;
	
	@Autowired
	RefundMapper refundMapper;
	
	@Autowired
	AppConfig appConfig;
	
	@Autowired
	GoodsMapper goodsMapper;
	@Autowired
	CartMapper cartMapper;
	
	@Autowired
	MemberMapper memberMapper;
	
	@Autowired
	ShopAccountMapper shopAccountMapper;
	
	@Autowired
	ShopAccountNoticeLogMapper shopAccountNoticeLogMapper;
	
	

	/**
	 * 添加订单信息
	 * 
	* @author chenbiao
	* @date 2019年8月16日 下午12:23:46
	* 参数说明 
	* 
	* @param order
	* @param carts
	* @return
	 */
	@Transactional
	public Order addOrder(Order order, List<CartResponse> carts) {
		if(mapper.insertSelective(order) > 0) {
			List<OrderDetails> details = new ArrayList<OrderDetails>();
			List<Integer> cartIds=new ArrayList<>();//购物车IDs
			for(CartResponse cr : carts) {
				details.add(new OrderDetails(cr,order));
				cartIds.add(cr.getId());
			}
			if(orderDetailsMapper.insertList(details) > 0) {
				//锁定库存
				for(OrderDetails d : details) {
					goodsMapper.updateStockBySubtract(d.getGoodsId(), d.getCounts().intValue());
				}
				//订单支付超时任务监听
				dynamicSchedule.executeForOnceByOrder(order.getId(), ServiceConstants.TIME_EXPIRE);
				return order;
			}
			/**
			 * 修改购物车状态
			 */
			cartMapper.updateStatusByList(cartIds,1);
		}

		return null;
	}

	/**
	 * 取消订单
	 *
	 * @author chenbiao
	 * @date 2019年8月15日 下午7:16:08 参数说明
	 *
	 * @param orderId
	 * @param memberId
	 * @return
	 */
	public boolean cannelOrder(Integer orderId, Integer memberId) throws Exception {
		Order order = getById(orderId);
		if (!order.getMemberId().equals(memberId)) {
			throw new Exception("当前订单不存在");
		} else if (order.getStatus() > 1) {
			String errorMsg = "当前订单";
			switch (order.getStatus()) {
			case 2: //待收货
				errorMsg+= "处于”待收货“状态";
				break;
			case 3: //已完成
				errorMsg+= "处于”已完成“状态";
				break;
			case 4: //已取消
				errorMsg+= "处于”已取消“状态";
				break;
			case 5: //退款中
				errorMsg+= "处于”退款中“状态";
				break;
			case 6: //已退款
				errorMsg+= "处于”已退款“状态";
				break;

			default:
				break;
			}
			errorMsg+= "不能取消";
			throw new Exception(errorMsg);
		}else if(order.getStatus() == 1) {
			refund(order,"会员手动取消订单");
		}
		order.setStatus(4);
		order.setCannelTime(CalendarUtils.getDate());
		if( mapper.updateByPrimaryKeySelective(order) > 0) {
			//订单取消之后去要还原库存
			restoreByOrderId(order.getId());
			return true;
		}
		return false;
	}
	/**
	 * 还原库存
	 * 
	* @author chenbiao
	* @date 2019年8月16日 下午5:43:16
	* 参数说明 
	* 
	* @param id
	 */
	public void restoreByOrderId(Integer id) {
		List<OrderDetails> details = selectDetailsByOrderId(id);
		for(OrderDetails d : details) {
			goodsMapper.updateStockByAdd(d.getGoodsId(), d.getCounts().intValue());
		}
	}

	public void refund(Order order,String remark) throws Exception {
		//待确认情况下取消订单，需要进行退款操作
		Refund refund = new Refund(RandomUtils.getRefundNumber(), order.getId(),(byte) 0, order.getTotalPrice().doubleValue(), -1,remark);
		refundMapper.insertSelective(refund); //添加退款信息
		WechatNotify wechatNotify = getWechatNotifyByOrderNumber(order.getOrderNumber());
		order.setStatus(5);//退款中
		orderMapper.updateByPrimaryKeySelective(order);
//		Member member = memberMapper.selectByPrimaryKey(order.getMemberId());
		Map<?,?> map = WechatUtils.refundByWXPay(refund.getNumbers(), order, wechatNotify, appConfig);
		if(map.containsKey("return_code") && map.get("return_code").toString().toUpperCase().equals("SUCCESS")
				&&
				map.containsKey("result_code") && map.get("result_code").toString().toUpperCase().equals("SUCCESS")) {
			//退款成功
			refund.setStatus((byte)1);
			refund.setRefundTime(CalendarUtils.getTimeStamp());
			refundMapper.updateByPrimaryKeySelective(refund);
			order.setStatus(6);//退款成功
			order.setRefundTime(CalendarUtils.getDate());
			orderMapper.updateByPrimaryKeySelective(order);
		}else {
			//退款失败
			refund.setStatus((byte)2);
			refund.setRefundTime(CalendarUtils.getTimeStamp());
			refundMapper.updateByPrimaryKeySelective(refund);
		}
	}

	/**
	 * 订单确认送达
	* @author chenbiao
	* @date 2019年8月15日 下午7:25:14
	* 参数说明
	*
	* @param orderId
	* @param memberId
	* @return
	 */
	public boolean finishedOrder(Integer orderId, Integer memberId) throws Exception{
		Order order = getById(orderId);
		if (!order.getMemberId().equals(memberId)) {
			throw new Exception("当前订单不存在");
		} else {
			order.setStatus(3); //确认送达，表示订单已完成
			order.setTakeOverTime(CalendarUtils.getDate());
			if( mapper.updateByPrimaryKeySelective(order) > 0) {
				//TODO 做一些消息通知
				//更新这些商品的销售量
				OrderDetailResponse orderDetailResponse=orderMapper.selectOrderDetailResponseByOrderId(orderId);
				for(OrderDetails orderDetails:orderDetailResponse.getOrderDetailsList()){
					goodsMapper.updateSalesCount(orderDetails.getGoodsId(),orderDetails.getCounts());
				}

				return true;
			}
		}
		return false;
	}

	/**
	 *
	* 根据orderId获取订单信息
	* @author chenbiao
	* @date 2019年8月15日 下午7:43:48
	* 参数说明
	*
	* @param orderId
	* @return
	 */
	public Order selectByOrderId(String orderId) {
		Example example = new Example(tClass);
		example.createCriteria().andEqualTo("orderNumber", orderId);
		List<Order> list = mapper.selectByExample(example);
		if(null != list && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 验签通过之后，需要针对订单进行修改状态，以及通知会员支付成功信息，通知商户来单信息
	* @author chenbiao
	* @date 2019年8月15日 下午7:45:50
	* 参数说明
	*
	* @param returnMap
	* @return
	 */
	public String payNotifyByWechatPay(Map<String, String> returnMap,WxMaMsgService maMsgService) {
		ObjectNode objectNode = JsonUtils.getMapperInstance().createObjectNode();
		WechatNotify notify = new WechatNotify(returnMap);
		log.info("the wechat miniprogram pay notify content : {}", returnMap.toString());

		Order order = selectByOrderId(notify.getOutTradeNo()); // 根据订单号查询订单信息
		// 判断订单是否处于待支付状态
		if (order.getStatus().intValue() == SystemConstants.STATUS_OK) {
			order.setPayTime(CalendarUtils.stringToDate(notify.getTimeEnd(), CalendarUtils.yyyyMMddHHmmss));
			order.setStatus(SystemConstants.STATUS_NO);// 标记为已支付
			wechatNotifyMapper.insertSelective(notify); // 添加微信回调通知
			if (mapper.updateByPrimaryKeySelective(order) > 0) {
				objectNode.put("status", true);
				//调用接口通知商户进行订单确认
				List<ShopAccount> accounts = shopAccountMapper.selectByShopId(order.getShopId());
				if(null != accounts && accounts.size() > 0) {
					ShopAccountNoticeLog log = new ShopAccountNoticeLog();
					for(ShopAccount account : accounts) {
						log.setCreateTime(CalendarUtils.getDate());
						log.setOrderId(order.getId());
						log.setPhone(account.getAccount());
						JsonNode json = NoticeUtils.sendVoicenotifySMS(account.getAccount());
						log.setResultCode(json.get("return_code").asText());
						log.setResultMsg(NoticeErrorEnum.getMsgByCode(log.getResultCode()));
						if(json.has("order_id")) {							
							log.setNoticeOrderId(json.get("order_id").asText());
						}
						shopAccountNoticeLogMapper.insert(log);
					}
				}
				//订单确认监控
				dynamicSchedule.executeForOnceByShopApply(order.getId(), ServiceConstants.SHOP_APPLY_TIME_EXPIRE);
				try {
					//用户端，给用户下行支付成功消息
					Member member = memberMapper.selectByPrimaryKey(order.getMemberId());
					Shop shop = shopMapper.selectByPrimaryKey(order.getShopId());
					List<WxMaTemplateData> datas= Lists.newArrayList();
					datas.add(new WxMaTemplateData("keyword1",order.getOrderNumber()));
					datas.add(new WxMaTemplateData("keyword2",order.getOrderName()));
					datas.add(new WxMaTemplateData("keyword3",order.getTotalPrice().toString()));
					datas.add(new WxMaTemplateData("keyword4",order.getStatusText()));
					datas.add(new WxMaTemplateData("keyword5",order.getCreateTimeText()));
					datas.add(new WxMaTemplateData("keyword6",shop.getName()));
					datas.add(new WxMaTemplateData("keyword7",shop.getContact()));
					
					WxMaTemplateMessage templateMessage = WxMaTemplateMessage
							.builder()
							.formId(order.getPrepayId()) //订单预支付ID
							.templateId(appConfig.getPaySuccessMessageTemplateId())
							.toUser(member.getOpenId())
							.data(datas)
							.build();
					maMsgService.sendTemplateMsg(templateMessage);
				} catch (WxErrorException e) {
					e.printStackTrace();
				}
			}
		}
		// 非待支付状态，则不修改订单相关信息
		objectNode.put("result", "SUCCESS");
		return objectNode.toString();
	}

    /**
     *
     * @param shopId
     * @return
     */
    public List<Order> selectAllByShopId(Integer shopId,Integer status) {
        Example example=new Example(tClass);
        Example.Criteria criteria=example.createCriteria();
        criteria.andEqualTo("shopId",shopId);
        if(status!=null){
            criteria.andEqualTo("status",status);
        }
        example.setOrderByClause(" id desc");
        return mapper.selectByExample(example);
    }

    /**
     * 查询订单流程
     * @param orderId
     * @return
     */
    public OrderProcessResponse selectOrderProcessResponseByOrderId(Integer orderId) {
         return orderMapper.selectOrderProcessResponseByOrderId(orderId);
    }

    /**
     * 查询订单详情
     * @param orderId
     * @return
     */
    public OrderDetailResponse selectOrderDetailResponseByOrderId(Integer orderId) {
        return orderMapper.selectOrderDetailResponseByOrderId(orderId);
    }

    /**
     * 构造订单号
    * @author chenbiao
    * @date 2019年8月16日 下午12:19:04
    * 参数说明 
    * 
    * @return
     */
	public String buildOrderNumber() {
		String number = RandomUtils.getOrderNumber();
		if(null != selectByOrderId(number)) {
			return buildOrderNumber();
		}
		return number;
	}

	/**
	 * 获取所有未支付订单信息
	 * 
	* @author chenbiao
	* @date 2019年8月16日 下午4:18:48
	* 参数说明 
	* 
	* @return
	 */
	public List<Order> selectAllByNoPay(){
		Example example = new Example(tClass);
		example.createCriteria().andEqualTo("status", SystemConstants.STATUS_OK);
		List<Order> list = mapper.selectByExample(example);
		return list;
	}
	/**
	 * 获取所有商户未确认订单
	* @author chenbiao
	* @date 2019年8月16日 下午5:24:16
	* 参数说明 
	* 
	* @return
	 */
	public List<Order> selectAllByNoApply(){
		Example example = new Example(tClass);
		example.createCriteria().andEqualTo("status", SystemConstants.STATUS_NO);
		List<Order> list = mapper.selectByExample(example);
		return list;
	}
	/**
	 * 根据订单ID获取订单详情信息
	* @author chenbiao
	* @date 2019年8月16日 下午5:41:42
	* 参数说明 
	* 
	* @param orderId
	* @return
	 */
	public List<OrderDetails> selectDetailsByOrderId(Integer orderId){
		Example example = new Example(OrderDetails.class);
		example.createCriteria().andEqualTo("orderId", orderId);
		List<OrderDetails> details = orderDetailsMapper.selectByExample(example);
		return details;
	}
	/**
	 * 根据订单编号获取微信支付回调通知信息
	* @author chenbiao
	* @date 2019年8月16日 下午5:05:21
	* 参数说明 
	* 
	* @param orderNumber
	* @return
	 */
	public WechatNotify getWechatNotifyByOrderNumber(String orderNumber) {
		Example example = new Example(WechatNotify.class);
		example.createCriteria().andEqualTo("outTradeNo",orderNumber);
		List<WechatNotify> list = wechatNotifyMapper.selectByExample(example);
		if(null != list && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 根据日期查询订单并统计订单数量和收入
	 * @param orderLogDate
	 * @return
	 */
    public OrderLogResponse selectByDate(String orderLogDate,Integer shopId) {
    	return orderMapper.selectByDate(orderLogDate,shopId);
    }

	/**
	 * 根据订单日期查询订单上的全部商品
	 * @param orderLogDate
	 * @param shopId
	 * @return
	 */
    public List<OrderGoodsResponse> selectGoodsByDateOrder(String orderLogDate,Integer shopId){
    	return orderMapper.selectGoodsByDateOrder(orderLogDate,shopId);
	}

	/**
	 * 后台订单主页面搜索
	 * @param map
	 * @return
	 */
	public PageInfo<Order> selectAllPagefor(Map<String, Object> map) {
		if(map.containsKey("pageNumber")){
			PageHelper.startPage(Integer.valueOf(String.valueOf(map.get("pageNumber"))), Integer.valueOf(String.valueOf(map.get("pageSize"))));
		}
		String shopName=map.containsKey("shopName")? "%"+ map.get("shopName") +"%":null;
		Integer shopId=map.containsKey("shopId")?(Integer)map.get("shopId"):null;
		String startTime=map.containsKey("startTime")?(String)map.get("startTime"):null;
		String endTime=map.containsKey("endTime")?(String)map.get("endTime"):null;
		Integer status=map.containsKey("status")?(Integer)map.get("status"):null;
		Integer createId=map.containsKey("createId")?(Integer) map.get("createId"):null;
		List<Order> orders=orderMapper.selectOrderForPlatform(shopName,shopId,startTime,endTime,status,createId);
    	return new PageInfo<>(orders);
	}

	/**
	 * 根据时间统计当天得营业额
	 * @param map
	 * @return
	 */
	public PageInfo<OrderCountResponse> selectAllPageByDate(Map<String,Object> map){
		if(map.containsKey("pageNumber")){
			PageHelper.startPage(Integer.valueOf(String.valueOf(map.get("pageNumber"))), Integer.valueOf(String.valueOf(map.get("pageSize"))));
		}
		String startTime=map.containsKey("startTime")?(String)map.get("startTime"):null;
		String endTime=map.containsKey("endTime")?(String)map.get("endTime"):null;
		Integer createId=map.containsKey("createId")?(Integer) map.get("createId"):null;
		List<OrderCountResponse> orderCountResponses=orderMapper.selectOrderMapperByDate(startTime,endTime,createId);
		return new PageInfo<>(orderCountResponses);
	}


	/**
	 * 商家订单流水统计
	 * @param map
	 * @return
	 */
	public PageInfo<ShopOrderCountResponse> selectShopPageByDate(Map<String,Object> map){
		if(map.containsKey("pageNumber")){
			PageHelper.startPage(Integer.valueOf(String.valueOf(map.get("pageNumber"))), Integer.valueOf(String.valueOf(map.get("pageSize"))));
		}
		String startTime=map.containsKey("startTime")?(String)map.get("startTime"):null;
		String endTime=map.containsKey("endTime")?(String)map.get("endTime"):null;
		Integer createId=map.containsKey("createId")?(Integer) map.get("createId"):null;
		List<ShopOrderCountResponse> orderCountResponses=orderMapper.selectShopOrderCountResponseByDate(startTime,endTime,createId);
		return new PageInfo<>(orderCountResponses);
	}

	/**
	 * 查询指定月的流水统计
	 * @param newTime
	 * @return
	 */
	public List<PlatformCostCensus> selectPlatformCostCensus(String newTime,Map<String,Object> map) {
		Integer createId=map.containsKey("createId")?(Integer) map.get("createId"):null;
		return orderMapper.selectPlatformCostCensus(newTime,createId);
	}

	/**
	 * 查询上月订单统计
	 * @param upMonth
	 * @return
	 */
	public OrderPieResponse selectOrderPieResponse(String upMonth,Map<String,Object> map) {
		Integer createId=map.containsKey("createId")?(Integer) map.get("createId"):null;
		return orderMapper.selectOrderPieResponse(upMonth,createId);
	}

	/**
	 * 查询商家订单
	 * @param shopId
	 * @return
	 */
	public List<ShopOrderResponse> selectShopOrderResponse(Integer shopId,Integer status,Integer merId) {
		return orderMapper.selectShopOrderResponse(shopId,status,merId);
	}
}
