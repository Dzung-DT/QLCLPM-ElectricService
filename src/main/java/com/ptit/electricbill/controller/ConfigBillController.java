package com.ptit.electricbill.controller;

import com.ptit.electricbill.dao.DonGiaDAO;
import com.ptit.electricbill.model.DienKe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ConfigBillController {

    @Autowired
    private DonGiaDAO donGiaDAO;

    @GetMapping("/lap-hoa-don")
    public String lapHoadon(){
        return "lapHoaDon";
    }

    @PostMapping("/them-hoa-don")
    @ResponseBody
    public String themHoaDon(@RequestParam("maHD") String maHD,
                            @RequestParam("maKH") String maKH,
                            @RequestParam("maThang") String maThang,
                            @RequestParam("luongDienTT") String luongDienTT,
                             @RequestParam("loaiDienSD") String loaiDienSD){
        System.out.println(maHD+" "+maKH+" "+maThang+" "+luongDienTT+" "+loaiDienSD);

        List<Integer> giaList = donGiaDAO.getGia(loaiDienSD);
        for(Integer gia : giaList){
            System.out.println(gia);
        }
        System.out.println(giaList.size());
        return "OK";
    }
}
