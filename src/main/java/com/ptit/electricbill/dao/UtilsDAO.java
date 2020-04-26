package com.ptit.electricbill.dao;

import com.ptit.electricbill.model.DonGia;
import com.ptit.electricbill.model.KhachHang;

public interface UtilsDAO {
    // kiểm tra đơn giá
    boolean kiemTraDonGiaTonTai(DonGia donGia);

    boolean kiemTraGiaTrung(int gia);

    //Kiểm tra khách hàng

    boolean kiemTraKhachHangTonTai(String column, String value);

}
