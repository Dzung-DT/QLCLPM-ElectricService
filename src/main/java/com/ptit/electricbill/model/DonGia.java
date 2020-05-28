package com.ptit.electricbill.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DonGia {
    private int maDonGia;
    private int gia;
    private String ghiChu;
}
