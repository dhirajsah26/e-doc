package com.example.edoc.dao;

import com.example.edoc.models.UserRolePermissionNew;

import java.util.List;

public interface UserRolePermissionNewDao {
	
	public Long save(UserRolePermissionNew userRolePermission);
	
	public boolean update(UserRolePermissionNew userRolePermission);
	
	public boolean deleteById(UserRolePermissionNew userRolePermission);
	
	public UserRolePermissionNew selectById(Long id);
	
	public boolean deleteByUserRoleIdAndUserPermissionId(Long userRoleId, int permissionId);
	
	public Long getUserRolePermissionIdByUserRoleIdAndUserPermissionId(Long userRoleId,int permissionId);
	
	public List<UserRolePermissionNew> selectAllUserRolePermissionByRoleId(Long userRoleId);
	
	public UserRolePermissionNew findByRoleIdAndPermissionKey(Long roleId, String permissionKey);
	
}
