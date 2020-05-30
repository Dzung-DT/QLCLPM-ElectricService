package com.ptit.electricbill.dao;

import com.ptit.electricbill.model.HoaDon;

import java.util.List;

public interface HoaDonDAO {
    List getAllDetail();

    void add(HoaDon hoaDon);

    Object getBill(String maKH, String maThang);

    List<Object> getBillByColumn(String maKH, String maThang);

    List<String> getMaKH();

    List<String> getMaThang();

    String getMaHDByMaDK(String maDK);

    void deleteHoaDon(String maHD);
}
