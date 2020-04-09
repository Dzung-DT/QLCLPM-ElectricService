package com.ptit.electricbill.service;


import com.ptit.electricbill.model.User;

public interface UserService {
    User getByUsername(String username);
}
