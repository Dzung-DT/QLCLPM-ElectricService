package com.ptit.electricbill.dao.impl;

import com.ptit.electricbill.dao.HoaDonDAO;
import com.ptit.electricbill.model.HoaDon;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
@Transactional
public class HoaDonDAOImpl implements HoaDonDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Object> getAll() {
        String sql = "SELECT  * from hoadon";
        Query query = entityManager.createNativeQuery(sql);
        List<Object> hoaDonList = query.getResultList();
        return hoaDonList;
    }

    @Override
    public void add(HoaDon hoaDon) {
        String sql = "INSERT INTO hoadon (MaHD,MaKH,MaThang,LuongDienTT,LoaiDien,Tien,ThoiGian) VALUES ('"+hoaDon.getMaHD()+"', '"+hoaDon.getMaKH()+"', '"+hoaDon.getMaThang()+"', '"+hoaDon.getLuongDienTT()+"', '"+hoaDon.getLoaiDien()+"', '"+hoaDon.getTien()+"', '"+hoaDon.getThoiGian()+"')";
        Query query = entityManager.createNativeQuery(sql);
        query.executeUpdate();
    }

    @Override
    public List<Object> getAllDetail() {
        String sql = "SELECT hoadon.MaHD, hoadon.MaThang, hoadon.MaKH, khachhang.TenKH, khachhang.Diachi, dienke.SoDienMoi, dienke.SoDienCu, hoadon.LuongDienTT, hoadon.LoaiDien, thue.giaThue, hoadon.Tien, hoadon.ThoiGian from hoadon, khachhang, dienke, thue where hoadon.MaKH = khachhang.MaKH and hoadon.MaThang = dienke.MaThang";
        Query query = entityManager.createNativeQuery(sql);
        List<Object> hoaDonChiTietList = query.getResultList();
        return hoaDonChiTietList;
    }

    @Override
    public Object getBill(String maKH, String maThang) {
        String sql = "SELECT hoadon.MaHD, hoadon.MaThang, hoadon.MaKH, khachhang.TenKH, khachhang.Diachi, dienke.SoDienMoi, dienke.SoDienCu, hoadon.LuongDienTT, hoadon.LoaiDien, thue.giaThue, hoadon.Tien, hoadon.ThoiGian from hoadon, khachhang, dienke, thue where hoadon.MaKH = khachhang.MaKH and hoadon.MaThang = dienke.MaThang and hoadon.MaKH ='"+maKH+"' and hoadon.MaThang='"+maThang+"'";
        Query query = entityManager.createNativeQuery(sql);
        Object hoaDon = query.getSingleResult();
        return hoaDon;
    }
}
