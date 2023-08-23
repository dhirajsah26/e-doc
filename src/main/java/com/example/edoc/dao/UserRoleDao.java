package com.example.edoc.dao;

import com.example.edoc.DTO.UserRoleDTO;
import com.example.edoc.models.UserRole;

import java.util.List;

public interface UserRoleDao {

	public int saveRole(UserRole r);
	
	public boolean updateRole(UserRole r);

	public boolean deleteRoleById(int id);
	
	public List<UserRole> selectAllRole();
	
	public UserRole selectRoleById(int id);
	
	public UserRoleDTO fetchById(int id);
	
	public List<UserRoleDTO> findAllRoles();
	
}
