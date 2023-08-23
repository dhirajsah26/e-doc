package com.example.edoc.daoImpl;

import com.example.edoc.dao.SettingDao;
import com.example.edoc.models.Settings;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@Service
@Repository
@Transactional
public class SettingsDaoImpl implements SettingDao {
	
    @Resource
    SessionFactory sessionFactory;

	@Override
	public Boolean save(Settings setting) {
	    Session sess = sessionFactory.getCurrentSession();
        try {
            sess.save(setting);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
	}

	@Override
	public Boolean update(Settings setting) {
	    Session sess = sessionFactory.getCurrentSession();
        try {
            sess.update(setting);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
	}

	@Override
	public Settings findDate() {
		  Session sess = sessionFactory.getCurrentSession();
		  Criteria criteria = sess.createCriteria(Settings.class);		 
	        return (Settings) criteria.setMaxResults(1).uniqueResult();
	}

}
