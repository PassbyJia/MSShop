package com.rainfir.service.impl;

import com.rainfir.dao.ItemDOMapper;
import com.rainfir.dao.ItemStockDOMapper;
import com.rainfir.dataobject.ItemDO;
import com.rainfir.dataobject.ItemStockDO;
import com.rainfir.error.BusinessException;
import com.rainfir.error.EmBusinessError;
import com.rainfir.model.ItemModel;
import com.rainfir.model.PromoModel;
import com.rainfir.service.ItemService;
import com.rainfir.service.PromoService;
import com.rainfir.validator.ValidationResult;
import com.rainfir.validator.ValidatorImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Autowired
    private PromoService promoService;

    @Override
    public List<ItemModel> getAllItems() {
        List<ItemDO> itemDOs = itemDOMapper.selectAll();
        List<ItemModel> itemModelList = itemDOs.stream().map(itemDO -> {
            ItemModel itemModel = this.convertFromDataObject(itemDO, itemStockDOMapper.selectByItemId(itemDO.getId()));
            //获取该商品的活动信息
            itemModel.setPromoModel(promoService.getPromoByItemId(itemModel.getId()));
            return itemModel;
        }).collect(Collectors.toList());
        return itemModelList;
    }

    @Override
    @Transactional
    public boolean decreaseStock(Integer itemId, Integer amount) {
        int affectedRow = itemStockDOMapper.decreaseStock(itemId, amount);
        if (affectedRow>0){
            //更新库存成功
            return true;
        }else{
            //更新库存失败
            return false;
        }

    }

    @Override
    @Transactional
    public void increaseSales(Integer itemId, Integer amount) {
        itemDOMapper.increaseSales(itemId,amount);
    }

    @Override
    @Transactional
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
        ItemModel itemModel = convertFromDataObject(itemDO, itemStockDO);
        //获取活动信息
        PromoModel promoModel = promoService.getPromoByItemId(itemModel.getId());
        if(promoModel==null||promoModel.getPromoStatus()==3){
            itemModel.setPromoModel(null);
        }else{
            itemModel.setPromoModel(promoModel);
        }
        return itemModel;
    }
}
