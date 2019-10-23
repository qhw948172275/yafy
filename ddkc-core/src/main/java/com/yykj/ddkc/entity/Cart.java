package com.yykj.ddkc.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.yykj.system.commons.CalendarUtils;
import com.yykj.system.commons.SystemConstants;

@Table(name = "t_cart")
public class Cart {
	
	public Cart() {}
	
	
    public Cart( Integer memberId, Integer shopId, Integer goodsId, Long counts) {
		this.memberId = memberId;
		this.shopId = shopId;
		this.goodsId = goodsId;
		this.counts = counts;
		this.status = SystemConstants.STATUS_OK;
		this.createTime = CalendarUtils.getDate();
	}


	/**
     * 购物车ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 会员ID
     */
    @Column(name = "member_id")
    private Integer memberId;

    /**
     * 店铺ID
     */
    @Column(name = "shop_id")
    private Integer shopId;

    /**
     * 商品ID
     */
    @Column(name = "goods_id")
    private Integer goodsId;

    /**
     * 数量
     */
    private Long counts;

    /**
     * 购物车状态。0表示正常；1表示已删除
     */
    private Integer status;

    /**
     * 加入购物车时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 获取购物车ID
     *
     * @return id - 购物车ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置购物车ID
     *
     * @param id 购物车ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取会员ID
     *
     * @return member_id - 会员ID
     */
    public Integer getMemberId() {
        return memberId;
    }

    /**
     * 设置会员ID
     *
     * @param memberId 会员ID
     */
    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    /**
     * 获取店铺ID
     *
     * @return shop_id - 店铺ID
     */
    public Integer getShopId() {
        return shopId;
    }

    /**
     * 设置店铺ID
     *
     * @param shopId 店铺ID
     */
    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    /**
     * 获取商品ID
     *
     * @return goods_id - 商品ID
     */
    public Integer getGoodsId() {
        return goodsId;
    }

    /**
     * 设置商品ID
     *
     * @param goodsId 商品ID
     */
    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    /**
     * 获取数量
     *
     * @return counts - 数量
     */
    public Long getCounts() {
        if(this.counts==null){
            return 0L;
        }
        return counts;
    }

    /**
     * 设置数量
     *
     * @param counts 数量
     */
    public void setCounts(Long counts) {
        this.counts = counts;
    }

    /**
     * 获取购物车状态。0表示正常；1表示已删除
     *
     * @return status - 购物车状态。0表示正常；1表示已删除
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置购物车状态。0表示正常；1表示已删除
     *
     * @param status 购物车状态。0表示正常；1表示已删除
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取加入购物车时间
     *
     * @return create_time - 加入购物车时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置加入购物车时间
     *
     * @param createTime 加入购物车时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}