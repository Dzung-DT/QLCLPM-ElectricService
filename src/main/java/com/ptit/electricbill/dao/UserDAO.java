package com.ptit.electricbill.dao;


import com.ptit.electricbill.entity.UserEntity;
import com.ptit.electricbill.model.User;

public interface UserDAO {
    UserEntity getByUsername(String username);

    boolean checkExistUser(String username);

    void addUser(User user);
}
