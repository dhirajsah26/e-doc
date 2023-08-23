package com.example.edoc.dao;

import com.example.edoc.DTO.AdminDto;
import com.example.edoc.DTO.DashboardDTO;
import com.example.edoc.models.Admin;
import com.example.edoc.models.Appointment;

import java.math.BigInteger;
import java.util.List;

public interface AdminDao {

	public int saveAdmin(Admin a);
	
	public Admin selectAdminByUsername(String username);
	
	public boolean updateAdmin(Admin admin);
	
	public boolean deleteAdminById(int id);
	
	public List<Admin> findAll();
	
	public Admin selectAdminById(int id);
	
	public List<AdminDto> fetchAll();
	
	public AdminDto fetchById(int id);

	public BigInteger countDoctor();

	public BigInteger countPatient();

	public BigInteger countSpecialist();

	public BigInteger countAppointment();

	public List<DashboardDTO> getCont();
	
}
