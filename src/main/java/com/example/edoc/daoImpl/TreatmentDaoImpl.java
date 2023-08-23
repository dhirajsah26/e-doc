package com.example.edoc.daoImpl;

import com.example.edoc.DTO.TreatmentDTO;
import com.example.edoc.dao.TreatmentDao;
import com.example.edoc.models.Treatment;
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
public class TreatmentDaoImpl implements TreatmentDao {

    @Resource
    SessionFactory sessionFactory;

    @Override
    public boolean save(Treatment treatment) {
        Session session = sessionFactory.getCurrentSession();
        try {
            session.save(treatment);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Treatment treatment) {
        Session session = sessionFactory.getCurrentSession();
        try {
            session.update(treatment);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Treatment> getAll() {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Treatment.class);

        return criteria.list();
    }

    @Override
    public boolean delete(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Treatment treatment = (Treatment) session.get(Treatment.class, id);
        try {
            session.delete(treatment);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public Treatment getById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Treatment treatment = (Treatment) session.get(Treatment.class, id);
        return treatment;
    }


    @Override
    public TreatmentDTO getTreatmentDetailById(Long id) {

        String queryString = "SELECT t.id, t.prescription_feedback as prescriptionFeedback, t.prescription_photo as prescriptionPhoto, t.date_of_prescription as dateOfPrescription, p.name as patientName , p.id as pId, d.id as doctorId , d.name as dName FROM tbl_treatment t, tbl_doctor d, tbl_patient p where t.id=:id and t.patient_id=p.id and t.doctor_id=d.id ";
        Session session = sessionFactory.getCurrentSession();
        SQLQuery query = session.createSQLQuery(queryString);
        query.setParameter("id", id);
        query.setResultTransformer(Transformers.aliasToBean(TreatmentDTO.class));
        return (TreatmentDTO) query.uniqueResult();
    }


    @Override
    public List<TreatmentDTO> getTreatmentDetail() {
        String queryString = "SELECT t.id, t.prescription_feedback as prescriptionFeedback, t.prescription_photo as prescriptionPhoto, t.date_of_prescription as dateOfPrescription, p.name as patientName , p.id as pId, d.id as doctorId , d.name as dName FROM tbl_treatment t, tbl_doctor d, tbl_patient p where t.patient_id=p.id and t.doctor_id=d.id ";
        Session session = sessionFactory.getCurrentSession();
        SQLQuery query = session.createSQLQuery(queryString);
        query.setResultTransformer(Transformers.aliasToBean(TreatmentDTO.class));
        return query.list();
    }
	@Override
    public List<TreatmentDTO> getTreatmentByPatient(Long patientId) {
        String queryString = "select t.id, t.prescription_feedback as prescriptionFeedback, t.prescription_photo as prescriptionPhoto, t.date_of_prescription as dateOfPrescription , p.name as patientName , p.id as pId , d.id as doctorId , d.name as dName from tbl_treatment t , tbl_doctor d, tbl_patient p where t.patient_id=:patientId and t.patient_id=p.id and t.doctor_id=d.id";
        Session session = sessionFactory.getCurrentSession();
        SQLQuery query = session.createSQLQuery(queryString);
        query.setParameter("patientId", patientId);
        query.setResultTransformer(Transformers.aliasToBean(TreatmentDTO.class));
        return query.list();
    }
	@Override
	public List<TreatmentDTO> getTreatmentByDoctor(Long doctorId) {
		String queryString = "select t.id, t.prescription_feedback as prescriptionFeedback, t.prescription_photo as prescriptionPhoto, t.date_of_prescription as dateOfPrescription , p.name as patientName , p.id as pId , d.id as doctorId , d.name as dName  from tbl_treatment t , tbl_doctor d , tbl_patient p where t.doctor_id=:doctorId and t.patient_id=p.id and t.doctor_id=d.id";
		Session session = sessionFactory.getCurrentSession();
		SQLQuery query = session.createSQLQuery(queryString);
		query.setParameter("doctorId", doctorId);
		query.setResultTransformer(Transformers.aliasToBean(TreatmentDTO.class));
		return query.list();
	}

}
