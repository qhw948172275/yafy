package com.yykj.ddkc.response;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public class OrderProcessResponse {
    /**
     * 订单创建时间
     */
    @ApiModelProperty(value = "订单创建时间")
    private Date createTime;

    /**
     * 支付时间
     */
    @ApiModelProperty(value = "支付时间")
    private Date payTime;
    /**
     * 取消订单时间
     */
    @ApiModelProperty(value = "取消订单时间")
    private Date cannelTime;
    /**
     * 接单时间
     */
    @ApiModelProperty(value = "接单时间")
    private Date receiptTime;
    /**
     * 送达时间
     */
    @ApiModelProperty(value = "送达时间")
    private Date takeOverTime;
    /**
     * 退款时间
     */
    @ApiModelProperty(value = "退款时间")
    private Date refundTime;



    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Date getReceiptTime() {
        return receiptTime;
    }

    public void setReceiptTime(Date receiptTime) {
        this.receiptTime = receiptTime;
    }

    public Date getTakeOverTime() {
        return takeOverTime;
    }

    public void setTakeOverTime(Date takeOverTime) {
        this.takeOverTime = takeOverTime;
    }

    public Date getCannelTime() {
        return cannelTime;
    }

    public void setCannelTime(Date cannelTime) {
        this.cannelTime = cannelTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getRefundTime() {
        return refundTime;
    }

    public void setRefundTime(Date refundTime) {
        this.refundTime = refundTime;
    }
}
