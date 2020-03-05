package com.ptit.electricbill.dao.impl;

import com.ptit.electricbill.dao.DonGiaDAO;
import com.ptit.electricbill.model.DonGia;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
@Transactional
public class DonGiaDAOImpl implements DonGiaDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Object> getAll() {
        String sql = "SELECT  * from dongia";
        Query query = entityManager.createNativeQuery(sql);
        List<Object> donGiaList = query.getResultList();
        return donGiaList;
    }

    @Override
    public void update(DonGia donGia) {
        String sql = "UPDATE dongia SET Gia = '"+donGia.getGia()+"',GhiChu = '"+ donGia.getGhiChu()+"' WHERE (MaDG = '"+donGia.getMaDonGia()+"')";
        Query query = entityManager.createNativeQuery(sql);
        query.executeUpdate();
    }

    @Override
    public void add(DonGia donGia) {
        String sql = "INSERT INTO dongia (Gia,GhiChu) VALUES ('"+donGia.getGia()+"', '"+donGia.getGhiChu()+"')";
        Query query = entityManager.createNativeQuery(sql);
        query.executeUpdate();
    }

    @Override
    public void delete(int IDDonGia) {
        String sql = "DELETE FROM dongia where MaDG = '"+IDDonGia+"'";
        Query query = entityManager.createNativeQuery(sql);
        query.executeUpdate();
    }
}
