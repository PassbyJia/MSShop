package com.rainfir.controller;

import com.rainfir.error.BusinessException;
import com.rainfir.error.EmBusinessError;
import com.rainfir.model.OrderModel;
import com.rainfir.model.UserModel;
import com.rainfir.response.CommonReturnType;
import com.rainfir.service.OrderService;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller("order")
@RequestMapping("/order")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class OrderController extends BaseController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private HttpServletRequest httpServletRequest;
    //商品下单接口
    @RequestMapping(value = "/order",method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType order(@RequestParam("itemId")Integer itemId,
                                  @RequestParam("amount")Integer amount) throws BusinessException {
        Boolean isLogin = (Boolean)httpServletRequest.getSession().getAttribute("IS_LOGIN");
        if (isLogin==null||!isLogin){
            throw new BusinessException(EmBusinessError.USER_NOT_LOGIN);
        }
        UserModel loginUser = (UserModel) httpServletRequest.getSession().getAttribute("LOGIN_USER");
        OrderModel orderModel = orderService.create(loginUser.getId(), itemId, amount);
        return CommonReturnType.create(null);
    }

}
