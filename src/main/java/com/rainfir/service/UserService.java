package com.rainfir.service;

import com.rainfir.controller.viewobject.UserVO;
import com.rainfir.error.BusinessException;
import com.rainfir.model.UserModel;

public interface UserService {
    UserModel getUserById(Integer id);
    void register(UserModel userModel) throws BusinessException;
    UserModel login(String telphone,String password) throws BusinessException;
    //根据手机号查询用户
    UserModel getUserByTelphone(String telphone);
}
