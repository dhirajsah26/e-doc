package com.example.edoc.daoImpl;

import com.example.edoc.DTO.PatientDTO;
import com.example.edoc.dao.PatientDao;
import com.example.edoc.models.Patient;
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
public class PatientDaoImpl implements PatientDao {

    @Resource
    SessionFactory sessionFactory;

    @Override
    public Long save(Patient patient) {
        Session sess = sessionFactory.getCurrentSession();
        try {
            return (Long) sess.save(patient);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Patient> findAll() {
        Session sess = sessionFactory.getCurrentSession();
        Criteria criteria = sess.createCriteria(Patient.class);
        return criteria.list();
    }

    @Override
    public boolean delete(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Patient patient = (Patient) session.get(Patient.class, id);
        try {
            session.delete(patient);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Patient patient) {
        Session sess = sessionFactory.getCurrentSession();
        try {
            sess.update(patient);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public Patient findById(Long id) {
        Session sess = sessionFactory.getCurrentSession();
        Patient patient = (Patient) sess.get(Patient.class, id);
        return patient;
    }

    @Override
    public List<PatientDTO> findEmployeeSelectedDetails() {
        Session session = sessionFactory.getCurrentSession();
        String queryString = "select * from tbl_patient";
        SQLQuery query = session.createSQLQuery(queryString);
        query.setResultTransformer(Transformers.aliasToBean(PatientDTO.class));
        System.out.println(query);
        return query.list();
    }

	@Override
	public Patient selectPatientByUsername(String username) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Patient.class);
		criteria.add(Restrictions.eq("username", username));
		Patient patient = (Patient)criteria.uniqueResult();
		return patient;
	}


	@Override
	public List<PatientDTO> findPatientByID(Long id) {
		String queryString ="select p.id , p.name, p.date_of_birth as dateOfBirth, p.address ,p.gender , p.landline_number as landlineNumber , p.mobile_number as mobileNumber from tbl_patient p where p.id =:id  ";
		Session session = sessionFactory.getCurrentSession();
		SQLQuery query = session.createSQLQuery(queryString);
		query.setParameter("id",id);
		query.setResultTransformer(Transformers.aliasToBean(PatientDTO.class));
		return query.list();
	}


}


















