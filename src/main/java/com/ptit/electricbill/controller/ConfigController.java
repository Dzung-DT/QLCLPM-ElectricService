package com.ptit.electricbill.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ptit.electricbill.dao.DonGiaDAO;
import com.ptit.electricbill.model.DonGia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ConfigController {
    // Don gia
    @Autowired
    private DonGiaDAO donGiaDAO;

    @GetMapping("/cau-hinh-don-gia")
    public String donGia(){
        return "donGiaDien";
    }

    @PostMapping("/danh-sach-don-gia")
    @ResponseBody
    public String getDonGia(){
        List<Object> donGiaList = donGiaDAO.getAll();
        String data;

        try {
            data =(new ObjectMapper()).writeValueAsString(donGiaList);
            return data;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("/cap-nhat-don-gia")
    @ResponseBody
    public String updateDonGia(@RequestParam("idDonGia") int idDonGia,
                               @RequestParam("giaMoi") int giaMoi,
                               @RequestParam("ghiChuMoi") String ghiChuMoi){
        DonGia dongia = new DonGia(idDonGia,giaMoi,ghiChuMoi);
        donGiaDAO.update(dongia);
        return "OK";
    }

    @PostMapping("/them-don-gia")
    @ResponseBody
    public String addDonGia(@RequestParam("gia") String gia,
                               @RequestParam("ghiChu") String ghiChu){
        DonGia donGia = new DonGia();
        donGia.setGia(Integer.parseInt(gia));
        donGia.setGhiChu(ghiChu);
        donGiaDAO.add(donGia);
        return "OK";
    }

    @PostMapping("/xoa-don-gia")
    @ResponseBody
    public String addDonGia(@RequestParam("idDonGia") String idDonGia){
        donGiaDAO.delete(Integer.parseInt(idDonGia));
        return "OK";
    }
}
