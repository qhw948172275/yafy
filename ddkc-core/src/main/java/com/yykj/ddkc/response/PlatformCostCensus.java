package com.yykj.ddkc.response;

import java.math.BigDecimal;

public class PlatformCostCensus {
    /**
     * 号数
     */
    private Integer dateDay;
    /**
     * 流水金额
     */
    private  BigDecimal amount;

    public Integer getDateDay() {
        return dateDay;
    }

    public void setDateDay(Integer dateDay) {
        this.dateDay = dateDay;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
