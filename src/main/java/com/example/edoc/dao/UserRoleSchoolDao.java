package com.example.edoc.dao;

import com.example.edoc.DTO.UserRoleSchoolDTO;
import com.example.edoc.models.UserRoleSchool;

import java.util.List;

public interface UserRoleSchoolDao {
	
	public Long save(UserRoleSchool userRoleSchool);
	
	public boolean update(UserRoleSchool userRoleSchool);

	public boolean delete(UserRoleSchool userRoleSchool);
	
	public List<UserRoleSchool> selectAllRole();
	
	public UserRoleSchool selectById(Long id);
	
	public boolean isNameAvailable(String name, Long ignorableId);
	
	public List<UserRoleSchool> selectAllActiveRoleByTeamId(Integer teamId);
	
	public List<UserRoleSchoolDTO> findAll(boolean activeOnly, boolean includeSuperUserRole);
	
	public boolean hasAccessPrivilege(Long userRoleId, String permissionKey);
	
	public UserRoleSchoolDTO findBySchoolAccountId(Integer schoolAccountId);
	
}
