package com.ptit.electricbill.dao;

import com.ptit.electricbill.model.DienKe;

import java.util.List;

public interface DienKeDAO {
    List<Object> getAll();
    void add(DienKe dienKe);
    void delete(int IDDienKe);
}
