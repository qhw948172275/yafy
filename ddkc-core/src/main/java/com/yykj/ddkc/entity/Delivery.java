package com.yykj.ddkc.entity;

import javax.persistence.*;

@Table(name = "t_delivery")
public class Delivery {
    /**
     * 配送设置ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 配送类型。0表示商家配送费每单多少元；1表示平台抽成每单多少元；2表示配送范围周围多少米；
     */
    private Integer type;

    /**
     * 配送类型对应值
     */
    private String val;

    /**
     * 获取配送设置ID
     *
     * @return id - 配送设置ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置配送设置ID
     *
     * @param id 配送设置ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取配送类型。0表示商家配送费每单多少元；1表示平台抽成每单多少元；2表示配送范围周围多少米；
     *
     * @return type - 配送类型。0表示商家配送费每单多少元；1表示平台抽成每单多少元；2表示配送范围周围多少米；
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置配送类型。0表示商家配送费每单多少元；1表示平台抽成每单多少元；2表示配送范围周围多少米；
     *
     * @param type 配送类型。0表示商家配送费每单多少元；1表示平台抽成每单多少元；2表示配送范围周围多少米；
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取配送类型对应值
     *
     * @return val - 配送类型对应值
     */
    public String getVal() {
        return val;
    }

    /**
     * 设置配送类型对应值
     *
     * @param val 配送类型对应值
     */
    public void setVal(String val) {
        this.val = val == null ? null : val.trim();
    }
}