package com.rainfir.model;

import java.math.BigDecimal;

public class OrderModel {
    //订单号（16位：前8位时间，中6位自增序列，后两位分库分表码暂时写死为00）
    private String id;
    //用户id
    private Integer userId;
    //商品id
    private Integer itemId;
    //购买时单价,若promoId不为空则为秒杀商品价格
    private BigDecimal price;
    //购买数量
    private Integer amount;
    //购买总额
    private BigDecimal amountPrice;
    //若非空，则表示是以秒杀商品方式下单
    private Integer promoId;

    public Integer getPromoId() {
        return promoId;
    }

    public void setPromoId(Integer promoId) {
        this.promoId = promoId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public BigDecimal getAmountPrice() {
        return amountPrice;
    }

    public void setAmountPrice(BigDecimal amountPrice) {
        this.amountPrice = amountPrice;
    }
}
