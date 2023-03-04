package com.rainfir.service.impl;

import com.rainfir.dao.ItemDOMapper;
import com.rainfir.dao.ItemStockDOMapper;
import com.rainfir.dataobject.ItemDO;
import com.rainfir.dataobject.ItemStockDO;
import com.rainfir.error.BusinessException;
import com.rainfir.error.EmBusinessError;
import com.rainfir.model.ItemModel;
import com.rainfir.service.ItemService;
import com.rainfir.validator.ValidationResult;
import com.rainfir.validator.ValidatorImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ValidatorImpl validator;
    @Autowired
    private ItemDOMapper itemDOMapper;
    @Autowired
    private ItemStockDOMapper itemStockDOMapper;

    @Override
    public List<ItemModel> getAllItems() {
        List<ItemDO> itemDOs = itemDOMapper.selectAll();
        List<ItemModel> itemModelList = itemDOs.stream().map(itemDO -> {
            ItemModel itemModel = this.convertFromDataObject(itemDO, itemStockDOMapper.selectByItemId(itemDO.getId()));
            return itemModel;
        }).collect(Collectors.toList());
        return itemModelList;
    }

    @Override
    public ItemModel create(ItemModel itemModel) throws BusinessException {
        //判空
        if(itemModel==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        //校验
        ValidationResult result = validator.validate(itemModel);
        if(result.isHasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
        }
        //model-->do,创建
        ItemDO itemDO = convertFromItemModel(itemModel);
        itemDO.setPrice(itemModel.getPrice().doubleValue());//防止精度丢失
        itemDOMapper.insertSelective(itemDO);
        itemModel.setId(itemDO.getId());
        ItemStockDO itemStockDO = convertItemStockFromItemModel(itemModel);
        itemStockDOMapper.insertSelective(itemStockDO);

        //返回创建的对象
        return getItemById(itemDO.getId());
    }
    private ItemModel convertFromDataObject(ItemDO itemDO,ItemStockDO itemStockDO){
        if(itemDO==null) return null;
        ItemModel itemModel = new ItemModel();
        BeanUtils.copyProperties(itemDO,itemModel);
        itemModel.setPrice(new BigDecimal(itemDO.getPrice()));
        if(itemStockDO==null) return null;
        itemModel.setStock(itemStockDO.getStock());

        return itemModel;
    }

    private ItemStockDO convertItemStockFromItemModel(ItemModel itemModel){
        if (itemModel==null) return null;
        ItemStockDO itemStockDO = new ItemStockDO();
        itemStockDO.setItemId(itemModel.getId());
        itemStockDO.setStock(itemModel.getStock());
        return itemStockDO;
    }

    private ItemDO convertFromItemModel(ItemModel itemModel){
        if(itemModel==null) return null;
        ItemDO itemDO = new ItemDO();
        BeanUtils.copyProperties(itemModel,itemDO);
        return itemDO;
    }

    @Override
    public ItemModel getItemById(Integer id) {
        ItemDO itemDO = itemDOMapper.selectByPrimaryKey(id);
        if(itemDO==null) return null;
        ItemStockDO itemStockDO = itemStockDOMapper.selectByItemId(itemDO.getId());
        if(itemStockDO==null) return null;
        return convertFromDataObject(itemDO,itemStockDO);
    }
}
