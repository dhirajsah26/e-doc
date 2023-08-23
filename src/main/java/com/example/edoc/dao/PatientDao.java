package com.example.edoc.dao;

import com.example.edoc.DTO.PatientDTO;
import com.example.edoc.models.Patient;

import java.util.List;


/*
* @author : Dhiraj sah

*/

public interface PatientDao {
	
    public Long save(Patient patient);

    public List<Patient> findAll();

    public boolean delete(Long id);

    public boolean update(Patient employee);

    public Patient findById(Long id);
    
    public List<PatientDTO> findEmployeeSelectedDetails();

    public Patient selectPatientByUsername(String username);

    public List<PatientDTO> findPatientByID(Long id);

}
