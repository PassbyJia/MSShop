package com.rainfir.service;

import com.rainfir.controller.viewobject.UserVO;
import com.rainfir.model.UserModel;

public interface UserService {
    UserModel getUserById(Integer id);
}
