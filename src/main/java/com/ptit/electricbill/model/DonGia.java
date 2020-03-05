package com.ptit.electricbill.model;

public class DonGia {
    private int maDonGia;
    private int gia;
    private String ghiChu;

    public DonGia() {
    }

    public DonGia(int maDonGia, int gia, String ghiChu) {
        this.maDonGia = maDonGia;
        this.gia = gia;
        this.ghiChu = ghiChu;
    }

    public int getMaDonGia() {
        return maDonGia;
    }

    public void setMaDonGia(int maDonGia) {
        this.maDonGia = maDonGia;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
}
