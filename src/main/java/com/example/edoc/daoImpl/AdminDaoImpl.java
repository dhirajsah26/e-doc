package com.example.edoc.daoImpl;

import com.example.edoc.DTO.AdminDto;
import com.example.edoc.DTO.DashboardDTO;
import com.example.edoc.dao.AdminDao;
import com.example.edoc.models.Admin;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.List;

@Repository
@Transactional
public class AdminDaoImpl implements AdminDao {

    @Resource
    SessionFactory sessionfactory;

    @Override
    public int saveAdmin(Admin a) {
        Session session = sessionfactory.getCurrentSession();
        int id = (Integer) session.save(a);
        return id;
    }

    @Override
    public Admin selectAdminByUsername(String username) {
        Session session = sessionfactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Admin.class);

        criteria.add(Restrictions.eq("username", username));
        Admin admin = (Admin) criteria.uniqueResult();

        return admin;
    }

    @Override
    public boolean deleteAdminById(int id) {
        return false;
    }

    @Override
    public List<Admin> findAll() {
        Session session = sessionfactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Admin.class);
        criteria.addOrder(Order.desc("createdDate"));
        return criteria.list();
    }

    @Override
    public Admin selectAdminById(int id) {
        Session session = sessionfactory.getCurrentSession();
        Admin admin = (Admin) session.get(Admin.class, id);
        return admin;
    }

    @Override
    public List<AdminDto> fetchAll() {
        Session session = sessionfactory.getCurrentSession();
        Query query = session.createSQLQuery("select id, username from tbl_admin").setResultTransformer(Transformers.aliasToBean(AdminDto.class));
        List<AdminDto> admins = query.list();
        return admins;
    }

    @Override
    public AdminDto fetchById(int id) {
        Session session = sessionfactory.getCurrentSession();
        Query query = session.createSQLQuery("select id, username from tbl_admin where id='" + id + "'").setResultTransformer(Transformers.aliasToBean(AdminDto.class));
        AdminDto adminDto = (AdminDto) query.uniqueResult();
        return adminDto;
    }

    @Override
    public BigInteger countDoctor() {
        Session session = sessionfactory.getCurrentSession();
        Query query = session.createSQLQuery("select count(id) from tbl_doctor ");
        BigInteger count = (BigInteger) query.uniqueResult();
        return count;
    }

    @Override
    public BigInteger countPatient() {
        Session session = sessionfactory.getCurrentSession();
        Query query = session.createSQLQuery("select count(id) from tbl_patient ");
        BigInteger count = (BigInteger) query.uniqueResult();
        return count;
    }

    @Override
    public BigInteger countSpecialist() {
        Session session = sessionfactory.getCurrentSession();
        Query query = session.createSQLQuery("select count(id) from tbl_specialization ");
        BigInteger count = (BigInteger) query.uniqueResult();
        return count;
    }

    @Override
    public BigInteger countAppointment() {
        Session session = sessionfactory.getCurrentSession();
        Query query = session.createSQLQuery("select count(id) from tbl_appointment ");
        BigInteger count = (BigInteger) query.uniqueResult();
        return count;
    }

    @Override
    public List<DashboardDTO> getCont() {
        Session session = sessionfactory.getCurrentSession();
        Query query = session.createSQLQuery("SELECT  distinct doctor_id ,(select name from tbl_doctor where id=a.doctor_id) as doctorName, (SELECT count(patient_id) FROM tbl_appointment  WHERE (doctor_id = a.doctor_id) )as patientCount FROM  tbl_appointment a").setResultTransformer(Transformers.aliasToBean(DashboardDTO.class));
        List<DashboardDTO> dash = query.list();
        return dash;
    }

    @Override
    public boolean updateAdmin(Admin admin) {
        Session session = sessionfactory.getCurrentSession();
        try {
            session.update(admin);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
