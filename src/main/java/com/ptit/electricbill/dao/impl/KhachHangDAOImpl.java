package com.ptit.electricbill.dao.impl;

import com.ptit.electricbill.dao.KhachHangDAO;
import com.ptit.electricbill.model.KhachHang;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
@Transactional
public class KhachHangDAOImpl implements KhachHangDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Object> getAll() {
        String sql = "SELECT * FROM khachhang";
        Query query = entityManager.createNativeQuery(sql);
        List<Object> userList = query.getResultList();
        return userList;
    }

    @Override
    public Object getByMaKH(String maKH) {
        String sql = "SELECT * FROM khachhang where MaKH = '" + maKH + "'";
        Query query = entityManager.createNativeQuery(sql);
        Object user = query.getResultList();
        return user;
    }

    @Override
    public void updateInformation(KhachHang KH) {
        String sql = "UPDATE khachhang SET TenKH = '" + KH.getTenKhachHang() + "',Ngaysinh = '" + KH.getNgaySinh() + "',CMND = '" + KH.getSoCMND() + "',Diachi = '" + KH.getDiaChi() + "',MailAddress = '" + KH.getMailAddress() + "',Gioitinh = '" + KH.getGioiTinh() + "',SoDienThoai = '" + KH.getSoDienThoai() + "',NgayBDSD = '" + KH.getNgayBDSD() + "',MucdichSD = '" + KH.getMucDichSD() + "' WHERE (MaKH = '" + KH.getMaKhachHang() + "')";
        Query query = entityManager.createNativeQuery(sql);
        query.executeUpdate();
    }

    @Override
    public void addKH(KhachHang KH) {
        String sql = "INSERT INTO khachhang (MaKH,TenKH,Ngaysinh,CMND,Diachi,MailAddress,Gioitinh,SoDienThoai,NgayBDSD,MucdichSD) VALUES ('" + KH.getMaKhachHang() + "', '" + KH.getTenKhachHang() + "', '" + KH.getNgaySinh() + "', '" + KH.getSoCMND() + "', '" + KH.getDiaChi() + "', '" + KH.getMailAddress() + "', '" + KH.getGioiTinh() + "', '" + KH.getSoDienThoai() + "', '" + KH.getNgayBDSD() + "', '" + KH.getMucDichSD() + "')";
        Query query = entityManager.createNativeQuery(sql);
        query.executeUpdate();
    }

    @Override
    public void deleteKH(String idKH) {
        String sql = "DELETE FROM khachhang where MaKH = '" + idKH + "'";
        Query query = entityManager.createNativeQuery(sql);
        query.executeUpdate();
    }
}
