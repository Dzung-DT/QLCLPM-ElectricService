package com.ptit.electricbill.dao.impl;

import com.ptit.electricbill.dao.DienKeDAO;
import com.ptit.electricbill.model.DienKe;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
@Transactional
public class DienKeDAOImpl implements DienKeDAO{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Object> getAll() {
        String sql = "SELECT  * from dienke order by MaKH desc, MaThang desc";
        Query query = entityManager.createNativeQuery(sql);
        List<Object> dienKeList = query.getResultList();
        return dienKeList;
    }

    @Override
    public void add(DienKe dienKe) {
        String sql = "INSERT INTO dienke (MaKH,MaThang,SoDienCu,SoDienMoi) VALUES ('"+dienKe.getMaKH()+"', '"+dienKe.getMaThang()+"', '"+dienKe.getSoDienCu()+"', '"+dienKe.getSoDienMoi()+"')";
        Query query = entityManager.createNativeQuery(sql);
        query.executeUpdate();
    }

    @Override
    public void delete(int IDDienKe) {
        String sql = "DELETE FROM dienke where id = '"+IDDienKe+"'";
        Query query = entityManager.createNativeQuery(sql);
        query.executeUpdate();
    }

    @Override
    public List<Object> searchByKHID(String KHID) {
        String sql = "SELECT  * from dienke where MaKH = '"+KHID+"' order by MaThang desc";
        Query query = entityManager.createNativeQuery(sql);
        List<Object> dienKeList = query.getResultList();
        return dienKeList;
    }

    @Override
    public boolean checkSoDien(String maKH, String maThang) {
        String sql = "SELECT * FROM dienke WHERE MaKH = " + maKH + " AND MaThang = " + maThang + "";
        Query query = entityManager.createNativeQuery(sql);
        List<Object> resultList;
        try {
            resultList = query.getResultList();
            if (resultList.size() == 0) {
                return true;
            }
        } catch (NoResultException nre) {}
        return false;
    }
}
