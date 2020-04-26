package com.ptit.electricbill.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ptit.electricbill.dao.DienKeDAO;
import com.ptit.electricbill.dao.DonGiaDAO;
import com.ptit.electricbill.dao.UtilsDAO;
import com.ptit.electricbill.model.DienKe;
import com.ptit.electricbill.model.DonGia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    private DienKeDAO dienKeDAO;

    @Autowired
    private UtilsDAO utilsDAO;

    @GetMapping("/cau-hinh-don-gia")
    public String donGia() {
        return "donGiaDien";
    }

    @PostMapping("/danh-sach-don-gia")
    @ResponseBody
    public ResponseEntity<String> getDonGia() {
        List<Object> donGiaList = donGiaDAO.getAll();
        String data;
        try {
            data = (new ObjectMapper()).writeValueAsString(donGiaList);
            return new ResponseEntity<>(data, HttpStatus.OK);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new ResponseEntity<>("ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/cap-nhat-don-gia")
    @ResponseBody
    public ResponseEntity<String> updateDonGia(@RequestParam("idDonGia") int idDonGia,
                                               @RequestParam("giaMoi") int giaMoi) {

        if (utilsDAO.kiemTraGiaTrung(giaMoi) == true) {
            donGiaDAO.update(idDonGia, giaMoi);
            return new ResponseEntity<>("Cập nhật thành công", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Giá bị trùng", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/them-don-gia")
    @ResponseBody
    public ResponseEntity<String> addDonGia(@RequestParam("gia") String gia,
                                            @RequestParam("ghiChu") String ghiChu) {
        DonGia donGia = new DonGia();
        donGia.setGia(Integer.parseInt(gia));
        donGia.setGhiChu(ghiChu);
        if (utilsDAO.kiemTraDonGiaTonTai(donGia) == true) {
            donGiaDAO.add(donGia);
            return new ResponseEntity<>("Thêm thành công", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Đơn giá đã tồn tại", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/xoa-don-gia")
    @ResponseBody
    public ResponseEntity<String> addDonGia(@RequestParam("idDonGia") String idDonGia) {
        try {
            donGiaDAO.delete(Integer.parseInt(idDonGia));
            return new ResponseEntity<>("Xóa thành công", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //cấu hình điện kế
    @GetMapping("/cap-nhat-so-dien")
    public String capNhatSoDien() {
        return "capNhatSoDien";
    }

    @PostMapping("/danh-sach-so-dien")
    @ResponseBody
    public String getSoDien() {
        List<Object> soDienList = dienKeDAO.getAll();
        String data;
        try {
            data = (new ObjectMapper()).writeValueAsString(soDienList);
            return data;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("/them-so-dien")
    @ResponseBody
    public String addSoDien(@RequestParam("maKH") String maKH,
                            @RequestParam("maThang") String maThang,
                            @RequestParam("chiSoCu") String chiSoCu,
                            @RequestParam("chiSoMoi") String chiSoMoi) {
        DienKe dienKe = new DienKe();
        dienKe.setMaKH(maKH);
        dienKe.setMaThang(maThang);
        dienKe.setSoDienMoi(Integer.parseInt(chiSoMoi));
        dienKe.setSoDienCu(Integer.parseInt(chiSoCu));
        dienKeDAO.add(dienKe);
        return "OK";
    }

    @PostMapping("/xoa-so-dien")
    @ResponseBody
    public String xoaSoDien(@RequestParam("idSoDien") String idSoDien) {
        dienKeDAO.delete(Integer.parseInt(idSoDien));
        return "OK";
    }
}
