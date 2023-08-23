package com.example.edoc.dao;

import com.example.edoc.DTO.UserRoleMenuDTO;
import com.example.edoc.models.UserRoleMenuInfo;

import java.util.List;

public interface UserRoleMenuInfoDao {
	
	public Long save(UserRoleMenuInfo userRoleMenuInfo);
	
	public boolean update(UserRoleMenuInfo userRoleMenuInfo);
	
	public boolean delete(UserRoleMenuInfo userRoleMenuInfo);
	
	public UserRoleMenuInfo selectById(Long id);
	
	public List<UserRoleMenuInfo> selectAllRoleByTeamId(Integer teamId);
	
	public UserRoleMenuInfo selectByUserRoleId(Long userRoleId);
	
	public boolean deleteByUserRoleId(Long userRoleId);
	
	public boolean deleteRemovedUserRoleMenuFromUserRoleMenuInfo(List<Long> userRoleMenuIds);
	
	public List<UserRoleMenuDTO> getAllModuleIdsByUserRoleMenuInfoId(Long userRoleMenuInfoId);
	
	public Long getUserRoleMenuInfoId(Long userRoleSchoolId);
	
	public List<Long> getAllMenuIdsFromUserRoleMenu(Long userRoleMenuInfoId);
	
	public boolean deleteAllMenuByUserRoleMenuInfoIdAndModuleIds(Long userRoleMenuInfoId, List<Long> moduleIds);
	
	public List<Long> findAllAssignedModuleIdByUserRoleMenuInfoId(Long userRoleMenuInfoId);
	
	public boolean deleteUnrelatedMenus(Integer teamId, Long packageId, List<Long> userRoleMenuInfoIds);
	
	public List<Long> findAllUserRoleMenuInfoIdsByTeamId(Integer teamId);
	
	
}
