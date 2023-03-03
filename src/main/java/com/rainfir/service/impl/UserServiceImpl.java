package com.rainfir.service.impl;

import com.rainfir.controller.viewobject.UserVO;
import com.rainfir.dao.UserDOMapper;
import com.rainfir.dao.UserPwdDOMapper;
import com.rainfir.dataobject.UserDO;
import com.rainfir.dataobject.UserPwdDO;
import com.rainfir.error.BusinessException;
import com.rainfir.error.EmBusinessError;
import com.rainfir.model.UserModel;
import com.rainfir.service.UserService;
import com.rainfir.validator.ValidationResult;
import com.rainfir.validator.ValidatorImpl;
import org.apache.catalina.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDOMapper userDOMapper;
    @Autowired
    private UserPwdDOMapper userPwdDOMapper;
    @Autowired
    private ValidatorImpl validator;

    @Override
    public UserModel getUserById(Integer id) {
        UserDO userDO = userDOMapper.selectByPrimaryKey(id);
        if(userDO==null) return null;
        UserPwdDO userPwdDO = userPwdDOMapper.selectByUserId(userDO.getId());

        return convertFromDataObject(userDO,userPwdDO);
    }

    @Override
    @Transactional//插入两个表的动作必须同时完成
    public void register(UserModel userModel) throws BusinessException {
        //判空
        if (userModel==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
//        if (StringUtils.isEmpty(userModel.getEncrptPassword())
//                || StringUtils.isEmpty(userModel.getName())
//                || StringUtils.isEmpty(userModel.getTelphone())
//                || userModel.getGender()==null
//                || userModel.getAge()==null){
//            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"用户数据不全");
//        }
        ValidationResult result = validator.validate(userModel);
        if(result.isHasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
        }
        //调用DO层方法将用户数据存入数据库
        UserDO userDO = convertFromUserModel(userModel);
        userDOMapper.insertSelective(userDO);
        userModel.setId(userDO.getId());
        UserPwdDO userPwdDO = convertUserPwdDOFromUserModel(userModel);
        userPwdDOMapper.insertSelective(userPwdDO);

        return;
    }

    @Override
    public UserModel login(String telphone, String password) throws BusinessException {
        //获取数据库对象
        UserDO userDO = userDOMapper.selectByTelphone(telphone);
        if(userDO==null){
            throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL);
        }
        UserPwdDO userPwdDO = userPwdDOMapper.selectByUserId(userDO.getId());
        //身份验证
        UserModel userModel = convertFromDataObject(userDO, userPwdDO);
        if(!StringUtils.equals(userModel.getEncrptPassword(),password)){
            throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL);
        }
        return userModel;
    }

    //实现model->do的转变
    private UserDO convertFromUserModel(UserModel userModel){
        if (userModel==null) return null;
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(userModel,userDO);
        return userDO;
    }

    private UserPwdDO convertUserPwdDOFromUserModel(UserModel userModel){
        if (userModel==null) return null;
        UserPwdDO userPwdDO = new UserPwdDO();
        userPwdDO.setEncrptPassword(userModel.getEncrptPassword());
        userPwdDO.setUserId(userModel.getId());
        return userPwdDO;
    }


    //完成do->model的转变
    private UserModel convertFromDataObject(UserDO userDO, UserPwdDO userPwdDO){
        if(userDO==null) return null;
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userDO,userModel);
        if(userPwdDO!=null)
            userModel.setEncrptPassword(userPwdDO.getEncrptPassword());
        return userModel;
    }
}
