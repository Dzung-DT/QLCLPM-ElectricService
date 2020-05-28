package com.ptit.electricbill.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    //Gọi trang login
    @GetMapping({"/login", "/"})
    public String login() {
        return "login";
    }

    //Chuyển hướng nếu login lỗi
    @GetMapping("/404-page")
    public String error() {
        return "404error";
    }
}
