package com.ptit.electricbill.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CheckUserListController {
    @GetMapping("/danh-sach-khach-hang")
    public String dashboard(){
        return "userList";
    }
}
