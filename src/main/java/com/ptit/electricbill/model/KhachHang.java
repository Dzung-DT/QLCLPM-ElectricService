package com.ptit.electricbill.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KhachHang {
    private String maKhachHang;
    private String tenKhachHang;
    private String ngaySinh;
    private String soCMND;
    private String diaChi;
    private String gioiTinh;
    private String soDienThoai;
    private String ngayBDSD;
    private String mailAddress;
    private String mucDichSD;
}
