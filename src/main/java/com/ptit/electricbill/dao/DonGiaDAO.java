package com.ptit.electricbill.dao;

import com.ptit.electricbill.model.DonGia;

import java.util.List;

public interface DonGiaDAO {
    List<Object> getAll();
    void update(int MaDG, int gia);
    void add(DonGia donGia);
    void delete(int IDDonGia);
    List<Integer> getGia(String loaiDonGia);
}
