package com.ptit.electricbill.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HoaDon {
    private String maHD;
    private String maKH;
    private String maThang;
    private int maThue;
    private double tien;
    private String thoiGian;
}
