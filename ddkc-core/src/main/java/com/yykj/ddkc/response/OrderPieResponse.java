package com.yykj.ddkc.response;

/**
 * 订单饼图实体类
 */
public class OrderPieResponse {
    /**
     * 支付订单
     */
    private Integer payOrder;
    /**
     *取消订单
     */
    private Integer cancelOrder;
    /**
     *完成订单
     */
    private Integer finishOrder;

    public Integer getPayOrder() {
        return payOrder;
    }

    public void setPayOrder(Integer payOrder) {
        this.payOrder = payOrder;
    }

    public Integer getCancelOrder() {
        return cancelOrder;
    }

    public void setCancelOrder(Integer cancelOrder) {
        this.cancelOrder = cancelOrder;
    }

    public Integer getFinishOrder() {
        return finishOrder;
    }

    public void setFinishOrder(Integer finishOrder) {
        this.finishOrder = finishOrder;
    }

    public OrderPieResponse(){
        super();
    }

    /**
     * 为空后都为0
     * @param type
     */
    public OrderPieResponse(Integer type){
        if(type==0){
            this.payOrder=0;
            this.finishOrder=0;
            this.cancelOrder=0;
        }
    }
}
