package com.ptit.electricbill.dao.impl;

import com.ptit.electricbill.dao.UtilsDAO;
import com.ptit.electricbill.model.DonGia;
import com.ptit.electricbill.model.KhachHang;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;


@Repository
@Transactional
public class UtilsDAOImpl implements UtilsDAO {

    @Autowired
    private EntityManager entityManager;

    //Kiểm tra đơn giá
    @Override
    public boolean kiemTraDonGiaTonTai(DonGia donGia) {
        String sql = "select MaDG FROM dongia where Gia = "+donGia.getGia()+" and GhiChu ='"+donGia.getGhiChu()+"'";
        Query query = entityManager.createNativeQuery(sql);
        List<Integer> IDList = query.getResultList();
        if (IDList.size() == 1) {
            return false;
        }
        return true;
    }

    @Override
    public boolean kiemTraGiaTrung(int gia) {
        String sql = "select MaDG FROM dongia where Gia = "+gia+"";
        Query query = entityManager.createNativeQuery(sql);
        List<Integer> IDList = query.getResultList();
        if (IDList.size() == 1) {
            return false;
        }
        return true;
    }

    //Kiểm tra khách hàng


    @Override
    public boolean kiemTraKhachHangTonTai(String column, String value) {
        String sql = "select MaKH FROM khachhang where "+column+" = '"+value+"'";
        Query query = entityManager.createNativeQuery(sql);
        List<String> maKHList = query.getResultList();
        if (maKHList.size() == 1) {
            return false;
        }
        return true;
    }
}

