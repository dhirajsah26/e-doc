package com.example.edoc.daoImpl;

import com.example.edoc.DTO.DiagnosisDTO;
import com.example.edoc.dao.DiagnosisDao;
import com.example.edoc.models.Diagnosis;
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
public class DiagnosisDaoImpl implements DiagnosisDao {
    @Resource SessionFactory sessionFactory;

    @Override
    public boolean save(Diagnosis diagnosis) {
        Session session= sessionFactory.getCurrentSession();
        try {
            session.save(diagnosis);
            return true;

        }catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Diagnosis diagnosis) {
        Session session=sessionFactory.getCurrentSession();
        try {
            session.update(diagnosis);
            return true;
        }catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Diagnosis> getAll() {
        Session session= sessionFactory.getCurrentSession();
        Criteria criteria=session.createCriteria(Diagnosis.class);

        return criteria.list();
    }

    @Override
    public boolean delete(Long id) {
        Session session= sessionFactory.getCurrentSession();
        Diagnosis diagnosis=(Diagnosis) session.get(Diagnosis.class, id);
        try {
            session.delete(diagnosis);
            return true;
        }catch(Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public Diagnosis getById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Diagnosis diagnosis= (Diagnosis) session.get(Diagnosis.class, id);
        return diagnosis;
    }



    @Override
    public DiagnosisDTO getDiagnosisDetailById(Long id) {
        String queryString="SELECT d.id, d.blood_pressure as bloodPressure, d.blood_glucose as bloodGlucose, d.body_weight as bodyWeight, d.body_mass_index as bodyMassIndex ,d.diagnosis_photo as diagnosisPhoto ,p.name as pName , p.id as patientId , do.id as doctorId ,do.name as dName FROM tbl_diagnosis d, tbl_doctor do,tbl_patient p where d.id=:id and d.patient_id=p.id and d.doctor_id=do.id";
        Session session = sessionFactory.getCurrentSession();
        SQLQuery query = session.createSQLQuery(queryString);
        query.setParameter("id", id);
        query.setResultTransformer(Transformers.aliasToBean(DiagnosisDTO.class));
        return (DiagnosisDTO) query.uniqueResult();
    }


    @Override
    public List<DiagnosisDTO> getDiagnosisDetail() {
        String queryString="SELECT d.id, d.blood_pressure as bloodPressure, d.blood_glucose as bloodGlucose, d.body_weight as bodyWeight, d.body_mass_index as bodyMassIndex , d.diagnosis_photo as diagnosisPhoto , p.id as patientId , p.name as pName  , do.id as doctorId , do.name as dName FROM tbl_diagnosis d,tbl_doctor do, tbl_patient p where d.patient_id=p.id and d.doctor_id=do.id ";
        Session session = sessionFactory.getCurrentSession();
        SQLQuery query = session.createSQLQuery(queryString);
        query.setResultTransformer(Transformers.aliasToBean(DiagnosisDTO.class));
        System.out.println(query.list().toString());
        return query.list();
    }

    public List<DiagnosisDTO> getDiagnosisByPatient(Long patientId){
        String queryString ="SELECT d.id, d.blood_pressure as bloodPressure, d.blood_glucose as bloodGlucose, d.body_weight as bodyWeight, d.body_mass_index as bodyMassIndex , d.diagnosis_photo as diagnosisPhoto , do.id as doctorId , do.name as dName FROM tbl_diagnosis d, tbl_doctor do where patient_id=:patientId and d.doctor_id=do.id";
        Session session = sessionFactory.getCurrentSession();
        SQLQuery query = session.createSQLQuery(queryString);
        query.setParameter("patientId", patientId);
        query.setResultTransformer(Transformers.aliasToBean(DiagnosisDTO.class));
        return query.list();
    }

    public List<DiagnosisDTO> getDiagnosisByDoctor(Long doctorId){
        String queryString ="SELECT di.id, di.blood_pressure as bloodPressure, di.blood_glucose as bloodGlucose, di.body_weight as bodyWeight, di.body_mass_index as bodyMassIndex , di.diagnosis_photo as diagnosisPhoto , do.id as doctorId , do.name as dName, p.id as patientId , p.name as pName  FROM tbl_diagnosis di, tbl_patient p , tbl_doctor do where doctor_id=:doctorId and di.doctor_id=do.id and di.patient_id=p.id";
        Session session = sessionFactory.getCurrentSession();
        SQLQuery query = session.createSQLQuery(queryString);
        query.setParameter("doctorId", doctorId);
        query.setResultTransformer(Transformers.aliasToBean(DiagnosisDTO.class));
        return query.list();
    }

}
