package com.rainfir.controller.viewobject;

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
