package com.ptit.electricbill.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ptit.electricbill.dao.DonGiaDAO;
import com.ptit.electricbill.dao.HoaDonDAO;
import com.ptit.electricbill.dao.ThueDAO;
import com.ptit.electricbill.model.HoaDon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Controller
public class ConfigBillController {

    @Autowired
    private DonGiaDAO donGiaDAO;

    @Autowired
    private HoaDonDAO hoaDonDAO;
    @Autowired
    private ThueDAO thueDAO;

    @GetMapping("/lap-hoa-don")
    public String lapHoadon() {
        return "lapHoaDon";
    }

    @PostMapping("/them-hoa-don")
    @ResponseBody
    public String themHoaDon(@RequestParam("maHD") String maHD,
                             @RequestParam("maKH") String maKH,
                             @RequestParam("maThang") String maThang,
                             @RequestParam("luongDienTT") String luongDienTT,
                             @RequestParam("loaiDienSD") String loaiDienSD) {

        List<Integer> giaList = donGiaDAO.getGia(loaiDienSD);
        int soDien = Integer.parseInt(luongDienTT);
        long giaTien = 0;
        if (loaiDienSD.equals("Sinh hoạt")) {
            int[] a = {giaList.get(0), giaList.get(1), giaList.get(2), giaList.get(3), giaList.get(4), giaList.get(5)};
            int[] b = {50, 50, 100, 100, 100, 100};
            List<Integer> c = new ArrayList<>();
            if (soDien > 500) {
                for (int x = 0; x < 5; x++) {
                    giaTien += a[x] * b[x];
                }
                giaTien += a[5] * (soDien - 400);
            } else if (soDien <= 500 && soDien >= 100) {
                int i = 0;
                while (true) {
                    soDien = soDien - b[i];
                    if (soDien > 0) {
                        c.add(b[i]);
                        i++;
                    } else if (soDien < 0) {
                        c.add(100 - soDien * (-1));
                        break;
                    } else if (soDien == 0) {
                        c.add(soDien + b[i]);
                        break;
                    }
                }
                for (int j = 0; j < c.size(); j++) {
                    giaTien += a[j] * c.get(j);
                }
            } else if (soDien < 100 && soDien >= 0) {
                int i = 0;
                while (true) {
                    soDien = soDien - b[i];
                    if (soDien > 0) {
                        c.add(b[i]);
                        i++;
                    } else if (soDien < 0) {
                        c.add(50 - soDien * (-1));
                        break;
                    } else if (soDien == 0) {
                        c.add(soDien + b[i]);
                        break;
                    }
                }
                for (int j = 0; j < c.size(); j++) {
                    giaTien += a[j] * c.get(j);
                }
            }
        }else if(loaiDienSD.equals("Sinh hoạt trả trước")){
            giaTien = giaList.get(0) * soDien;
        }

        Double giaThue = thueDAO.getGiaThue();

        SimpleDateFormat dateFormatGmt = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT+7"));

        HoaDon hoaDon = new HoaDon();
        hoaDon.setMaHD(maHD);
        hoaDon.setMaKH(maKH);
        hoaDon.setMaThang(maThang);
        hoaDon.setLuongDienTT(Integer.parseInt(luongDienTT));
        hoaDon.setLoaiDien(loaiDienSD);
        hoaDon.setTien(Math.round(giaTien + giaTien*giaThue));
        hoaDon.setThoiGian(dateFormatGmt.format(new Date())+"");
        hoaDonDAO.add(hoaDon);

        return "OK";
    }

    @PostMapping("/danh-sach-hoa-don-tom-tat")
    @ResponseBody
    public String getDSHoaDonTomTat(){
        List<Object> hoaDonTTList = hoaDonDAO.getAll();
        String data;
        try {
            data =(new ObjectMapper()).writeValueAsString(hoaDonTTList);
            return data;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }


    @GetMapping("/danh-sach-hoa-don")
    public String xemHoadon() {
        return "hoaDonChiTiet";
    }

    @PostMapping("/danh-sach-hoa-don")
    @ResponseBody
    public String xemHoaDon(){
        List<Object> hoaDonCTList = hoaDonDAO.getAllDetail();
        String data;
        try {
            data =(new ObjectMapper()).writeValueAsString(hoaDonCTList);
            System.out.println(data);
            return data;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("/tim-kiem-hoa-don")
    @ResponseBody
    public String timHoaDon( @RequestParam("maKH") String maKH,
                             @RequestParam("maThang") String maThang){
        Object hoaDon = hoaDonDAO.getBill(maKH,maThang);
        String data;
        try {
            data =(new ObjectMapper()).writeValueAsString(hoaDon);
            return data;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
