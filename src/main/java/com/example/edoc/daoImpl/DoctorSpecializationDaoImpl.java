package com.example.edoc.daoImpl;

import com.example.edoc.DTO.DoctorSpecializationDTO;
import com.example.edoc.dao.DoctorSpecializationDao;
import com.example.edoc.models.DoctorSpecialization;
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
public class DoctorSpecializationDaoImpl implements DoctorSpecializationDao {
    @Resource
    SessionFactory sessionFactory;

    @Override
    public Boolean save(DoctorSpecialization doctorSpecialization) {
        Session sess = sessionFactory.getCurrentSession();
        try {
            sess.save(doctorSpecialization);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<DoctorSpecialization> findAll() {
        Session sess = sessionFactory.getCurrentSession();
        Criteria criteria = sess.createCriteria(DoctorSpecialization.class);
        return criteria.list();
    }

    @Override
    public boolean delete(Long id) {
        Session session = sessionFactory.getCurrentSession();
        DoctorSpecialization doctorSpecialization = (DoctorSpecialization) session.get(DoctorSpecialization.class, id);
        try {
            session.delete(doctorSpecialization);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }



    @Override
    public DoctorSpecialization findById(Long id) {
        Session sess = sessionFactory.getCurrentSession();
        DoctorSpecialization doctorSpecialization = (DoctorSpecialization) sess.get(DoctorSpecialization.class, id);
        return doctorSpecialization;

    }

    @Override
    public List<DoctorSpecializationDTO> findAllByDoctorAndSpecialization(){
        String queryString="select d.name as doctorName, s.specialization_name as specializationName from tbl_doctor d, tbl_specialization s ,tbl_doctor_specialization ds  where ds.doctor_id = d.id and ds.specialization_id = s.id";
        Session session = sessionFactory.getCurrentSession();
        SQLQuery query = session.createSQLQuery(queryString);
        query.setResultTransformer(Transformers.aliasToBean(DoctorSpecializationDTO.class));
        return query.list();
    }

    @Override
    public List<DoctorSpecializationDTO> findByDoctorIDAndSpecialization(Long id){
        String queryString="select d.name as doctorName, s.specialization_name as specializationName from tbl_doctor d, tbl_specialization s ,tbl_doctor_specialization ds  where ds.doctor_id =:id and ds.doctor_id = d.id and ds.specialization_id = s.id";
        Session session = sessionFactory.getCurrentSession();
        SQLQuery query = session.createSQLQuery(queryString);
        query.setParameter("id",id);
        query.setResultTransformer(Transformers.aliasToBean(DoctorSpecializationDTO.class));
        return query.list();
    }

}
