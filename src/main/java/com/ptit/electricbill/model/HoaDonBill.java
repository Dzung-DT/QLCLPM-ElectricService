package com.ptit.electricbill.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HoaDonBill {
    private String stt;
    private String maHD;
    private String maKH;
    private String maThang;
    private String tenKH;
    private String diaChi;
    private String soDienHienTai;
    private String soThangTruoc;
    private String soKwh;
    private String loaiDien;
    private String thue;
    private String tien;
    private String ngayTao;
}
