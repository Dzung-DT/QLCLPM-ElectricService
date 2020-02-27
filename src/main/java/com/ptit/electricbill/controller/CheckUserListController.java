package com.ptit.electricbill.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ptit.electricbill.dao.KhachHangDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class CheckUserListController {

    @Autowired
    private KhachHangDAO khachHangDAO;

    @GetMapping("/trang-chu")
    public String dashboard() {
        return "userList";
    }

    @PostMapping("/danh-sach-khach-hang")
    @ResponseBody
    public String getUserList() {
        List<Object> userList = khachHangDAO.getAll();
        String data = null;
        try {
            data = (new ObjectMapper()).writeValueAsString(userList);
            return data;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("/gui-mail-khach-hang")
    @ResponseBody
    public String sendMail(@RequestParam("mailContent") String mailContent) {
        System.out.println(mailContent);
        return mailContent;
    }

    @PostMapping("/tim-kiem-khach-hang")
    @ResponseBody
    public String searchCustomerByID(@RequestParam("customerID") String customerID) {
        Object customer = khachHangDAO.getByMaKH(customerID.trim());
        try {
            String data = (new ObjectMapper()).writeValueAsString(customer);
            return data;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
