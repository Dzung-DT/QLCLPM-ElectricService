package com.ptit.electricbill.dao;

import com.ptit.electricbill.model.HoaDon;

import java.util.List;

public interface HoaDonDAO {
    List<Object> getAll();
    List<Object> getAllDetail();
    void add(HoaDon hoaDon);
}
