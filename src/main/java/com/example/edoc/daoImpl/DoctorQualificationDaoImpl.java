package com.example.edoc.daoImpl;

import com.example.edoc.DTO.DoctorQualificationDTO;
import com.example.edoc.dao.DoctorQualificationDao;
import com.example.edoc.models.Qualification;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class DoctorQualificationDaoImpl implements DoctorQualificationDao {
    @Resource
    SessionFactory sessionFactory;
    @Override
    public Boolean save(Qualification qualification) {
        Session sess = sessionFactory.getCurrentSession();
        try {
            sess.save(qualification);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Qualification> findAll() {
        Session sess = sessionFactory.getCurrentSession();
        Criteria criteria = sess.createCriteria(Qualification.class);
        return criteria.list();
    }

    @Override
    public boolean delete(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Qualification qualification = (Qualification) session.get(Qualification.class, id);
        try {
            session.delete(qualification);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Qualification findById(Long id) {
        Session sess = sessionFactory.getCurrentSession();
        Qualification qualification = (Qualification) sess.get(Qualification.class, id);
        return qualification;
    }

    @Override
    public List<DoctorQualificationDTO> findQualificationByDoctorID(Long id){

        String queryString="select q.id, d.name as doctorName, q.qualification_name as qualificationName,q.institute_name as instituteName , q.procurement_year as procurementYear from  tbl_doctor d ,tbl_qualification q  where q.doctor_id =:id and q.doctor_id=d.id";
        Session session = sessionFactory.getCurrentSession();
        SQLQuery query = session.createSQLQuery(queryString);
        query.setParameter("id",id);
        query.setResultTransformer(Transformers.aliasToBean(DoctorQualificationDTO.class));
        return query.list();

    }

    @Override
    public List<DoctorQualificationDTO> findQualificationAndDoctorName(){
        String queryString="select q.id, d.name as doctorName, q.qualification_name as qualificationName,q.institute_name as instituteName , q.procurement_year as procurementYear from  tbl_doctor d ,tbl_qualification q  where q.doctor_id = d.id";
        Session session = sessionFactory.getCurrentSession();
        SQLQuery query = session.createSQLQuery(queryString);
        query.setResultTransformer(Transformers.aliasToBean(DoctorQualificationDTO.class));
        return query.list();
    }
}
