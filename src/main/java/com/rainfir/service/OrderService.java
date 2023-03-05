package com.rainfir.service;

import com.rainfir.error.BusinessException;
import com.rainfir.model.OrderModel;

public interface OrderService {
    OrderModel create(Integer userId,Integer itemId,Integer amount) throws BusinessException;
}
