package com.rainfir.service.impl;

import com.rainfir.controller.viewobject.UserVO;
import com.rainfir.dao.UserDOMapper;
import com.rainfir.dao.UserPwdDOMapper;
import com.rainfir.dataobject.UserDO;
import com.rainfir.dataobject.UserPwdDO;
import com.rainfir.model.UserModel;
import com.rainfir.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDOMapper userDOMapper;
    @Autowired
    private UserPwdDOMapper userPwdDOMapper;

    @Override
    public UserModel getUserById(Integer id) {
        UserDO userDO = userDOMapper.selectByPrimaryKey(id);
        if(userDO==null) return null;
        UserPwdDO userPwdDO = userPwdDOMapper.selectByUserId(userDO.getId());

        return convertFromDataObject(userDO,userPwdDO);
    }



    private UserModel convertFromDataObject(UserDO userDO, UserPwdDO userPwdDO){
        if(userDO==null) return null;
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userDO,userModel);
        if(userPwdDO!=null)
            userModel.setEncrptPassword(userPwdDO.getEncrptPassword());
        return userModel;
    }
}
