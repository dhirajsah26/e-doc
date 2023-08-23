package com.example.edoc.daoImpl;

import com.example.edoc.DTO.DoctorDTO;
import com.example.edoc.dao.DoctorDao;
import com.example.edoc.models.Doctor;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class DoctorDaoImpl implements DoctorDao {
    @Resource
    SessionFactory sessionFactory;
    @Override
    public Long save(Doctor doctor) {
        Session sess = sessionFactory.getCurrentSession();
        try {
            return (Long) sess.save(doctor);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Doctor> findAll() {
        Session sess = sessionFactory.getCurrentSession();
        Criteria criteria = sess.createCriteria(Doctor.class);
        return criteria.list();
    }

    @Override
    public List<DoctorDTO> findAllDoctorWithSpecialization() {
        String queryString ="select d.name , d.id, d.practicing_from_date as practicingFromDate, s.specialization_name as specializationName from tbl_doctor d, tbl_specialization s, tbl_doctor_specialization ds where d.id= ds.doctor_id and ds.specialization_id = s.id ";
        Session session = sessionFactory.getCurrentSession();
        SQLQuery query = session.createSQLQuery(queryString);
        query.setResultTransformer(Transformers.aliasToBean(DoctorDTO.class));
        return query.list();
    }

    @Override
    public boolean delete(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Doctor doctor = (Doctor) session.get(Doctor.class, id);
        try {
            session.delete(doctor);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Doctor doctor) {
        Session sess = sessionFactory.getCurrentSession();
        try {
            sess.update(doctor);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Doctor findById(Long id) {
        Session sess = sessionFactory.getCurrentSession();
        Doctor doctor = (Doctor) sess.get(Doctor.class, id);

        return doctor;
    }

    @Override
    public List<Doctor> findDoctorSelectedDetails() {
        Session session = sessionFactory.getCurrentSession();
        String queryString = "select * from tbl_doctor";
        SQLQuery query = session.createSQLQuery(queryString);
        query.setResultTransformer(Transformers.aliasToBean(Doctor.class));
        System.out.println(query);
        return query.list();
    }

    @Override
    public List<DoctorDTO> findDoctorBySpecializationID(Long id) {
        String queryString="select  d.name,  d.id from  tbl_doctor d,  tbl_doctor_specialization ds where  ds.specialization_id =:id  and ds.doctor_id = d.id;";
        Session session = sessionFactory.getCurrentSession();
        SQLQuery query = session.createSQLQuery(queryString);
        query.setParameter("id",id);
        query.setResultTransformer(Transformers.aliasToBean(DoctorDTO.class));
        return query.list();
    }

    @Override
    public List<DoctorDTO> findDoctorByID(Long id) {
        String queryString="select  d.name,  d.id from  tbl_doctor d where   d.id =:id";
        Session session = sessionFactory.getCurrentSession();
        SQLQuery query = session.createSQLQuery(queryString);
        query.setParameter("id",id);
        query.setResultTransformer(Transformers.aliasToBean(DoctorDTO.class));
        return query.list();
    }

    @Override
    public Doctor selectAdminByUsername(String username) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Doctor.class);
        criteria.add(Restrictions.eq("username", username));
        Doctor doctor = (Doctor)criteria.uniqueResult();
        return doctor;
    }


    @Override
    public List<DoctorDTO> findAllDoctorWithSpecialization(Long id) {
        String queryString ="select d.name , d.id, d.practicing_from_date as practicingFromDate, s.specialization_name as specializationName from tbl_doctor d, tbl_specialization s, tbl_doctor_specialization ds where d.id =:id and d.id= ds.doctor_id and ds.specialization_id = s.id ";
        Session session = sessionFactory.getCurrentSession();
        SQLQuery query = session.createSQLQuery(queryString);
        query.setParameter("id",id);
        query.setResultTransformer(Transformers.aliasToBean(DoctorDTO.class));
        return query.list();
    }
}
