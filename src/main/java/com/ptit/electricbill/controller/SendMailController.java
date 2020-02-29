package com.ptit.electricbill.controller;

import com.ptit.electricbill.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SendMailController {

    @Autowired
    MailService mailService;

    @PostMapping("/gui-mail-khach-hang")
    @ResponseBody
    public String sendMail(@RequestParam("mailContent") String mailContent,
                           @RequestParam("mailAddressReceive") String mailAddressReceive,
                           @RequestParam("mailSubject") String mailSubject) {
        mailService.sendMail(mailAddressReceive,mailSubject,mailContent);
        return "OK";
    }
}
