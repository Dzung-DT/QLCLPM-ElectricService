package com.ptit.electricbill.dao;

import com.ptit.electricbill.model.KhachHang;

import java.util.List;

public interface KhachHangDAO {
    List<Object> getAll();

    Object getByMaKH(String maKH);

    void updateInformation(KhachHang KH);

    void addKH(KhachHang KH);

    void deleteKH(String idKH);

    String getMDSD(String mailAdd);
}

