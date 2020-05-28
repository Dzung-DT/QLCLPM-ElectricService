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
import java.util.*;

@Controller
public class ConfigController {

    //Khai báo DAO được sử dựng
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

    //Trang cấu hình đơn giá
    @GetMapping("/cau-hinh-don-gia")
    public String donGia() {
        return "donGiaDien";
    }

    //Lấy toàn bộ đơn giá
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

    // Cập nhật đơn giá
    // Có check đơn giá tồn tại
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

    //Thêm đơn giá
    //Check số lượng giá của từng loại
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

    //Xóa đơn giá có id = ?
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


    //Cấu hình điện kế
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

    //Thêm số điện mới
    //Check số điện tồn tại
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

    // Xóa số điện có id = ?
    @PostMapping("/xoa-so-dien")
    @ResponseBody
    public ResponseEntity<String> xoaSoDien(@RequestParam("idSoDien") String idSoDien) {
        dienKeDAO.delete(Integer.parseInt(idSoDien));
        return new ResponseEntity<>("Xóa thành công", HttpStatus.OK);
    }

    //Lấy mục đích sử dụng của Khách hàng có Mã Khách hàng = ?
    @PostMapping("/lay-MDSD-by-maKH")
    @ResponseBody
    public ResponseEntity<String> getMDSDByMaKH(@RequestParam("maKH") String maKH) {
        String MDSD = userDAO.getMDSD(maKH);
        try {
            return new ResponseEntity<>((new ObjectMapper()).writeValueAsString(MDSD), HttpStatus.OK);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    //Lấy mã khách hàng
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

    //Tìm điện kế bằng mã Khách hàng
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

    //Trang hóa đơn
    @GetMapping("/danh-sach-hoa-don")
    public String xemHoadon() {
        return "hoaDonChiTiet";
    }

    //Tạo mới hóa đơn
    //Có kiểm tra hóa đơn tồn tại
    @PostMapping("/them-hoa-don")
    @ResponseBody
    public ResponseEntity<String> themHoaDon(@RequestParam("maDK") String maDK,
                                             @RequestParam("maHD") String maHD,
                                             @RequestParam("maKH") String maKH,
                                             @RequestParam("maThang") String maThang) {
        boolean checkExist = utilsDAO.kiemTraTonTai("hoadon", "MaHD", "MaHD", maHD);
        if (checkExist == false) {
            return new ResponseEntity<>("Hoá đơn có mã " + maHD + " đã tồn tại", HttpStatus.INTERNAL_SERVER_ERROR);
        } else {

            HoaDon hoaDon = new HoaDon();
            hoaDon.setMaHD(maHD);
            hoaDon.setMaKH(maKH);
            hoaDon.setMaThang(maThang);
            hoaDon.setMaDK(Integer.parseInt(maDK));
            int thueID = thueDAO.getMaThue();
            hoaDon.setMaThue(thueID);
            SimpleDateFormat dateFormatGmt = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT+7"));
            hoaDon.setThoiGian(dateFormatGmt.format(new Date()) + "");
            hoaDonDAO.add(hoaDon);
            return new ResponseEntity<>("Tạo hóa đơn thành công", HttpStatus.OK);
        }
    }

    //Lấy toàn bộ đơn giá có mục đích sử dụng = ?
    @PostMapping("/lay-don-gia-by-MDSD")
    @ResponseBody
    public String getDonGiaByMDSD() {
        String MDSD1 = "Sinh hoạt";
        String MDSD2 = "Sinh hoạt trả trước";
        List<Integer> giaList1 = donGiaDAO.getGia(MDSD1);
        List<Integer> giaList2 = donGiaDAO.getGia(MDSD2);
        Map<String, List> donGiaInfor = new LinkedHashMap<>();
        donGiaInfor.put(MDSD1, giaList1);
        donGiaInfor.put(MDSD2, giaList2);
        try {
            return (new ObjectMapper()).writeValueAsString(donGiaInfor);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    //Lấy danh sách hóa đơn
    @PostMapping("/danh-sach-hoa-don")
    @ResponseBody
    public String xemHoaDon() {
        List<Object> hoaDonCTList = hoaDonDAO.getAllDetail();
        try {
            return (new ObjectMapper()).writeValueAsString(hoaDonCTList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    //Tìm kiếm hóa đơn có maKH = ? && maThang = ?
    @PostMapping("/tim-kiem-hoa-don")
    @ResponseBody
    public ResponseEntity<String> timHoaDon(@RequestParam("maKH") String maKH,
                                            @RequestParam("maThang") String maThang) {
        List<Object> hoaDonCTList = hoaDonDAO.getBillByColumn(maKH, maThang);
        if (hoaDonCTList.size() > 0) {
            try {
                return new ResponseEntity<>((new ObjectMapper()).writeValueAsString(hoaDonCTList), HttpStatus.OK);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        } else {
            return new ResponseEntity<>("Hóa đơn không tồn tại", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    //Lấy mã Khách hàng cho autocomplete mã KH
    @PostMapping("/lay-maKH")
    @ResponseBody
    public ResponseEntity<String> layMaKH() {
        List<String> maKHList = hoaDonDAO.getMaKH();
        if (maKHList.size() > 0) {
            try {
                return new ResponseEntity<>((new ObjectMapper()).writeValueAsString(maKHList), HttpStatus.OK);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        } else {
            return new ResponseEntity<>("Không tìm thấy kết quả", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    //Lấy mã Khách hàng cho autocomplete mã Tháng
    @PostMapping("/lay-maThang")
    @ResponseBody
    public ResponseEntity<String> layMaThang() {
        List<String> maThangList = hoaDonDAO.getMaThang();
        if (maThangList.size() > 0) {
            try {
                return new ResponseEntity<>((new ObjectMapper()).writeValueAsString(maThangList), HttpStatus.OK);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        } else {
            return new ResponseEntity<>("Không tìm thấy kết quả", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
}
