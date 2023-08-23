package com.example.edoc.daoImpl;

import com.example.edoc.dao.SpecializationDao;
import com.example.edoc.models.Specialization;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class SpecializationDaoImpl implements SpecializationDao {

    @Resource   SessionFactory sessionFactory;

    @Override
    public Boolean save(Specialization specialization) {
        Session sess = sessionFactory.getCurrentSession();
        try {
             sess.save(specialization);
             return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Specialization> findAll() {
        Session sess = sessionFactory.getCurrentSession();
        Criteria criteria = sess.createCriteria(Specialization.class);
        return criteria.list();
    }

    @Override
    public boolean delete(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Specialization specialization = (Specialization) session.get(Specialization.class, id);
        try {
            session.delete(specialization);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Specialization findById(Long id) {
        Session sess = sessionFactory.getCurrentSession();
        Specialization specialization = (Specialization) sess.get(Specialization.class, id);
        return specialization;
    }
}
