package com.rainfir.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class ItemModel {
    //商品id
    private Integer id;
    //商品名称
    @NotBlank(message = "商品名称不能为空")
    private String name;
    //商品描述
    @NotBlank(message = "商品描述不能为空")
    private String description;
    //商品价格
    @NotNull(message = "商品价格不能为空")
    @Min(value = 0,message = "商品价格不能小于0")
    private BigDecimal price;
    //商品销量
    private Integer sales;
    //商品库存
    @NotNull(message = "商品库存不能为空")
    @Min(value = 0,message = "商品库存不能小于0")
    private Integer stock;
    //商品图片url
    @NotBlank(message = "商品图片不能为空")
    private String imgUrl;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
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

}
