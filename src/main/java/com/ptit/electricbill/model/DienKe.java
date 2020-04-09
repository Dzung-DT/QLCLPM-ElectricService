package com.ptit.electricbill.model;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DienKe {
    private int id;
    private String maKH;
    private String maThang;
    private int soDienMoi;
    private int soDienCu;
}
