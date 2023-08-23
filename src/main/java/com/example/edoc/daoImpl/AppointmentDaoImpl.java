package com.example.edoc.daoImpl;

import com.example.edoc.DTO.AppointmentDTO;
import com.example.edoc.dao.AppointmentDao;
import com.example.edoc.models.Appointment;
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
public class AppointmentDaoImpl implements AppointmentDao {
    @Resource
    SessionFactory sessionFactory;


    @Override
    public boolean saveOrUpdate(Appointment appointment) {
        Session session = sessionFactory.getCurrentSession();

        try{
            session.saveOrUpdate(appointment);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }



    @Override
    public AppointmentDTO getAppointmentByDoctorId(Long id) {
        String queryString="SELECT id, patient_id, doctor_id , appointment_date , description, appointment_time, status FROM tbl_appointment a where a.doctor_id =:id";
        Session session = sessionFactory.getCurrentSession();
        SQLQuery query = session.createSQLQuery(queryString);
        query.setParameter("id", id);
        query.setResultTransformer(Transformers.aliasToBean(AppointmentDTO.class));
        return (AppointmentDTO) query.uniqueResult();
    }

    @Override
    public List<AppointmentDTO> getAppointmentsByDoctorId(Long id) {

        String queryString="SELECT a.id, a.patient_id, a.doctor_id , a.appointment_date , a.description, a.appointment_time, a.status, d.name as doctorName , p.name as patientName FROM tbl_appointment a ,tbl_patient p, tbl_doctor d where a.doctor_id =:id and a.patient_id=p.id and a.doctor_id= d.id";
        Session session = sessionFactory.getCurrentSession();
        SQLQuery query = session.createSQLQuery(queryString);
        query.setParameter("id", id);
        query.setResultTransformer(Transformers.aliasToBean(AppointmentDTO.class));
        return query.list();
    }

    @Override
    public List<AppointmentDTO> getAppointmentsByPatientId(Long id) {

        String queryString="SELECT a.id, a.patient_id, a.doctor_id , a.appointment_date , p.name as patientName ,d.name as doctorName, a.description, a.appointment_time, a.status FROM tbl_appointment a ,tbl_patient p, tbl_doctor d where a.patient_id =:id and a.patient_id=p.id and a.doctor_id= d.id";
        Session session = sessionFactory.getCurrentSession();
        SQLQuery query = session.createSQLQuery(queryString);
        query.setParameter("id", id);
        query.setResultTransformer(Transformers.aliasToBean(AppointmentDTO.class));
        return query.list();
    }


    @Override
    public List<Appointment> getAll() {
        Session session= sessionFactory.getCurrentSession();
        Criteria criteria=session.createCriteria(Appointment.class);
        return criteria.list();
    }

    @Override
    public List<AppointmentDTO> getAllAppointment() {
        String queryString ="select d.name as doctorName, p.id as patient_id , d.id as doctor_id,p.name as patientName , a.appointment_date , a.id  , a.appointment_time from tbl_doctor d, tbl_patient p,tbl_appointment a where a.doctor_id=d.id and a.patient_id=p.id";
        Session session = sessionFactory.getCurrentSession();
        SQLQuery query = session.createSQLQuery(queryString);
        query.setResultTransformer(Transformers.aliasToBean(AppointmentDTO.class));
        return query.list();
    }

    @Override
    public boolean delete(Long id){
        Session session= sessionFactory.getCurrentSession();
        Appointment appointment=(Appointment) session.get(Appointment.class, id);
        try {
            session.delete(appointment);
            return true;
        }catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
