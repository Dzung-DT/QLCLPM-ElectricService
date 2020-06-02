package com.ptit.electricbill.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ptit.electricbill.dao.*;
import com.ptit.electricbill.model.KhachHang;
import com.ptit.electricbill.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CheckUserListController {

    private String KHTableName = "khachhang";

    // Khai báo cáo DAO được sử dụng
    @Autowired
    private KhachHangDAO khachHangDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private UtilsDAO utilsDAO;

    @Autowired
    private DienKeDAO dienKeDAO;

    @Autowired
    private HoaDonDAO hoaDonDAO;

    @GetMapping("/trang-chu")
    public String dashboard() {
        return "danhSachKH";
    }

    //Lấy toàn bộ danh sách khách hàng
    @PostMapping("/danh-sach-khach-hang")
    @ResponseBody
    public ResponseEntity<String> getUserList() {
        List<Object> userList = khachHangDAO.getAll();
        try {
            return new ResponseEntity<>((new ObjectMapper()).writeValueAsString(userList), HttpStatus.OK);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Lấy thông tin thành công", HttpStatus.OK);
    }

    // Tìm kiếm khách hàng với mã khách hàng
    @PostMapping("/tim-kiem-khach-hang")
    @ResponseBody
    public ResponseEntity<String> searchCustomerByID(@RequestParam("customerID") String customerID) {
        boolean checkExistCustotmer = utilsDAO.kiemTraTonTai("khachhang", "TenKH", "MaKH", customerID);
        if (checkExistCustotmer == false) {
            Object customer = khachHangDAO.getByMaKH(customerID.trim());
            try {
                return new ResponseEntity<>((new ObjectMapper()).writeValueAsString(customer), HttpStatus.OK);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return new ResponseEntity<>("Không tìm thấy khách hàng", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Cập nhật thông tin khách hàng
    @PostMapping("/cap-nhat-thong-tin-khach-hang")
    @ResponseBody
    public ResponseEntity<String> updateCustomerByID(@RequestParam("idKH_update") String idKH_update,
                                                     @RequestParam("tenKH_update") String tenKH_update,
                                                     @RequestParam("dob_update") String dob_update,
                                                     @RequestParam("soCmnd_update") String soCmnd_update,
                                                     @RequestParam("diaChi_update") String diaChi_update,
                                                     @RequestParam("mail_update") String mail_update,
                                                     @RequestParam("gioiTinh_update") String gioiTinh_update,
                                                     @RequestParam("soDT_update") String soDT_update,
                                                     @RequestParam("ngayBDSD_update") String ngayBDSD_update,
                                                     @RequestParam("MDSD_update") String MDSD_update) {
        String columnOut = "MaKH";
        if (utilsDAO.kiemTraTonTaiUpdateKH(KHTableName, columnOut, "CMND", soCmnd_update, idKH_update) == false) {
            return new ResponseEntity<>("CMND đã tồn tại", HttpStatus.INTERNAL_SERVER_ERROR);
        } else if (utilsDAO.kiemTraTonTaiUpdateKH(KHTableName, columnOut, "MailAddress", mail_update, idKH_update) == false) {
            return new ResponseEntity<>("Mail đã dược sử dụng", HttpStatus.INTERNAL_SERVER_ERROR);
        } else if (utilsDAO.kiemTraTonTaiUpdateKH(KHTableName, columnOut, "SoDienThoai", soDT_update, idKH_update) == false) {
            return new ResponseEntity<>("Số điện thoại đã dược sử dụng", HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            KhachHang KH = new KhachHang(idKH_update, tenKH_update, dob_update, soCmnd_update, diaChi_update, gioiTinh_update, soDT_update, ngayBDSD_update, mail_update, MDSD_update);
            khachHangDAO.updateInformation(KH);
            return new ResponseEntity<>("Cập nhật thành công", HttpStatus.OK);
        }
    }

    // Thêm mới khách hàng
    // Có check MaKH, CMND, MailAddress, SoDienThoai tồn tại
    @PostMapping("/them-khach-hang")
    @ResponseBody
    public ResponseEntity<String> addCustomer(@RequestParam("idKH_add") String idKH_add,
                                              @RequestParam("tenKH_add") String tenKH_add,
                                              @RequestParam("dob_add") String dob_add,
                                              @RequestParam("soCmnd_add") String soCmnd_add,
                                              @RequestParam("diaChi_add") String diaChi_add,
                                              @RequestParam("mail_add") String mail_add,
                                              @RequestParam("gioiTinh_add") String gioiTinh_add,
                                              @RequestParam("soDT_add") String soDT_add,
                                              @RequestParam("ngayBDSD_add") String ngayBDSD_add,
                                              @RequestParam("MDSD_add") String MDSD_add) {
        String columnOut = "MaKH";
        if (utilsDAO.kiemTraTonTai(KHTableName, columnOut, "MaKH", idKH_add) == false) {
            return new ResponseEntity<>("Mã khách hàng đã tồn tại", HttpStatus.INTERNAL_SERVER_ERROR);
        } else if (utilsDAO.kiemTraTonTai(KHTableName, columnOut, "CMND", soCmnd_add) == false) {
            return new ResponseEntity<>("CMND đã tồn tại", HttpStatus.INTERNAL_SERVER_ERROR);
        } else if (utilsDAO.kiemTraTonTai(KHTableName, columnOut, "MailAddress", mail_add) == false) {
            return new ResponseEntity<>("Mail đã dược sử dụng", HttpStatus.INTERNAL_SERVER_ERROR);
        } else if (utilsDAO.kiemTraTonTai(KHTableName, columnOut, "SoDienThoai", soDT_add) == false) {
            return new ResponseEntity<>("Số điện thoại đã dược sử dụng", HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            KhachHang KH = new KhachHang(idKH_add, tenKH_add, dob_add, soCmnd_add, diaChi_add, gioiTinh_add, soDT_add, ngayBDSD_add, mail_add, MDSD_add);
            khachHangDAO.addKH(KH);
            String password = soCmnd_add;
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String hashedPassword = passwordEncoder.encode(password);
            if (userDAO.checkExistUser(mail_add) == false) {
                User user = new User(mail_add, hashedPassword, "ROLE_USER");
                userDAO.addUser(user);
            }
            return new ResponseEntity<>("Thêm thành công", HttpStatus.OK);
        }
    }

    // Xóa khách hàng có ID = ?
    @PostMapping("/xoa-khach-hang")
    @ResponseBody
    public ResponseEntity<String> deleteCustomer(@RequestParam("idKHDelete") String idKHDelete) {
        List<Integer> maDKList = dienKeDAO.getIDListByMaDK(idKHDelete);
        if (maDKList.size() == 0) {
            khachHangDAO.deleteKH(idKHDelete);
        } else {
            List<String> maHDList = new ArrayList<>();
            for (Integer maDienKe : maDKList) {
                maHDList.add(hoaDonDAO.getMaHDByMaDK(String.valueOf(maDienKe)));
            }
            if (maHDList.size() == 0) {
                for (Integer maDienKe : maDKList) {
                    dienKeDAO.delete(maDienKe);
                }
                khachHangDAO.deleteKH(idKHDelete);
            } else {
                for (String maHD : maHDList) {
                    hoaDonDAO.deleteHoaDon(maHD);
                }
                for (Integer maDienKe : maDKList) {
                    dienKeDAO.delete(maDienKe);
                }
                khachHangDAO.deleteKH(idKHDelete);
            }
        }

        return new ResponseEntity<>("Xóa thành công", HttpStatus.OK);
    }

}
