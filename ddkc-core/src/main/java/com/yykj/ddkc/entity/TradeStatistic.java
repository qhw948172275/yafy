package com.yykj.ddkc.entity;

import javax.persistence.*;

@Table(name = "t_trade_statistic")
public class TradeStatistic {
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * @return Id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }
}