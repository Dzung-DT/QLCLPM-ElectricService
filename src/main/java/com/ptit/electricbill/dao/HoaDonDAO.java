package com.ptit.electricbill.dao;

import com.ptit.electricbill.model.HoaDon;

import java.util.List;

public interface HoaDonDAO {
    List<Object> getAll();
    void update(HoaDon hoaDon);
    void add(HoaDon hoaDon);
    void delete(int IDHoaDon);
}
