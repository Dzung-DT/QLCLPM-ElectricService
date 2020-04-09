package com.ptit.electricbill.dao;


import com.ptit.electricbill.entity.UserEntity;

public interface UserDAO {
    UserEntity getByUsername(String username);
}
