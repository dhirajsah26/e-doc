package com.example.edoc.daoImpl;

import com.example.edoc.DTO.UserPermissionDTO;
import com.example.edoc.dao.UserPermissionDao;
import com.example.edoc.models.UserPermission;
import org.hibernate.*;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Repository
@Transactional
public class UserPermissionDaoImpl implements UserPermissionDao {
	
	@Resource
	SessionFactory sessionfactory;

	@Override
	public int savePermission(UserPermission p) {
		Session session = sessionfactory.getCurrentSession();
		int id = (Integer) session.save(p);
		return id;
	}

	@Override
	public boolean updatePermission(UserPermission p) {
		Session session = sessionfactory.getCurrentSession();
		try {
			session.saveOrUpdate(p);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean deletePermissionById(int id) {
		Session session = sessionfactory.getCurrentSession();
		UserPermission perm = (UserPermission) session.get(UserPermission.class, id);
		session.delete(perm);
		return true;
	}

	@Override
	public UserPermission selectPermissionById(int id) {
		Session session = sessionfactory.getCurrentSession();
		UserPermission perm = (UserPermission) session.get(UserPermission.class, id);
		return perm;
	}

	@Override
	public List<UserPermission> selectAllPermission() {
		Session session = sessionfactory.getCurrentSession();
		Criteria criteria = session.createCriteria(UserPermission.class);
		List<UserPermission> plist = criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY).list();
		return plist;
	}

	@Override
	public UserPermissionDTO fetchById(int id) {
		Session session = sessionfactory.getCurrentSession();
		Query query = session.createSQLQuery("select id, name, permission_key from tbl_user_permission where id='"+id+"'").setResultTransformer(Transformers.aliasToBean(UserPermissionDTO.class));
		UserPermissionDTO userPermissionDTO = (UserPermissionDTO) query.uniqueResult();
		return userPermissionDTO;
	}

	@Override
	public List<Integer> getUserPermissionIdsByUserRoleId(int userRoleId) {
		Session session = sessionfactory.getCurrentSession();
		SQLQuery query = session.createSQLQuery("select user_permission_id from tbl_user_role_permission where user_role_id='"+userRoleId+"'");
		List<Integer> userPermissionIds = query.list();
		return userPermissionIds;
	}

	@Override
	public List<UserPermissionDTO> findAllSyncableByPcUniqueIdAndSyncType(String pcUniqueId, String syncType) {
		String queryString = "SELECT DISTINCT up.id, up.name, up.permission_key, sfa.id AS syncId FROM tbl_user_permission up JOIN tbl_sync_from_admin sfa ON sfa.data_id=up.id AND sfa.table_name='tbl_user_permission' WHERE up.id IN (SELECT data_id FROM tbl_sync_from_admin WHERE table_name='tbl_user_permission' AND (sync_type=:syncType or sync_type='update') AND id NOT IN (SELECT sync_from_admin_id FROM tbl_synced_to_from_admin WHERE pcUniqueId=:pcUniqueId AND STATUS=1))";
		//String queryString = "SELECT id, name, permission_key FROM tbl_user_permission WHERE id IN (SELECT data_id FROM tbl_sync_from_admin WHERE table_name='tbl_user_permission' AND sync_type=:syncType AND id NOT IN (SELECT sync_from_admin_id FROM tbl_synced_to_from_admin WHERE pcUniqueId=:pcUniqueId AND STATUS=1))";
		Session session = sessionfactory.getCurrentSession();
		SQLQuery query = session.createSQLQuery(queryString);
		query.setParameter("syncType", syncType).setParameter("pcUniqueId", pcUniqueId);
		query.setResultTransformer(Transformers.aliasToBean(UserPermissionDTO.class));
		return query.list();
	}

	@Override
	public boolean isNameAvailable(String name, Integer ignorableId) {
		if(!name.trim().isEmpty()) {
			Session session = sessionfactory.getCurrentSession();
			Criteria criteria = session.createCriteria(UserPermission.class);
			criteria.add(Restrictions.eq("name", name.trim()));
			if (ignorableId != null) criteria.add(Restrictions.ne("id", ignorableId));
			UserPermission userPermission =  (UserPermission) criteria.setMaxResults(1).uniqueResult();
			if(userPermission == null) return true;
			return false;
		}
		return true;
	}

	/**
	 * sub module id is fetched as module id in case of module having role MENU_LINE 
	 * because MENU_LINE is not displayed on assign user role permission page
	 */
	@Override
	public List<UserPermission> findAllByPackageId(Long packageId) {
		String queryString = "select * from tbl_user_permission where module_new_id in (select distinct module_new_id from tbl_package_template where package_template_info_id=:packageId)";
		Session session = sessionfactory.getCurrentSession();
		SQLQuery query = session.createSQLQuery(queryString);
		query.setParameter("packageId", packageId);
		query.addEntity(UserPermission.class);
		return query.list();
	}
	
	@Override
	public List<Integer> getUserPermissionIdsByUserRoleId(Long userRoleId) {
		Session session = sessionfactory.getCurrentSession();
		SQLQuery query = session.createSQLQuery("select user_permission_id from tbl_user_role_permission_new where user_role_id='"+userRoleId+"'");
		List<Integer> userPermissionIds = query.list();
		return userPermissionIds;
	}

	@Override
	public List<UserPermissionDTO> findAllByModulePackageId(Long packageId) {
		String queryString = "select DISTINCT up.id, up.name, IF(mn.role != 'MENU_LINE', up.module_new_id, mn.parent_module_id) as moduleId from tbl_user_permission up join tbl_module_new mn on mn.id=up.module_new_id where up.module_new_id in (select distinct module_new_id from tbl_package_template where package_template_info_id=:packageId) order by up.name asc";
		Session session = sessionfactory.getCurrentSession();
		SQLQuery query = session.createSQLQuery(queryString);
		query.setParameter("packageId", packageId).setResultTransformer(Transformers.aliasToBean(UserPermissionDTO.class));
		return query.list();
	}

}
