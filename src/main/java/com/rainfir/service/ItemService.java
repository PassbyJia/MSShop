package com.rainfir.service;

import com.rainfir.error.BusinessException;
import com.rainfir.model.ItemModel;

import java.util.List;

public interface ItemService {
    //创建商品
    ItemModel create (ItemModel itemModel) throws BusinessException;
    //获取商品
    ItemModel getItemById(Integer id);
    //生成商品列表
    List<ItemModel> getAllItems();
}
