package com.ptit.electricbill.dao.impl;

import com.ptit.electricbill.dao.ThueDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
@Transactional
public class ThueDAOImpl implements ThueDAO {
    @Autowired
    private EntityManager entityManager;

    @Override
    public Double getGiaThue() {
        String sql = "SELECT giaThue from thue";
        Query query = entityManager.createNativeQuery(sql);
        return (Double) query.getSingleResult();
    }

    @Override
    public Integer getMaThue() {
        String sql = "SELECT id from thue";
        Query query = entityManager.createNativeQuery(sql);
        return (Integer) query.getSingleResult();
    }

    @Override
    public List<Object> getAll() {
        String sql = "SELECT * from thue";
        Query query = entityManager.createNativeQuery(sql);
        return query.getResultList();
    }
}
