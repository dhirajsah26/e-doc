package com.example.edoc.dao;

import com.example.edoc.DTO.ModuleNewAdvancedDTO;
import com.example.edoc.DTO.ModuleNewDTO;
import com.example.edoc.models.ModuleNew;

import java.util.Date;
import java.util.List;

public interface ModuleNewDao {

	public Long save(ModuleNew moduleNew);
	
	public boolean update(ModuleNew moduleNew);
	
	public boolean deleteById(Long id);
	
	public ModuleNew findById(Long id);
	
	public List<ModuleNew> findAll(boolean activeOnly);
	
	public List<ModuleNewDTO> findAllByRoleParentModuleIdAndStatus(String role, Long parentModuleId, boolean activeOnly);
	
	public ModuleNew findByIdWithJoins(Long id);
	
	public boolean deleteAllAssignedRolesByModuleId(Long moduleId);
	
	public List<ModuleNewDTO> findAllActiveByUserRoleId(Integer userRoleId, boolean includeNonViewMenu);
	
	public String fetchHelpContentByTargetUrl(String targetUrl);
	
	public List<ModuleNewDTO> findAllActive();
	
	public List<ModuleNewDTO> findAllSelected(Long packageInfoId);
	
	public List<ModuleNewDTO> findAllModule(String role, boolean activeOnly);
	
	public List<ModuleNewDTO> findAllSelectedByUserRoleMenuId(Long userRoleMenuInfoId);
	
	public List<ModuleNewDTO> findAllActiveByUserRoleSchoolId(Long userRoleSchoolId, boolean includeNonViewMenu);
	
	public List<ModuleNewDTO> findAllByPackageId(Long packageTemplateInfoId);
	
	/**
	 * detects package according to team id and fetchs all related modules
	 * @param
	 * @return
	 *//*teamId*/
	/*public List<ModuleNewDTO> findAllDTOs(boolean includeNonViewMenu);*/
	
	public List<ModuleNewAdvancedDTO> selectAll(boolean activeOnly);
	
	
	/**
	 * @author digital suyogya
	 * @param moduleId
	 * @return List<ModuleNewAdvancedDTO> whether active or de-active
	 */
	public List<ModuleNewAdvancedDTO> filterByModuleId(Long moduleId);
	public List<ModuleNewAdvancedDTO> fetchAllParentModules();
	public boolean quickUpdateModuleNew(Long id, String name, String icon, String targetUrl, String permissionKey, 
			String rank, boolean status, boolean isViewMenu, Date modifiedDate, Integer modifiedBy);
	
	/**
	 * @author Kapil Basnet
	 * @data 2019-06-13 11:20 AM
	 */
	public List<Long> findAllSyncLogsUnavailableIds();
	
}
