package com.ptit.electricbill.dao;

import com.ptit.electricbill.model.DienKe;

import java.util.List;

public interface DienKeDAO {
    List<Object> getAll();

    void add(DienKe dienKe);

    void delete(int IDDienKe);

    List<Object> searchByKHID(String KHID);

    boolean checkSoDien(String maKH, String maThang);

    void updateDienKeStatus(int IDDienKe);
}
