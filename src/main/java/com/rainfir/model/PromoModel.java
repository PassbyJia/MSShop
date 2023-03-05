package com.rainfir.model;

import org.joda.time.DateTime;

import java.math.BigDecimal;

public class PromoModel {
    //活动id
    private Integer id;
    //活动开始时间
    private DateTime startDate;
    //活动结束时间
    private DateTime endDate;
    //活动价格
    private BigDecimal promoPrice;
    //活动商品
    private Integer itemId;
    //活动状态 1 还未开始 2 正在进行 3 已经结束
    private Integer promoStatus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public DateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(DateTime startDate) {
        this.startDate = startDate;
    }

    public DateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(DateTime endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getPromoPrice() {
        return promoPrice;
    }

    public void setPromoPrice(BigDecimal promoPrice) {
        this.promoPrice = promoPrice;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getPromoStatus() {
        return promoStatus;
    }

    public void setPromoStatus(Integer promoStatus) {
        this.promoStatus = promoStatus;
    }
}
