package com.example.edoc.daoImpl;

import com.example.edoc.DTO.UserRoleDTO;
import com.example.edoc.dao.UserRoleDao;
import com.example.edoc.models.UserRole;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Repository
@Transactional
public class UserRoleDaoImpl implements UserRoleDao {
	
	@Resource
	SessionFactory sessionfactory;

	@Override
	@Transactional
	public int saveRole(UserRole r) {
		Session session = sessionfactory.getCurrentSession();
		int id = (Integer)session.save(r);
		return id;
	}

	@Override
	@Transactional
	public boolean updateRole(UserRole r) {
		Session session = sessionfactory.getCurrentSession();
		session.saveOrUpdate(r);
		return true;
	}

	@Override
	@Transactional
	public boolean deleteRoleById(int id) {
		Session session = sessionfactory.getCurrentSession();
		UserRole role = (UserRole) session.get(UserRole.class, id);
		session.delete(role);
		return true;
	}

	@Override
	public List<UserRole> selectAllRole() {
		Session session = sessionfactory.getCurrentSession();
		Criteria criteria = session.createCriteria(UserRole.class);
		List<UserRole> rlist = criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY).list();
		return rlist;
	}

	@Override
	@Transactional
	public UserRole selectRoleById(int id) {
		Session session = sessionfactory.getCurrentSession();
		UserRole role = (UserRole) session.get(UserRole.class,id);
		return role;
	}

	@Override
	public UserRoleDTO fetchById(int id) {
		Session session = sessionfactory.getCurrentSession();
		Query query = session.createSQLQuery("select id,name,DATE_FORMAT(created_date, '%Y-%m-%d %H:%i:%s') as created_date from tbl_user_role where id='"+id+"'").setResultTransformer(Transformers.aliasToBean(UserRoleDTO.class));
		UserRoleDTO userRoleDTO = (UserRoleDTO) query.uniqueResult();
		return userRoleDTO;
	}

	@Override
	public List<UserRoleDTO> findAllRoles() {
		String queryString = "select distinct id, name, DATE_FORMAT(created_date, '%Y-%m-%d %H:%i:%s') as created_date from tbl_user_role  order by name asc";		
		Session session = sessionfactory.getCurrentSession();
		Query query = session.createSQLQuery(queryString).setResultTransformer(Transformers.aliasToBean(UserRoleDTO.class));
		return query.list();
	}

}
