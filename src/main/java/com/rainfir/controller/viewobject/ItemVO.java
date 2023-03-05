package com.rainfir.controller.viewobject;

import org.joda.time.DateTime;

import javax.validation.constraints.Min;
import java.math.BigDecimal;

public class ItemVO {
    //商品id
    private Integer id;
    //商品名称
    private String name;
    //商品描述
    private String description;
    //商品价格
    private BigDecimal price;
    //商品销量
    private Integer sales;
    //商品库存
    private Integer stock;
    //商品图片url
    private String imgUrl;
    //记录商品是否在秒杀活动中，以及对应的状态0：表示没有秒杀活动，1：表示秒杀活动待开始，2：表示秒杀活动正在进行
    private Integer promoStatus;
    //秒杀价格
    private BigDecimal promoPrice;
    //秒杀活动ID
    private Integer promoId;
    //秒杀活动开始时间
    private String promoStartDate;

    public Integer getPromoStatus() {
        return promoStatus;
    }

    public void setPromoStatus(Integer promoStatus) {
        this.promoStatus = promoStatus;
    }

    public BigDecimal getPromoPrice() {
        return promoPrice;
    }

    public void setPromoPrice(BigDecimal promoPrice) {
        this.promoPrice = promoPrice;
    }

    public Integer getPromoId() {
        return promoId;
    }

    public void setPromoId(Integer promoId) {
        this.promoId = promoId;
    }

    public String getPromoStartDate() {
        return promoStartDate;
    }

    public void setPromoStartDate(String promoStartDate) {
        this.promoStartDate = promoStartDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
