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

    @Override
    public boolean kiemTraSoLuongGia(String ghiChu) {
        String sql = "SELECT * FROM dongia where dongia.GhiChu = '"+ghiChu+"'";
        Query query = entityManager.createNativeQuery(sql);
        List<Object> records = query.getResultList();
        if(ghiChu.equals("Sinh hoạt") && records.size() < 6){
            return true;
        }
        if(ghiChu.equals("Sinh hoạt trả trước") && records.size() < 1){
            return true;
        }
        return false;
    }

    //Kiểm tra tồn tại
    @Override
    public boolean kiemTraTonTai(String tableName,String columnOut, String columnIn, String value) {
        String sql = "select "+columnOut+" FROM "+tableName+" where "+columnIn+" = '"+value+"'";
        Query query = entityManager.createNativeQuery(sql);
        List<String> resultList = query.getResultList();
        if (resultList.size() != 0) {
            return false;
        }
        return true;
    }
}

