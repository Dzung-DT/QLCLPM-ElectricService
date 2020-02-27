package com.ptit.electricbill.dao.impl;

import com.ptit.electricbill.dao.KhachHangDAO;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
        String sql = "SELECT * FROM khachhang where MaKH = '"+maKH+"'";
        Query query = entityManager.createNativeQuery(sql);
        Object user = query.getResultList();
        return user;
    }
}
