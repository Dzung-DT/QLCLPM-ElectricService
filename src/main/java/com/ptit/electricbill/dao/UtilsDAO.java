package com.ptit.electricbill.dao;

import com.ptit.electricbill.model.DonGia;
import com.ptit.electricbill.model.KhachHang;

public interface UtilsDAO {
    // kiểm tra đơn giá
    boolean kiemTraDonGiaTonTai(DonGia donGia);

    boolean kiemTraGiaTrung(int gia);

    boolean kiemTraSoLuongGia(String ghiChu);

    //Kiểm tra khách hàng

    boolean kiemTraTonTai(String tableName, String columnOut, String columnIn, String value);

    boolean kiemTraTonTaiUpdateKH(String tableName, String columnOut, String columnIn, String value, String maKH);

}
