package com.chord.lsr.service;

import com.chord.lsr.pojo.dto.UserLoginDTO;
import com.chord.lsr.pojo.dto.UserRegisterDTO;
import com.chord.lsr.pojo.entity.User;

public interface UserService {
    User login(UserLoginDTO userLoginDTO);

    void register(UserRegisterDTO userAddDTO);

    void upgrade();

    void downgrade();

    User getById(Integer id);
}
