package com.example.edoc.daoImpl;

import com.example.edoc.DTO.DoctorHospitalDTO;
import com.example.edoc.dao.DoctorHospitalDao;
import com.example.edoc.models.HospitalAffiliation;
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
public class DoctorHospitalDaoImpl implements DoctorHospitalDao {
    @Resource
    SessionFactory sessionFactory;
    @Override
    public Boolean save(HospitalAffiliation hospitalAffiliation) {
        Session sess = sessionFactory.getCurrentSession();
        try {
            sess.save(hospitalAffiliation);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<HospitalAffiliation> findAll() {
        Session sess = sessionFactory.getCurrentSession();
        Criteria criteria = sess.createCriteria(HospitalAffiliation.class);
        return criteria.list();
    }

    @Override
    public boolean delete(Long id) {
        Session session = sessionFactory.getCurrentSession();
        HospitalAffiliation hospitalAffiliation = (HospitalAffiliation) session.get(HospitalAffiliation.class, id);
        try {
            session.delete(hospitalAffiliation);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public HospitalAffiliation findById(Long id) {
        Session sess = sessionFactory.getCurrentSession();
        HospitalAffiliation hospitalAffiliation = (HospitalAffiliation) sess.get(HospitalAffiliation.class, id);
        return hospitalAffiliation;
    }

    @Override
    public List<DoctorHospitalDTO> findHospitalByDoctorID(Long id){

        String queryString="select  h.id, d.name as doctorName, h.hospital_name as hospitalName, h.city  , h.country, h.start_date as startDate ,h.end_date as endDate  from  tbl_doctor d , tbl_hospital h  where h.doctor_id =:id and h.doctor_id=d.id";
        Session session = sessionFactory.getCurrentSession();
        SQLQuery query = session.createSQLQuery(queryString);
        query.setParameter("id",id);
        query.setResultTransformer(Transformers.aliasToBean(DoctorHospitalDTO.class));
        return query.list();

    }

    @Override
    public List<DoctorHospitalDTO> findHospitalAndDoctorName(){
        String queryString="select h.id, d.name as doctorName, h.hospital_name as hospitalName, h.city  , h.country, h.start_date as startDate, h.end_date as endDate  from  tbl_doctor d ,tbl_hospital h  where  h.doctor_id=d.id";
        Session session = sessionFactory.getCurrentSession();
        SQLQuery query = session.createSQLQuery(queryString);
        query.setResultTransformer(Transformers.aliasToBean(DoctorHospitalDTO.class));
        return query.list();
    }
}
