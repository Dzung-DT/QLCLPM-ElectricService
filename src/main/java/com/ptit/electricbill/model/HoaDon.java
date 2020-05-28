package com.ptit.electricbill.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HoaDon {
    private String maHD;
    private String maKH;
    private String maThang;
    private int maThue;
    private int maDK;
    private String thoiGian;
}
