package com.ptit.electricbill.model;

public class KhachHang {
    private String maKhachHang;
    private String tenKhachHang;
    private String ngaySinh;
    private String soCMND;
    private String diaChi;
    private String gioiTinh;
    private String ngayBDSD;
    private String mucdichSD;
    private String mailAddress;

    public KhachHang() {
    }

    public KhachHang(String maKhachHang, String tenKhachHang, String ngaySinh, String soCMND, String diaChi, String gioiTinh, String ngayBDSD, String mucdichSD, String mailAddress) {
        this.maKhachHang = maKhachHang;
        this.tenKhachHang = tenKhachHang;
        this.ngaySinh = ngaySinh;
        this.soCMND = soCMND;
        this.diaChi = diaChi;
        this.gioiTinh = gioiTinh;
        this.ngayBDSD = ngayBDSD;
        this.mucdichSD = mucdichSD;
        this.mailAddress = mailAddress;
    }

    public String getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(String maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getSoCMND() {
        return soCMND;
    }

    public void setSoCMND(String soCMND) {
        this.soCMND = soCMND;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getNgayBDSD() {
        return ngayBDSD;
    }

    public void setNgayBDSD(String ngayBDSD) {
        this.ngayBDSD = ngayBDSD;
    }

    public String getMucdichSD() {
        return mucdichSD;
    }

    public void setMucdichSD(String mucdichSD) {
        this.mucdichSD = mucdichSD;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }
}
