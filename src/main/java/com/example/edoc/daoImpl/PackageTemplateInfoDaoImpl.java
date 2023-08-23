package com.example.edoc.daoImpl;

import com.example.edoc.DTO.PackageTemplateBasicDTO;
import com.example.edoc.DTO.PackageTemplateInfoDTO;
import com.example.edoc.dao.PackageTemplateInfoDao;
import com.example.edoc.models.PackageTemplateInfo;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Repository
@Transactional
public class PackageTemplateInfoDaoImpl implements PackageTemplateInfoDao {

	@Resource
	SessionFactory sessionFactory;
	
	@Override
	public Long save(PackageTemplateInfo packageTemplateInfo) {
		Session session = sessionFactory.getCurrentSession();
		try {
			return (Long) session.save(packageTemplateInfo);
		} catch (Exception e) {
			return 0L;
		}
	}

	@Override
	public boolean update(PackageTemplateInfo packageTemplateInfo) {
		Session session = sessionFactory.getCurrentSession();
		try {
			session.update(packageTemplateInfo);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean delete(PackageTemplateInfo packageTemplateInfo) {
		Session session = sessionFactory.getCurrentSession();
		try {
			session.delete(packageTemplateInfo);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public PackageTemplateInfo findById(Long id) {
		Session session = sessionFactory.getCurrentSession();
		PackageTemplateInfo packageTemplateInfo = (PackageTemplateInfo) session.get(PackageTemplateInfo.class, id);
		return packageTemplateInfo;
	}

	@Override
	public List<PackageTemplateInfo> findAllPackageTemplateInfo() {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(PackageTemplateInfo.class);
		criteria.addOrder(Order.desc("createdDate")).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<PackageTemplateInfo> packageTemplateInfos = criteria.list();
		return packageTemplateInfos;
	}
	
	@Override
	public List<PackageTemplateBasicDTO> getAllModuleIdsByPackageTemplateInfoId(Long packageTemlateInfoId){
		Session session = sessionFactory.getCurrentSession();
		
		String sql = "select pti.id AS id, mn.id as menuId from tbl_package_template_info pti JOIN tbl_package_template pt ON pt.package_template_info_id = pti.id "
						+ "JOIN tbl_module_new mn ON mn.id = pt.module_new_id WHERE pti.id=:packageTemlateInfoId";
		
		SQLQuery query = (SQLQuery) session.createSQLQuery(sql).setResultTransformer(Transformers.aliasToBean(PackageTemplateBasicDTO.class));
		query.setParameter("packageTemlateInfoId", packageTemlateInfoId);
		return query.list();
	}
	
	@Override
	public boolean deleteRemovedPackageTemplatesFromPackageTemplatesInfo(List<Long> packageTemplateIds) {
		String queryString = "DELETE FROM tbl_package_template where id in (:packageTemplateId)";
		Session session = sessionFactory.getCurrentSession();
		SQLQuery query = session.createSQLQuery(queryString);
		query.setParameterList("packageTemplateId", packageTemplateIds);
		try {
			query.executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public List<PackageTemplateBasicDTO> findAllPackageTemplates(){
		Session session = sessionFactory.getCurrentSession();
		String sql = "select id AS id, name AS packageTemplateName from tbl_package_template_info pti";
		SQLQuery query = (SQLQuery) session.createSQLQuery(sql).setResultTransformer(Transformers.aliasToBean(PackageTemplateBasicDTO.class));
		return query.list();
	}

	@Override
	public List<Long> fetchAllPackageTemplateIds(Long packageTemplateInfoId) {
		String queryString = "select mn.id from tbl_package_template_info as pti join tbl_package_template as pt on pti.id=pt.package_template_info_id join tbl_module_new as mn on mn.id=pt.module_new_id where pti.id=:packageTemplateInfoId";
		Session session = sessionFactory.getCurrentSession();
		SQLQuery query = session.createSQLQuery(queryString);
		query.setParameter("packageTemplateInfoId", packageTemplateInfoId);
		return query.list();
	}

	@Override
	public List<PackageTemplateInfoDTO> findAll() {
		String queryString = "select pti.id, pti.name, pti.created_date as createdDate, pti.modified_date as modifiedDate, cb.username as createdBy, mb.username as modifiedBy, pti.remarks from tbl_package_template_info pti join tbl_admin cb on cb.id=pti.created_by left join tbl_admin mb on mb.id=pti.modified_by order by pti.created_date desc";		
		Session session = sessionFactory.getCurrentSession();
		return session.createSQLQuery(queryString).setResultTransformer(Transformers.aliasToBean(PackageTemplateInfoDTO.class)).list();
	}



}
