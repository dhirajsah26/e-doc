package com.example.edoc.dao;

import com.example.edoc.DTO.UserPermissionDTO;
import com.example.edoc.models.UserPermission;

import java.util.List;

public interface UserPermissionDao {

	public int savePermission(UserPermission p);
	
	public boolean updatePermission(UserPermission p);
	
	public boolean deletePermissionById(int id);
	
	public UserPermission selectPermissionById(int id);
	
	public List<UserPermission> selectAllPermission();
	
	public UserPermissionDTO fetchById(int id);
	
	public List<Integer> getUserPermissionIdsByUserRoleId(int userRoleId);
	
	public List<UserPermissionDTO> findAllSyncableByPcUniqueIdAndSyncType(String pcUniqueId, String syncType);
	
	public boolean isNameAvailable(String name, Integer ignorableId);
	
	public List<UserPermission> findAllByPackageId(Long packageId);
	
	public List<Integer> getUserPermissionIdsByUserRoleId(Long userRoleId);
	
	public List<UserPermissionDTO> findAllByModulePackageId(Long packageId);
	
}
