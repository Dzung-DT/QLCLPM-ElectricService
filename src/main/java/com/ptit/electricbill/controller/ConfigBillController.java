package com.ptit.electricbill.controller;

import com.ptit.electricbill.dao.DonGiaDAO;
import com.ptit.electricbill.dao.HoaDonDAO;
import com.ptit.electricbill.model.HoaDon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ConfigBillController {

    @Autowired
    private DonGiaDAO donGiaDAO;

    @Autowired
    private HoaDonDAO hoaDonDAO;

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
        HoaDon hoaDon = new HoaDon();
        hoaDon.setMaHD(maHD);
        hoaDon.setMaKH(maKH);
        hoaDon.setMaThang(maThang);
        hoaDon.setLuongDienTT(Integer.parseInt(luongDienTT));
        hoaDon.setLoaiDien(loaiDienSD);
        hoaDon.setTien(Math.round((double) (giaTien * 1.1)));
        hoaDonDAO.add(hoaDon);
        return "OK";
    }


}
