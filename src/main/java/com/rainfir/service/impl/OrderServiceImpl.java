package com.rainfir.service.impl;

import com.rainfir.dao.OrderDOMapper;
import com.rainfir.dao.SequenceDOMapper;
import com.rainfir.dataobject.OrderDO;
import com.rainfir.dataobject.SequenceDO;
import com.rainfir.dataobject.UserDO;
import com.rainfir.error.BusinessException;
import com.rainfir.error.EmBusinessError;
import com.rainfir.model.ItemModel;
import com.rainfir.model.OrderModel;
import com.rainfir.model.UserModel;
import com.rainfir.service.ItemService;
import com.rainfir.service.OrderService;
import com.rainfir.service.SequenceService;
import com.rainfir.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ItemService itemService;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderDOMapper orderDOMapper;
    @Autowired
    private SequenceService sequenceService;
    @Override
    @Transactional
    public OrderModel create(Integer userId, Integer itemId, Integer amount) throws BusinessException {
        //1.校验下单状态，下单的商品是否存在，用户是否合法，购买数量是否正确
        ItemModel itemModel = itemService.getItemById(itemId);
        if (itemModel==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"商品信息不存在");
        }
        UserModel userModel = userService.getUserById(userId);
        if (userModel==null){
            throw new BusinessException(EmBusinessError.USER_NOT_EXIST);
        }
        if (amount<=0||amount>99){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"数量信息不正确");
        }
        //2.落单减库存,加销量
        boolean result = itemService.decreaseStock(itemId, amount);
        if (!result){
            throw new BusinessException(EmBusinessError.STOCK_NOT_ENOUGH);
        }
        itemService.increaseSales(itemId,amount);
        //3.订单入库
        OrderModel orderModel = new OrderModel();
        orderModel.setAmount(amount);
        orderModel.setItemId(itemId);
        orderModel.setPrice(itemModel.getPrice());
        orderModel.setAmountPrice(itemModel.getPrice().multiply(new BigDecimal(amount)));
        orderModel.setUserId(userId);
        //生成交易流水号
        String orderId = sequenceService.generateOrderId();
        orderModel.setId(orderId);

        //model-->dao,并存入数据库
        OrderDO orderDO = convertFromOrderModel(orderModel);
        orderDOMapper.insertSelective(orderDO);
        //4.返回前端
        return orderModel;
    }

    private OrderDO convertFromOrderModel(OrderModel orderModel){
        OrderDO orderDO = new OrderDO();
        BeanUtils.copyProperties(orderModel,orderDO);
        orderDO.setPrice(orderModel.getPrice().doubleValue());
        orderDO.setAmountPrice(orderModel.getAmountPrice().doubleValue());
        return orderDO;
    }

}
