package com.ptit.electricbill.dao.impl;

import com.ptit.electricbill.dao.HoaDonDAO;
import com.ptit.electricbill.model.HoaDon;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
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
    public void update(HoaDon hoaDon) {

    }

    @Override
    public void add(HoaDon hoaDon) {
        String sql = "INSERT INTO hoadon (MaHD,MaKH,MaThang,LuongDienTT,LoaiDien,Tien) VALUES ('"+hoaDon.getMaHD()+"', '"+hoaDon.getMaKH()+"', '"+hoaDon.getMaThang()+"', '"+hoaDon.getLuongDienTT()+"', '"+hoaDon.getLoaiDien()+"', '"+hoaDon.getTien()+"')";
        Query query = entityManager.createNativeQuery(sql);
        query.executeUpdate();
    }

    @Override
    public void delete(int IDHoaDon) {

    }
}
