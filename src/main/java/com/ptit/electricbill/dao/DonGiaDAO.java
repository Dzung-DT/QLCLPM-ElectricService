package com.ptit.electricbill.dao;

import com.ptit.electricbill.model.DonGia;

import java.util.List;

public interface DonGiaDAO {
    List<Object> getAll();
    void update(DonGia donGia);
    void add(DonGia donGia);
    void delete(int IDDonGia);
}
