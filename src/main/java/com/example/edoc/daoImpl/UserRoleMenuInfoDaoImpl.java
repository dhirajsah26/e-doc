package com.example.edoc.daoImpl;

import com.example.edoc.DTO.UserRoleMenuDTO;
import com.example.edoc.dao.UserRoleMenuInfoDao;
import com.example.edoc.models.UserRoleMenuInfo;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.LongType;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Repository
@Transactional
public class UserRoleMenuInfoDaoImpl implements UserRoleMenuInfoDao {

	@Resource SessionFactory sessionFactory;
	@Override
	public Long save(UserRoleMenuInfo userRoleMenuInfo) {
		
		Session session = sessionFactory.getCurrentSession();
		try {
			return (Long) session.save(userRoleMenuInfo);
		} catch (Exception e) {
			return 0L;
		}
	}

	@Override
	public boolean update(UserRoleMenuInfo userRoleMenuInfo) {
		
		Session session = sessionFactory.getCurrentSession();
		try {
			session.update(userRoleMenuInfo);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean delete(UserRoleMenuInfo userRoleMenuInfo) {
		
		Session session = sessionFactory.getCurrentSession();
		try {
			session.delete(userRoleMenuInfo);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public UserRoleMenuInfo selectById(Long id) {
		
		Session session = sessionFactory.getCurrentSession();
		UserRoleMenuInfo userRoleMenuInfo = (UserRoleMenuInfo) session.get(UserRoleMenuInfo.class, id);
		return userRoleMenuInfo;
	}

	@Override
	public List<UserRoleMenuInfo> selectAllRoleByTeamId(Integer teamId) {
		
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(UserRoleMenuInfo.class);
		criteria.add(Restrictions.eq("team.id", teamId)).add(Restrictions.eq("deleted", false)).addOrder(Order.desc("createdDate")).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<UserRoleMenuInfo> userRoleMenuInfos = criteria.list();
		return userRoleMenuInfos;
	}
	
	@Override
	public UserRoleMenuInfo selectByUserRoleId(Long userRoleId) {
		
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(UserRoleMenuInfo.class);
		criteria.add(Restrictions.eq("userRoleSchool.id", userRoleId));
		return (UserRoleMenuInfo) criteria.uniqueResult();
	}

	@Override
	public boolean deleteByUserRoleId(Long userRoleId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(UserRoleMenuInfo.class);
		criteria.add(Restrictions.eq("userRoleSchool.id", userRoleId));
		UserRoleMenuInfo userRoleMenuInfo = (UserRoleMenuInfo) criteria.uniqueResult();
		if(userRoleMenuInfo != null) {
			session.delete(userRoleMenuInfo);
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteRemovedUserRoleMenuFromUserRoleMenuInfo(List<Long> userRoleMenuIds) {
		String queryString = "DELETE FROM tbl_user_role_menu where id in (:userRoleMenu)";
		Session session = sessionFactory.getCurrentSession();
		SQLQuery query = session.createSQLQuery(queryString);
		query.setParameterList("userRoleMenu", userRoleMenuIds);
		try {
			query.executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public List<UserRoleMenuDTO> getAllModuleIdsByUserRoleMenuInfoId(Long userRoleMenuInfoId){
		Session session = sessionFactory.getCurrentSession();
		
		String sql = "select urmi.id AS id, mn.id as menuId from tbl_user_role_menu_info urmi JOIN tbl_user_role_menu urm ON urm.user_role_menu_info_id=urmi.id "
						+ "JOIN tbl_module_new mn ON mn.id=urm.module_new_id WHERE urmi.id=:userRoleMenuInfoId";
		
		SQLQuery query = (SQLQuery) session.createSQLQuery(sql).setResultTransformer(Transformers.aliasToBean(UserRoleMenuDTO.class));
		query.setParameter("userRoleMenuInfoId", userRoleMenuInfoId);
		return query.list();
	}
	
	@Override
	public Long getUserRoleMenuInfoId(Long userRoleSchoolId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(UserRoleMenuInfo.class);
		criteria.add(Restrictions.eq("userRoleSchool.id", userRoleSchoolId));
		UserRoleMenuInfo userRoleMenuInfo = (UserRoleMenuInfo) criteria.uniqueResult();
		if(userRoleMenuInfo != null) {
			return userRoleMenuInfo.getId();
		}
		return 0L;
	}
	
	@Override
	public List<Long> getAllMenuIdsFromUserRoleMenu(Long userRoleMenuInfoId) {
		Session session = sessionFactory.getCurrentSession();
		SQLQuery query = session.createSQLQuery("select module_new_id from tbl_user_role_menu_info urmi join tbl_user_role_menu urm ON urm.user_role_menu_info_id=urmi.id join tbl_module_new mn ON mn.id=urm.module_new_id WHERE urmi.id=:userRoleMenuInfoId");
		query.setParameter("userRoleMenuInfoId", userRoleMenuInfoId);
		query.addScalar("module_new_id", LongType.INSTANCE);
		List<Long> menuIds = query.list();
		return menuIds;
	}

	@Override
	public boolean deleteAllMenuByUserRoleMenuInfoIdAndModuleIds(Long userRoleMenuInfoId, List<Long> moduleIds) {
		String queryString = "delete from tbl_user_role_menu where user_role_menu_info_id=:userRoleMenuInfoId and module_new_id in (:moduleIds)";
		
		Session session = sessionFactory.getCurrentSession();
		SQLQuery query = session.createSQLQuery(queryString);
		query.setParameter("userRoleMenuInfoId", userRoleMenuInfoId).setParameterList("moduleIds", moduleIds);
		
		try {
			query.executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<Long> findAllAssignedModuleIdByUserRoleMenuInfoId(Long userRoleMenuInfoId) {
		String queryString = "select module_new_id from tbl_user_role_menu where user_role_menu_info_id=:userRoleMenuInfoId";
		
		Session session = sessionFactory.getCurrentSession();
		SQLQuery query = session.createSQLQuery(queryString);
		query.setParameter("userRoleMenuInfoId", userRoleMenuInfoId);
		query.addScalar("module_new_id", LongType.INSTANCE);
		
		return query.list();
	}

	@Override
	public boolean deleteUnrelatedMenus(Integer teamId, Long packageId, List<Long> userRoleMenuInfoIds) {
		String queryString = "DELETE FROM tbl_user_role_menu WHERE module_new_id NOT IN (SELECT module_new_id FROM tbl_package_template WHERE package_template_info_id=:packageId) AND user_role_menu_info_id IN (:userRoleMenuInfoIds)";
		Session session = sessionFactory.getCurrentSession();
		SQLQuery query = session.createSQLQuery(queryString);
		query.setParameter("packageId", packageId).setParameterList("userRoleMenuInfoIds", userRoleMenuInfoIds);
		try {
			query.executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<Long> findAllUserRoleMenuInfoIdsByTeamId(Integer teamId) {
		String queryString = "select id from tbl_user_role_menu_info where team_id=:teamId";
		Session session = sessionFactory.getCurrentSession();
		SQLQuery query = session.createSQLQuery(queryString);
		query.setParameter("teamId", teamId);
		query.addScalar("id", LongType.INSTANCE);
		return query.list();
	}

 }
