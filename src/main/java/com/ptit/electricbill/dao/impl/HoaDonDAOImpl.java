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
    public void add(HoaDon hoaDon) {
        String sql = "INSERT INTO hoadon (MaHD,MaKH,MaThang,MaThue,Tien,ThoiGian) VALUES ('" + hoaDon.getMaHD() + "', '" + hoaDon.getMaKH() + "', '" + hoaDon.getMaThang() + "', '" + hoaDon.getMaThue() + "', '" + hoaDon.getTien() + "', '" + hoaDon.getThoiGian() + "')";
        Query query = entityManager.createNativeQuery(sql);
        query.executeUpdate();
    }

    @Override
    public List<Object> getAllDetail() {
        String sql = "SELECT hoadon.MaHD, hoadon.MaThang, hoadon.MaKH, khachhang.TenKH, khachhang.Diachi, dienke.SoDienMoi, dienke.SoDienCu, khachhang.MucDichSD , thue.giaThue, hoadon.Tien, hoadon.ThoiGian from hoadon, khachhang, dienke, thue where hoadon.MaKH = khachhang.MaKH and hoadon.MaThang = dienke.MaThang";
        Query query = entityManager.createNativeQuery(sql);
        return query.getResultList();
    }

    @Override
    public Object getBill(String maKH, String maThang) {
        String sql = "SELECT hoadon.MaHD, hoadon.MaThang, hoadon.MaKH, khachhang.TenKH, khachhang.Diachi, dienke.SoDienMoi, dienke.SoDienCu, khachhang.MucDichSD , thue.giaThue, hoadon.Tien, hoadon.ThoiGian from hoadon, khachhang, dienke, thue where hoadon.MaKH = khachhang.MaKH and hoadon.MaThang = dienke.MaThang and hoadon.MaKH ='" + maKH + "' and hoadon.MaThang='" + maThang + "'";
        Query query = entityManager.createNativeQuery(sql);
        return query.getSingleResult();
    }

    @Override
    public List<Object> getBillByColumn(String maKH, String maThang) {
        if (maKH.equals("") && maThang.equals("")) {
            String sql = "SELECT hoadon.MaHD, hoadon.MaThang, hoadon.MaKH, khachhang.TenKH, khachhang.Diachi, dienke.SoDienMoi, dienke.SoDienCu, khachhang.MucDichSD , thue.giaThue, hoadon.Tien, hoadon.ThoiGian from hoadon, khachhang, dienke, thue where hoadon.MaKH = khachhang.MaKH and hoadon.MaThang = dienke.MaThang";
            Query query = entityManager.createNativeQuery(sql);
            return query.getResultList();
        } else if (maKH.equals("")) {
            String sql = "SELECT hoadon.MaHD, hoadon.MaThang, hoadon.MaKH, khachhang.TenKH, khachhang.Diachi, dienke.SoDienMoi, dienke.SoDienCu, khachhang.MucDichSD , thue.giaThue, hoadon.Tien, hoadon.ThoiGian from hoadon, khachhang, dienke, thue where hoadon.MaKH = khachhang.MaKH and hoadon.MaThang = dienke.MaThang and hoadon.MaThang = " + maThang + "";
            Query query = entityManager.createNativeQuery(sql);
            return query.getResultList();
        } else if (maThang.equals("")) {
            String sql = "SELECT hoadon.MaHD, hoadon.MaThang, hoadon.MaKH, khachhang.TenKH, khachhang.Diachi, dienke.SoDienMoi, dienke.SoDienCu, khachhang.MucDichSD , thue.giaThue, hoadon.Tien, hoadon.ThoiGian from hoadon, khachhang, dienke, thue where hoadon.MaKH = khachhang.MaKH and hoadon.MaThang = dienke.MaThang and hoadon.MaKH = " + maKH + "";
            Query query = entityManager.createNativeQuery(sql);
            return query.getResultList();
        } else {
            String sql = "SELECT hoadon.MaHD, hoadon.MaThang, hoadon.MaKH, khachhang.TenKH, khachhang.Diachi, dienke.SoDienMoi, dienke.SoDienCu, khachhang.MucDichSD , thue.giaThue, hoadon.Tien, hoadon.ThoiGian from hoadon, khachhang, dienke, thue where hoadon.MaKH = khachhang.MaKH and hoadon.MaThang = dienke.MaThang and hoadon.MaKH = " + maKH + " and hoadon.MaThang = " + maThang + "";
            Query query = entityManager.createNativeQuery(sql);
            return query.getResultList();
        }
    }

    @Override
    public List<String> getValueColumn(String columnName) {
        String sql = "SELECT distinct ("+columnName+") from hoadon";
        Query query = entityManager.createNativeQuery(sql);
        return query.getResultList();
    }
}
