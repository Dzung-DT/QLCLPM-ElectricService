package com.ptit.electricbill.dao;

import com.ptit.electricbill.model.HoaDon;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;

import java.util.List;

public interface HoaDonDAO {
    List getAllDetail();

    void add(HoaDon hoaDon);

    Object getBill(String maKH, String maThang);

    List<Object> getBillByColumn(String maKH, String maThang);

    List<String> getValueColumn(String columnName);
}
