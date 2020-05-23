package com.ptit.electricbill.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ptit.electricbill.dao.*;
import com.ptit.electricbill.model.DienKe;
import com.ptit.electricbill.model.DonGia;
import com.ptit.electricbill.model.HoaDon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
public class ConfigController {
    // Don gia
    @Autowired
    private DonGiaDAO donGiaDAO;

    @Autowired
    private DienKeDAO dienKeDAO;

    @Autowired
    private UtilsDAO utilsDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private HoaDonDAO hoaDonDAO;
    @Autowired
    private ThueDAO thueDAO;

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
            if (utilsDAO.kiemTraSoLuongGia(ghiChu) == true) {
                donGiaDAO.add(donGia);
                return new ResponseEntity<>("Thêm thành công", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Số lượng giá loại < " + ghiChu + " > đã đủ", HttpStatus.INTERNAL_SERVER_ERROR);
            }
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
    public ResponseEntity<String> addSoDien(@RequestParam("maKH") String maKH,
                                            @RequestParam("maThang") String maThang,
                                            @RequestParam("chiSoCu") String chiSoCu,
                                            @RequestParam("chiSoMoi") String chiSoMoi) {
        StringBuilder maThangStr = new StringBuilder(maThang);
        boolean checkExist = dienKeDAO.checkSoDien(maKH, maThang);
        if (checkExist == true) {
            DienKe dienKe = new DienKe();
            dienKe.setMaKH(maKH);
            dienKe.setMaThang(maThang);
            dienKe.setSoDienMoi(Integer.parseInt(chiSoMoi));
            dienKe.setSoDienCu(Integer.parseInt(chiSoCu));
            dienKeDAO.add(dienKe);
            return new ResponseEntity<>("Thêm số điện thành công", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Số điện của khách hàng có mã " + maKH + " \nTháng " + maThangStr.insert(2, '-') + " đã tồn tại", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/xoa-so-dien")
    @ResponseBody
    public ResponseEntity<String> xoaSoDien(@RequestParam("idSoDien") String idSoDien) {
        dienKeDAO.delete(Integer.parseInt(idSoDien));
        return new ResponseEntity<>("Xóa thành công", HttpStatus.OK);
    }


    @PostMapping("/get-customer-id-list")
    @ResponseBody
    public ResponseEntity<String> getCustomerID() {
        List<String> customerIDList = userDAO.getCustomerID();
        try {
            return new ResponseEntity<>((new ObjectMapper()).writeValueAsString(customerIDList), HttpStatus.OK);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @PostMapping("/tim-kiem-dien-ke-KH")
    @ResponseBody
    public ResponseEntity<String> getDienKeByKH(@RequestParam("customerID") String customerID) {
        boolean checkExist = utilsDAO.kiemTraTonTai("dienke", "MaKH", "MaKH", customerID);
        if (checkExist == false) {
            List<Object> result = dienKeDAO.searchByKHID(customerID);
            try {
                return new ResponseEntity<>((new ObjectMapper()).writeValueAsString(result), HttpStatus.OK);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

        } else {
            return new ResponseEntity<>("Điện kế với mà KH " + customerID + " không tồn tại", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    //Hóa đơn

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
        } else if (loaiDienSD.equals("Sinh hoạt trả trước")) {
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
        hoaDon.setTien(Math.round(giaTien + giaTien * giaThue));
        hoaDon.setThoiGian(dateFormatGmt.format(new Date()) + "");
        hoaDonDAO.add(hoaDon);

        return "OK";
    }

    @GetMapping("/danh-sach-hoa-don")
    public String xemHoadon() {
        return "hoaDonChiTiet";
    }

    @PostMapping("/danh-sach-hoa-don")
    @ResponseBody
    public String xemHoaDon() {
        List<Object> hoaDonCTList = hoaDonDAO.getAllDetail();
        String data;
        try {
            data = (new ObjectMapper()).writeValueAsString(hoaDonCTList);
            return data;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("/tim-kiem-hoa-don")
    @ResponseBody
    public String timHoaDon(@RequestParam("maKH") String maKH,
                            @RequestParam("maThang") String maThang) {
        Object hoaDon = hoaDonDAO.getBill(maKH, maThang);
        String data;
        try {
            data = (new ObjectMapper()).writeValueAsString(hoaDon);
            return data;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
