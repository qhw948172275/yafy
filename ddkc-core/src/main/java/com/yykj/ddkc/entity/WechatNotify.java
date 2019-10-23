package com.yykj.ddkc.entity;

import java.util.Map;

import javax.persistence.*;

@Table(name = "t_wechat_notify")
public class WechatNotify {
    /**
     * 自增id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 公众号id
     */
    private String appid;

    /**
     * 商户号
     */
    @Column(name = "mch_id")
    private String mchId;

    /**
     * 微信支付分配的终端设备号，
     */
    @Column(name = "device_info")
    private String deviceInfo;

    /**
     * 随机字符串，不长于32位
     */
    @Column(name = "nonce_str")
    private String nonceStr;

    /**
     * 签名
     */
    private String sign;

    /**
     * 签名类型，默认MD5
     */
    @Column(name = "sign_type")
    private String signType;

    /**
     * SUCCESS/FAIL
     */
    @Column(name = "result_code")
    private String resultCode;

    /**
     * 错误返回的信息描述
     */
    @Column(name = "err_code")
    private String errCode;

    /**
     * 错误返回的信息描述
     */
    @Column(name = "err_code_des")
    private String errCodeDes;

    /**
     * 用户在商户appid下的唯一标识
     */
    private String openid;

    /**
     * 用户是否关注公众账号，Y-关注，N-未关注，仅在公众账号类型支付有效
     */
    @Column(name = "is_subscribe")
    private String isSubscribe;

    /**
     * 交易类型
     */
    @Column(name = "trade_type")
    private String tradeType;

    /**
     * 付款银行
     */
    @Column(name = "bank_type")
    private String bankType;

    /**
     * 总金额
     */
    @Column(name = "total_fee")
    private Integer totalFee;

    /**
     * 货币种类。货币类型，符合ISO4217标准的三位字母代码，默认人民币：CNY
     */
    @Column(name = "fee_type")
    private String feeType;

    /**
     * 现金支付金额
     */
    @Column(name = "cash_fee")
    private Integer cashFee;

    /**
     * 现金支付货币类型。 货币类型，符合ISO4217标准的三位字母代码，默认人民币：CNY
     */
    @Column(name = "cash_fee_type")
    private String cashFeeType;

    /**
     * 代金券或立减优惠金额
     */
    @Column(name = "coupon_fee")
    private Integer couponFee;

    /**
     * 代金券或立减优惠使用数量
     */
    @Column(name = "coupon_count")
    private Integer couponCount;

    /**
     * 代金券或立减优惠ID
     */
    @Column(name = "coupon_id_no")
    private String couponIdNo;

    /**
     * 单个代金券或立减优惠支付金额
     */
    @Column(name = "coupon_fee_no")
    private Integer couponFeeNo;

    /**
     * 微信订单号ID
     */
    @Column(name = "transaction_id")
    private String transactionId;

    /**
     * 商户订单号
     */
    @Column(name = "out_trade_no")
    private String outTradeNo;

    /**
     * 商家数据包
     */
    private String attach;

    /**
     * 支付完成时间
     */
    @Column(name = "time_end")
    private String timeEnd;

    /**
     * 获取自增id
     *
     * @return id - 自增id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置自增id
     *
     * @param id 自增id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取公众号id
     *
     * @return appid - 公众号id
     */
    public String getAppid() {
        return appid;
    }

    /**
     * 设置公众号id
     *
     * @param appid 公众号id
     */
    public void setAppid(String appid) {
        this.appid = appid == null ? null : appid.trim();
    }

    /**
     * 获取商户号
     *
     * @return mch_id - 商户号
     */
    public String getMchId() {
        return mchId;
    }

    /**
     * 设置商户号
     *
     * @param mchId 商户号
     */
    public void setMchId(String mchId) {
        this.mchId = mchId == null ? null : mchId.trim();
    }

    /**
     * 获取微信支付分配的终端设备号，
     *
     * @return device_info - 微信支付分配的终端设备号，
     */
    public String getDeviceInfo() {
        return deviceInfo;
    }

    /**
     * 设置微信支付分配的终端设备号，
     *
     * @param deviceInfo 微信支付分配的终端设备号，
     */
    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo == null ? null : deviceInfo.trim();
    }

    /**
     * 获取随机字符串，不长于32位
     *
     * @return nonce_str - 随机字符串，不长于32位
     */
    public String getNonceStr() {
        return nonceStr;
    }

    /**
     * 设置随机字符串，不长于32位
     *
     * @param nonceStr 随机字符串，不长于32位
     */
    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr == null ? null : nonceStr.trim();
    }

    /**
     * 获取签名
     *
     * @return sign - 签名
     */
    public String getSign() {
        return sign;
    }

    /**
     * 设置签名
     *
     * @param sign 签名
     */
    public void setSign(String sign) {
        this.sign = sign == null ? null : sign.trim();
    }

    /**
     * 获取签名类型，默认MD5
     *
     * @return sign_type - 签名类型，默认MD5
     */
    public String getSignType() {
        return signType;
    }

    /**
     * 设置签名类型，默认MD5
     *
     * @param signType 签名类型，默认MD5
     */
    public void setSignType(String signType) {
        this.signType = signType == null ? null : signType.trim();
    }

    /**
     * 获取SUCCESS/FAIL
     *
     * @return result_code - SUCCESS/FAIL
     */
    public String getResultCode() {
        return resultCode;
    }

    /**
     * 设置SUCCESS/FAIL
     *
     * @param resultCode SUCCESS/FAIL
     */
    public void setResultCode(String resultCode) {
        this.resultCode = resultCode == null ? null : resultCode.trim();
    }

    /**
     * 获取错误返回的信息描述
     *
     * @return err_code - 错误返回的信息描述
     */
    public String getErrCode() {
        return errCode;
    }

    /**
     * 设置错误返回的信息描述
     *
     * @param errCode 错误返回的信息描述
     */
    public void setErrCode(String errCode) {
        this.errCode = errCode == null ? null : errCode.trim();
    }

    /**
     * 获取错误返回的信息描述
     *
     * @return err_code_des - 错误返回的信息描述
     */
    public String getErrCodeDes() {
        return errCodeDes;
    }

    /**
     * 设置错误返回的信息描述
     *
     * @param errCodeDes 错误返回的信息描述
     */
    public void setErrCodeDes(String errCodeDes) {
        this.errCodeDes = errCodeDes == null ? null : errCodeDes.trim();
    }

    /**
     * 获取用户在商户appid下的唯一标识
     *
     * @return openid - 用户在商户appid下的唯一标识
     */
    public String getOpenid() {
        return openid;
    }

    /**
     * 设置用户在商户appid下的唯一标识
     *
     * @param openid 用户在商户appid下的唯一标识
     */
    public void setOpenid(String openid) {
        this.openid = openid == null ? null : openid.trim();
    }

    /**
     * 获取用户是否关注公众账号，Y-关注，N-未关注，仅在公众账号类型支付有效
     *
     * @return is_subscribe - 用户是否关注公众账号，Y-关注，N-未关注，仅在公众账号类型支付有效
     */
    public String getIsSubscribe() {
        return isSubscribe;
    }

    /**
     * 设置用户是否关注公众账号，Y-关注，N-未关注，仅在公众账号类型支付有效
     *
     * @param isSubscribe 用户是否关注公众账号，Y-关注，N-未关注，仅在公众账号类型支付有效
     */
    public void setIsSubscribe(String isSubscribe) {
        this.isSubscribe = isSubscribe == null ? null : isSubscribe.trim();
    }

    /**
     * 获取交易类型
     *
     * @return trade_type - 交易类型
     */
    public String getTradeType() {
        return tradeType;
    }

    /**
     * 设置交易类型
     *
     * @param tradeType 交易类型
     */
    public void setTradeType(String tradeType) {
        this.tradeType = tradeType == null ? null : tradeType.trim();
    }

    /**
     * 获取付款银行
     *
     * @return bank_type - 付款银行
     */
    public String getBankType() {
        return bankType;
    }

    /**
     * 设置付款银行
     *
     * @param bankType 付款银行
     */
    public void setBankType(String bankType) {
        this.bankType = bankType == null ? null : bankType.trim();
    }

    /**
     * 获取总金额
     *
     * @return total_fee - 总金额
     */
    public Integer getTotalFee() {
        return totalFee;
    }

    /**
     * 设置总金额
     *
     * @param totalFee 总金额
     */
    public void setTotalFee(Integer totalFee) {
        this.totalFee = totalFee;
    }

    /**
     * 获取货币种类。货币类型，符合ISO4217标准的三位字母代码，默认人民币：CNY
     *
     * @return fee_type - 货币种类。货币类型，符合ISO4217标准的三位字母代码，默认人民币：CNY
     */
    public String getFeeType() {
        return feeType;
    }

    /**
     * 设置货币种类。货币类型，符合ISO4217标准的三位字母代码，默认人民币：CNY
     *
     * @param feeType 货币种类。货币类型，符合ISO4217标准的三位字母代码，默认人民币：CNY
     */
    public void setFeeType(String feeType) {
        this.feeType = feeType == null ? null : feeType.trim();
    }

    /**
     * 获取现金支付金额
     *
     * @return cash_fee - 现金支付金额
     */
    public Integer getCashFee() {
        return cashFee;
    }

    /**
     * 设置现金支付金额
     *
     * @param cashFee 现金支付金额
     */
    public void setCashFee(Integer cashFee) {
        this.cashFee = cashFee;
    }

    /**
     * 获取现金支付货币类型。 货币类型，符合ISO4217标准的三位字母代码，默认人民币：CNY
     *
     * @return cash_fee_type - 现金支付货币类型。 货币类型，符合ISO4217标准的三位字母代码，默认人民币：CNY
     */
    public String getCashFeeType() {
        return cashFeeType;
    }

    /**
     * 设置现金支付货币类型。 货币类型，符合ISO4217标准的三位字母代码，默认人民币：CNY
     *
     * @param cashFeeType 现金支付货币类型。 货币类型，符合ISO4217标准的三位字母代码，默认人民币：CNY
     */
    public void setCashFeeType(String cashFeeType) {
        this.cashFeeType = cashFeeType == null ? null : cashFeeType.trim();
    }

    /**
     * 获取代金券或立减优惠金额
     *
     * @return coupon_fee - 代金券或立减优惠金额
     */
    public Integer getCouponFee() {
        return couponFee;
    }

    /**
     * 设置代金券或立减优惠金额
     *
     * @param couponFee 代金券或立减优惠金额
     */
    public void setCouponFee(Integer couponFee) {
        this.couponFee = couponFee;
    }

    /**
     * 获取代金券或立减优惠使用数量
     *
     * @return coupon_count - 代金券或立减优惠使用数量
     */
    public Integer getCouponCount() {
        return couponCount;
    }

    /**
     * 设置代金券或立减优惠使用数量
     *
     * @param couponCount 代金券或立减优惠使用数量
     */
    public void setCouponCount(Integer couponCount) {
        this.couponCount = couponCount;
    }

    /**
     * 获取代金券或立减优惠ID
     *
     * @return coupon_id_no - 代金券或立减优惠ID
     */
    public String getCouponIdNo() {
        return couponIdNo;
    }

    /**
     * 设置代金券或立减优惠ID
     *
     * @param couponIdNo 代金券或立减优惠ID
     */
    public void setCouponIdNo(String couponIdNo) {
        this.couponIdNo = couponIdNo == null ? null : couponIdNo.trim();
    }

    /**
     * 获取单个代金券或立减优惠支付金额
     *
     * @return coupon_fee_no - 单个代金券或立减优惠支付金额
     */
    public Integer getCouponFeeNo() {
        return couponFeeNo;
    }

    /**
     * 设置单个代金券或立减优惠支付金额
     *
     * @param couponFeeNo 单个代金券或立减优惠支付金额
     */
    public void setCouponFeeNo(Integer couponFeeNo) {
        this.couponFeeNo = couponFeeNo;
    }

    /**
     * 获取微信订单号ID
     *
     * @return transaction_id - 微信订单号ID
     */
    public String getTransactionId() {
        return transactionId;
    }

    /**
     * 设置微信订单号ID
     *
     * @param transactionId 微信订单号ID
     */
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId == null ? null : transactionId.trim();
    }

    /**
     * 获取商户订单号
     *
     * @return out_trade_no - 商户订单号
     */
    public String getOutTradeNo() {
        return outTradeNo;
    }

    /**
     * 设置商户订单号
     *
     * @param outTradeNo 商户订单号
     */
    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo == null ? null : outTradeNo.trim();
    }

    /**
     * 获取商家数据包
     *
     * @return attach - 商家数据包
     */
    public String getAttach() {
        return attach;
    }

    /**
     * 设置商家数据包
     *
     * @param attach 商家数据包
     */
    public void setAttach(String attach) {
        this.attach = attach == null ? null : attach.trim();
    }

    /**
     * 获取支付完成时间
     *
     * @return time_end - 支付完成时间
     */
    public String getTimeEnd() {
        return timeEnd;
    }

    /**
     * 设置支付完成时间
     *
     * @param timeEnd 支付完成时间
     */
    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd == null ? null : timeEnd.trim();
    }
    public WechatNotify() {}
    public WechatNotify(Map<String, String> returnMap) {

		this.appid = returnMap.get("appid");
		this.mchId = returnMap.get("mch_id");
		this.deviceInfo = returnMap.get("device_info");
		this.nonceStr = returnMap.get("nonce_str");
		this.sign = returnMap.get("sign");
		this.resultCode = returnMap.get("result_code");
		this.errCode = returnMap.get("err_code");
		this.errCodeDes = returnMap.get("err_code_des");
		this.openid = returnMap.get("openid");
		this.isSubscribe = returnMap.get("is_subscribe");
		this.tradeType = returnMap.get("trade_type");
		this.bankType = returnMap.get("bank_type");
		this.totalFee = Integer.parseInt(returnMap.get("total_fee"));
		this.feeType = returnMap.get("fee_type");
		this.cashFee = returnMap.containsKey("cash_fee") ? Integer.parseInt(returnMap.get("cash_fee")) : null;
		this.cashFeeType = returnMap.get("case_fee_type");
		this.couponFee = returnMap.containsKey("coupon_fee") ? Integer.parseInt(returnMap.get("coupon_fee")) : null;
		this.couponCount = returnMap.containsKey("coupon_count") ? Integer.parseInt(returnMap.get("coupon_count"))
				: null;
		// this.couponIdNo = returnMap.get("");
		// this.couponFeeNo = returnMap.get("");
		this.transactionId = returnMap.get("transaction_id");
		this.outTradeNo = returnMap.get("out_trade_no");
		this.attach = returnMap.get("attach");
		this.timeEnd = returnMap.get("time_end");
	}
}