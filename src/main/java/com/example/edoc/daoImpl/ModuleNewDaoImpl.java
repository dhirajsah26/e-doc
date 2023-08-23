package com.example.edoc.daoImpl;

import com.example.edoc.DTO.ModuleNewAdvancedDTO;
import com.example.edoc.DTO.ModuleNewDTO;
import com.example.edoc.dao.ModuleNewDao;
import com.example.edoc.models.ModuleNew;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public class ModuleNewDaoImpl implements ModuleNewDao {
	
	@Resource
	SessionFactory sessionfactory;

	@Override
	public Long save(ModuleNew moduleNew) {
		Session session = sessionfactory.getCurrentSession();
		session.save(moduleNew);
		try {
			return (Long) session.save(moduleNew);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean update(ModuleNew moduleNew) {
		Session session = sessionfactory.getCurrentSession();
		try {
			session.saveOrUpdate(moduleNew);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean deleteById(Long id) {
		Session session = sessionfactory.getCurrentSession();
		ModuleNew module = (ModuleNew) session.get(ModuleNew.class, id);
		if (module != null) {
			try {
				session.delete(module);
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

	@Override
	public ModuleNew findById(Long id) {
		Session session = sessionfactory.getCurrentSession();
		return (ModuleNew) session.get(ModuleNew.class, id);
	}

	@Override
	public List<ModuleNew> findAll(boolean activeOnly) {
		Session session = sessionfactory.getCurrentSession();
		Criteria criteria = session.createCriteria(ModuleNew.class);
		criteria.addOrder(Order.desc("createdDate"));
		if (activeOnly) criteria.add(Restrictions.eq("status", true));
		return criteria.list();
	}

	@Override
	public List<ModuleNewDTO> findAllByRoleParentModuleIdAndStatus(String role, Long parentModuleId, boolean activeOnly) {
		String queryString = "select distinct id, name from tbl_module_new where role=:role";
		if (activeOnly) queryString += " and status=:status";
		if (parentModuleId != null) queryString += " and parent_module_id=:parentModuleId";
		queryString += " order by rank asc";
		
		Session session = sessionfactory.getCurrentSession();
		SQLQuery query = session.createSQLQuery(queryString);
		query.setParameter("role", role);
		if (activeOnly) query.setParameter("status", activeOnly);
		if (parentModuleId != null) query.setParameter("parentModuleId", parentModuleId);
		
		query.setResultTransformer(Transformers.aliasToBean(ModuleNewDTO.class));
		return query.list();
	}

	@Override
	public ModuleNew findByIdWithJoins(Long id) {
		Session session = sessionfactory.getCurrentSession();
		ModuleNew moduleNew = (ModuleNew) session.get(ModuleNew.class, id);
		return moduleNew;
	}

	@Override
	public boolean deleteAllAssignedRolesByModuleId(Long moduleId) {
		String queryString = "delete from tbl_module_role where module_id=:moduleId";
		Session session = sessionfactory.getCurrentSession();
		SQLQuery query = session.createSQLQuery(queryString);
		query.setParameter("moduleId", moduleId);
		try {
			query.executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<ModuleNewDTO> findAllActiveByUserRoleId(Integer userRoleId, boolean includeNonViewMenu) {
		String queryString = "select distinct id, name, target_url as targetUrl, role, parent_module_id as parentModuleId, icon, rank from tbl_module_new where id in (select module_id from tbl_module_role where user_role_id=:userRoleId) and status=1 ";
		if (!includeNonViewMenu) queryString += "and is_view_menu=1 ";
		queryString += "order by FIELD(role, 'MODULE', 'SUB_MODULE', 'MENU_LINE'), rank asc";
				
		Session session = sessionfactory.getCurrentSession();
		SQLQuery query = session.createSQLQuery(queryString);
		query.setParameter("userRoleId", userRoleId);
		query.setResultTransformer(Transformers.aliasToBean(ModuleNewDTO.class));
		return query.list();
	}

	@Override
	public String fetchHelpContentByTargetUrl(String targetUrl) {
		String queryString = "select helpSection from tbl_module_new where target_url=:targetUrl";
		Session session = sessionfactory.getCurrentSession();
		SQLQuery query = session.createSQLQuery(queryString);
		query.setParameter("targetUrl", targetUrl);
		query.addScalar("helpSection", new StringType());
		return (String) query.setMaxResults(1).uniqueResult();
	}
	
	@Override
	public List<ModuleNewDTO> findAllActive(){
		String queryString = "select distinct id, name, target_url as targetUrl, role, parent_module_id as parentModuleId, icon, rank from tbl_module_new where  status=1 order by FIELD(role, 'MODULE', 'SUB_MODULE', 'MENU_LINE'), rank asc";
		Session session = sessionfactory.getCurrentSession();
		SQLQuery query = session.createSQLQuery(queryString);
		query.setResultTransformer(Transformers.aliasToBean(ModuleNewDTO.class));
		return query.list();
	}

	@Override
	public List<ModuleNewDTO> findAllSelected(Long packageInfoId){
		String queryString = "select distinct m.id as id, m.name as name, m.target_url as targetUrl, m.role as role, m.parent_module_id as parentModuleId, m.icon as icon, m.rank as rank from tbl_module_new as m JOIN tbl_package_template as pt "
								+ "ON pt.module_new_id = m.id JOIN tbl_package_template_info as pti ON pti.id = pt.package_template_info_id where pti.id=:packageInfoId AND m.status=1 order by FIELD(m.role, 'MODULE', 'SUB_MODULE', 'MENU_LINE'), m.rank asc";
		Session session = sessionfactory.getCurrentSession();
		SQLQuery query = session.createSQLQuery(queryString);
		query.setParameter("packageInfoId", packageInfoId);
		query.setResultTransformer(Transformers.aliasToBean(ModuleNewDTO.class));
		return query.list();
	}
	
	@Override
	public List<ModuleNewDTO> findAllModule(String role, boolean activeOnly) {
		String queryString = "select distinct id, name from tbl_module_new where role=:role";
		if (activeOnly) queryString += " and status=:status";
		queryString += " order by rank asc";
		
		Session session = sessionfactory.getCurrentSession();
		SQLQuery query = session.createSQLQuery(queryString);
		query.setParameter("role", role);
		if (activeOnly) query.setParameter("status", activeOnly);
		query.setResultTransformer(Transformers.aliasToBean(ModuleNewDTO.class));
		return query.list();
	}
	
	@Override
	public List<ModuleNewDTO> findAllSelectedByUserRoleMenuId(Long userRoleMenuInfoId){
		String queryString = "select distinct m.id as id, m.name as name, m.target_url as targetUrl, m.role as role, m.parent_module_id as parentModuleId, m.icon as icon, m.rank as rank from tbl_module_new as m JOIN tbl_user_role_menu as urm "
								+ "ON urm.module_new_id = m.id JOIN tbl_user_role_menu_info as urmi ON urmi.id=urm.user_role_menu_info_id where urmi.id=:userRoleMenuInfoId AND m.status=1 order by FIELD(m.role, 'MODULE', 'SUB_MODULE', 'MENU_LINE'), m.rank asc";
		Session session = sessionfactory.getCurrentSession();
		SQLQuery query = session.createSQLQuery(queryString);
		query.setParameter("userRoleMenuInfoId", userRoleMenuInfoId);
		query.setResultTransformer(Transformers.aliasToBean(ModuleNewDTO.class));
		return query.list();
	}

	@Override
	public List<ModuleNewDTO> findAllActiveByUserRoleSchoolId(Long userRoleSchoolId, boolean includeNonViewMenu) {
		String queryString = "SELECT DISTINCT id, name, target_url AS targetUrl, role, parent_module_id AS parentModuleId, icon, rank FROM tbl_module_new WHERE id IN (SELECT module_new_id FROM tbl_user_role_menu WHERE user_role_menu_info_id=(SELECT id FROM tbl_user_role_menu_info WHERE user_role_school_id=:userRoleSchoolId)) AND STATUS=1 ";
		if (!includeNonViewMenu) queryString += "and is_view_menu=1 ";
		queryString += "ORDER BY FIELD(ROLE, 'MODULE', 'SUB_MODULE', 'MENU_LINE'), RANK ASC";
					
		Session session = sessionfactory.getCurrentSession();
		SQLQuery query = session.createSQLQuery(queryString);
		query.setParameter("userRoleSchoolId", userRoleSchoolId);
		query.setResultTransformer(Transformers.aliasToBean(ModuleNewDTO.class));
		return query.list();
	}

	@Override
	public List<ModuleNewDTO> findAllByPackageId(Long packageTemplateInfoId) {
		String queryString = "SELECT DISTINCT id, name, target_url AS targetUrl, role, parent_module_id AS parentModuleId, icon, rank FROM tbl_module_new WHERE id IN (SELECT DISTINCT module_new_id FROM tbl_package_template WHERE package_template_info_id=:packageTemplateInfoId) AND STATUS=1 ORDER BY FIELD(ROLE, 'MODULE', 'SUB_MODULE', 'MENU_LINE'), RANK ASC";
		Session session = sessionfactory.getCurrentSession();
		SQLQuery query = session.createSQLQuery(queryString);
		query.setParameter("packageTemplateInfoId", packageTemplateInfoId).setResultTransformer(Transformers.aliasToBean(ModuleNewDTO.class));
		return query.list();
	}

	/*@Override
	public List<ModuleNewDTO> findAllDTOs(boolean includeNonViewModule) {
		String queryString = "SELECT DISTINCT id, name, target_url AS targetUrl, role, parent_module_id AS parentModuleId, icon, rank FROM tbl_module_new WHERE id IN (SELECT DISTINCT module_new_id FROM tbl_package_template WHERE package_template_info_id=(SELECT package_template_info_id FROM tbl_settings limit 1)) AND STATUS=1 ";
		if (!includeNonViewModule) queryString += "and is_view_menu=1 ";
		queryString += "ORDER BY FIELD(ROLE, 'MODULE', 'SUB_MODULE', 'MENU_LINE'), RANK ASC";
				
		Session session = sessionfactory.getCurrentSession();
		SQLQuery query = session.createSQLQuery(queryString);
		query.setResultTransformer(Transformers.aliasToBean(ModuleNewDTO.class));
		return query.list();
	}*/

	@Override
	public List<ModuleNewAdvancedDTO> selectAll(boolean activeOnly) {
		String queryString = "select mn.id, mn.name, mn.status, mn.is_view_menu as viewMenu, mn.icon, mn.rank, mn.target_url as targetUrl, mn.created_date as createdDate, mn.modified_date as modifiedDate, mnp.name as parentModuleName, mn.role, mn.permission_key as permissionKey, cb.username as createdBy, mb.username as modifiedBy from tbl_module_new mn left join tbl_module_new mnp on mnp.id=mn.parent_module_id join tbl_admin cb on cb.id=mn.created_by left join tbl_admin mb on mb.id=mn.modified_by ";
		if (activeOnly) queryString += "where mn.status=1 ";
		queryString += " order by mn.created_date desc";
				
		Session session = sessionfactory.getCurrentSession();
		SQLQuery query = session.createSQLQuery(queryString);
		query.setResultTransformer(Transformers.aliasToBean(ModuleNewAdvancedDTO.class));
		
		return query.list();
	}

	@Override
	public List<ModuleNewAdvancedDTO> filterByModuleId(Long moduleId) {
		String queryString = "select mn.id, mn.name, mn.status, mn.is_view_menu as viewMenu, mn.icon, mn.rank, mn.target_url as targetUrl, mn.created_date as createdDate, mn.modified_date as modifiedDate, mnp.name as parentModuleName, mn.role, mn.permission_key as permissionKey, cb.username as createdBy, mb.username as modifiedBy from tbl_module_new mn left join tbl_module_new mnp on mnp.id=mn.parent_module_id join tbl_admin cb on cb.id=mn.created_by left join tbl_admin mb on mb.id=mn.modified_by ";
		if (moduleId != null) queryString += "where mn.parent_module_id="+ moduleId;
		queryString += " order by mn.created_date desc";
				
		Session session = sessionfactory.getCurrentSession();
		SQLQuery query = session.createSQLQuery(queryString);
		query.setResultTransformer(Transformers.aliasToBean(ModuleNewAdvancedDTO.class));
		
		return query.list();
	}	
	
	@Override
	public List<ModuleNewAdvancedDTO> fetchAllParentModules(){
		String queryString = "SELECT distinct id, name FROM tbl_module_new WHERE parent_module_id IS NULL ORDER BY rank ASC";
		Session session = sessionfactory.getCurrentSession();
		SQLQuery query = session.createSQLQuery(queryString);
		query.setResultTransformer(Transformers.aliasToBean(ModuleNewAdvancedDTO.class));
		return query.list();
	}

	@Override
	public boolean quickUpdateModuleNew(Long id, String name, String icon, String targetUrl, String permissionKey,
			String rank, boolean status, boolean isViewMenu, Date modifiedDate, Integer modifiedBy) {
		String queryString = "UPDATE tbl_module_new SET name=:name, icon=:icon, target_url=:targetUrl, permission_key=:permissionKey, "
				+ " rank=:rank, status=:status, is_view_menu=:isViewMenu, modified_by=:modifiedBy, modified_date=:modifiedDate WHERE id=:id";
		Session session = sessionfactory.getCurrentSession();
		SQLQuery query = session.createSQLQuery(queryString);
		query.setParameter("id", id);
		query.setParameter("name", name);
		query.setParameter("icon", icon);
		query.setParameter("targetUrl", targetUrl);
		query.setParameter("permissionKey", permissionKey);
		query.setParameter("rank", rank);
		query.setParameter("status", status);
		query.setParameter("isViewMenu", isViewMenu);
		query.setParameter("modifiedDate", modifiedDate);
		query.setParameter("modifiedBy", modifiedBy);
		
		try {
			query.executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<Long> findAllSyncLogsUnavailableIds() {
		String queryString = "select id from tbl_module_new where id not in (select data_id from tbl_sync_from_admin where table_name='tbl_module_new')";
		Session session = sessionfactory.getCurrentSession();
		SQLQuery query = session.createSQLQuery(queryString);
		query.addScalar("id", LongType.INSTANCE);
		return query.list();
	}


}
