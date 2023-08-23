package com.example.edoc.daoImpl;

import com.example.edoc.dao.UserRolePermissionNewDao;
import com.example.edoc.models.UserRolePermissionNew;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Repository
@Transactional
public class UserRolePermissionNewDaoImpl implements UserRolePermissionNewDao {
	
	@Resource
	SessionFactory sessionFactory;

	@Override
	public Long save(UserRolePermissionNew userRolePermission) {
		Session session = sessionFactory.getCurrentSession();
		try {
			return (Long) session.save(userRolePermission);
		} catch (Exception e) {
			return 0L;
		}
	}
	
	@Override
	public boolean update(UserRolePermissionNew userRolePermission) {
		Session session = sessionFactory.getCurrentSession();
		try {
			session.update(userRolePermission);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean deleteById(UserRolePermissionNew userRolePermission) {
		Session session = sessionFactory.getCurrentSession();
		try {
			session.delete(userRolePermission);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public UserRolePermissionNew selectById(Long id) {
		Session session = sessionFactory.getCurrentSession();
		UserRolePermissionNew userRolePermission = (UserRolePermissionNew) session.get(UserRolePermissionNew.class, id);
		return userRolePermission;
	}

	@Override
	public boolean deleteByUserRoleIdAndUserPermissionId(Long userRoleId, int permissionId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(UserRolePermissionNew.class);
		criteria.add(Restrictions.eq("userRoleSchool.id", userRoleId)).add(Restrictions.eq("userPermission.id", permissionId));
		UserRolePermissionNew userRolePermission = (UserRolePermissionNew) criteria.uniqueResult();
		if(userRolePermission != null) {
			session.delete(userRolePermission);
			return true;
		}
		return false;
	}

	@Override
	public Long getUserRolePermissionIdByUserRoleIdAndUserPermissionId(Long userRoleId, int permissionId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(UserRolePermissionNew.class);
		criteria.add(Restrictions.eq("userRoleSchool.id", userRoleId)).add(Restrictions.eq("userPermission.id", permissionId));
		UserRolePermissionNew userRolePermission = (UserRolePermissionNew) criteria.uniqueResult();
		if(userRolePermission != null) {
			return userRolePermission.getId();
		}
		return 0L;
	}

	@Override
	public List<UserRolePermissionNew> selectAllUserRolePermissionByRoleId(Long userRoleId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(UserRolePermissionNew.class);
		criteria.add(Restrictions.eq("userRoleSchool.id", userRoleId));
		List<UserRolePermissionNew> userRolePermissions = criteria.list();
		return userRolePermissions;
	}

	@Override
	public UserRolePermissionNew findByRoleIdAndPermissionKey(Long roleId, String permissionKey) {
		Session session = sessionFactory.getCurrentSession();
		String SQL = "select urp.* from tbl_user_role_permission_new urp join tbl_user_permission up on urp.user_permission_id=up.id where urp.user_role_id=:roleId and up.permission_key=:permissionKey";
		SQLQuery query = session.createSQLQuery(SQL).addEntity(UserRolePermissionNew.class);
		query.setParameter("roleId", roleId).setParameter("permissionKey", permissionKey);
		UserRolePermissionNew rolePermission = (UserRolePermissionNew) query.setMaxResults(1).uniqueResult();
		return rolePermission;
	}
}
