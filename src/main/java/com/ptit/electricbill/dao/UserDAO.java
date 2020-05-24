package com.ptit.electricbill.dao;


import com.ptit.electricbill.entity.UserEntity;
import com.ptit.electricbill.model.User;

import java.util.List;

public interface UserDAO {
    UserEntity getByUsername(String username);

    boolean checkExistUser(String username);

    void addUser(User user);

    List<String> getCustomerID();

    String getMDSD(String maKH);
}
