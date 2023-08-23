package com.example.edoc.daoImpl;

import com.example.edoc.DTO.UserRoleSchoolDTO;
import com.example.edoc.dao.UserRoleSchoolDao;
import com.example.edoc.models.UserRoleSchool;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.IntegerType;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Repository
@Transactional
public class UserRoleSchoolDaoImpl implements UserRoleSchoolDao {
	
	@Resource SessionFactory sessionFactory;

	@Override
	public Long save(UserRoleSchool userRoleSchool) {
		Session session = sessionFactory.getCurrentSession();
		try {
			return (Long) session.save(userRoleSchool);
		} catch (Exception e) {
			return 0L;
		}
	}

	@Override
	public boolean update(UserRoleSchool userRoleSchool) {
		Session session = sessionFactory.getCurrentSession();
		try {
			session.update(userRoleSchool);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean delete(UserRoleSchool userRoleSchool) {
		Session session = sessionFactory.getCurrentSession();
		try {
			session.delete(userRoleSchool);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<UserRoleSchool> selectAllRole() {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(UserRoleSchool.class);
		criteria.addOrder(Order.desc("createdDate"));
		return criteria.list();
	}

	@Override
	public UserRoleSchool selectById(Long id) {
		Session session = sessionFactory.getCurrentSession();
		UserRoleSchool userRoleSchool = (UserRoleSchool) session.get(UserRoleSchool.class, id);
		return userRoleSchool;
	}

	@Override
	public boolean isNameAvailable(String name,Long ignorableId) {
		if(!name.trim().isEmpty()) {
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(UserRoleSchool.class);
			criteria.add(Restrictions.eq("name", name.trim()));
			UserRoleSchool userRoleSchool = (UserRoleSchool) criteria.setMaxResults(1).uniqueResult();
			if(userRoleSchool == null || userRoleSchool.getId().equals(ignorableId)) return true;
			return false;
		}
		return true;
	}

	@Override
	public List<UserRoleSchool> selectAllActiveRoleByTeamId(Integer teamId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(UserRoleSchool.class);
		criteria.add(Restrictions.eq("team.id", teamId)).add(Restrictions.eq("status", true));
		return criteria.list();
	}

	@Override
	public List<UserRoleSchoolDTO> findAll(boolean activeOnly, boolean includeSuperUserRole) {
		String queryString = "select id, name from tbl_user_role_school where";
		if (activeOnly) queryString += " status=1";
		if (!includeSuperUserRole) queryString += ((activeOnly) ? " and " : "") + "is_super_user_role=0";
		queryString += " order by name asc";
		
		Session session = sessionFactory.getCurrentSession();
		SQLQuery query = session.createSQLQuery(queryString);
		query.setResultTransformer(Transformers.aliasToBean(UserRoleSchoolDTO.class));
		
		return query.list();
	}
	
	@Override
	public boolean hasAccessPrivilege(Long userRoleId, String permissionKey) {
		String queryString = "SELECT COUNT(urm.id) total FROM tbl_user_role_menu urm JOIN tbl_module_new mn ON mn.id=urm.module_new_id WHERE urm.user_role_menu_info_id=(SELECT id FROM tbl_user_role_menu_info WHERE user_role_school_id=:userRoleId) AND mn.permission_key=:permissionKey";
		
		Session session = sessionFactory.getCurrentSession();
		SQLQuery query = session.createSQLQuery(queryString);
		query.setParameter("userRoleId", userRoleId).setParameter("permissionKey", permissionKey);
		query.addScalar("total", IntegerType.INSTANCE);
		Integer count = (Integer) query.uniqueResult();
		
		if (count != null && count > 0) return true;
		return false;
	}

	@Override
	public UserRoleSchoolDTO findBySchoolAccountId(Integer schoolAccountId) {
		String queryString = "select id, name, is_super_user_role as superUserRole from tbl_user_role_school where id=(SELECT role_id FROM tbl_admin WHERE id=:schoolAccountId)";
		
		Session session = sessionFactory.getCurrentSession();
		SQLQuery query = session.createSQLQuery(queryString);
		query.setParameter("schoolAccountId", schoolAccountId).setResultTransformer(Transformers.aliasToBean(UserRoleSchoolDTO.class));
		
		return (UserRoleSchoolDTO) query.setMaxResults(1).uniqueResult();
	}

}
