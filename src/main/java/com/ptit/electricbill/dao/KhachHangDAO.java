package com.ptit.electricbill.dao;

import java.util.List;

public interface KhachHangDAO {
    List<Object> getAll();
    Object getByMaKH(String maKH);
}
