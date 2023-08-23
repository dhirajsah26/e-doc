package com.example.edoc.dao;

import com.example.edoc.DTO.DoctorDTO;
import com.example.edoc.models.Doctor;

import java.util.List;

public interface DoctorDao {
    public Long save(Doctor doctor);

    public List<Doctor> findAll();

    public List<DoctorDTO> findAllDoctorWithSpecialization();

    public boolean delete(Long id);

    public boolean update(Doctor doctor);

    public Doctor findById(Long id);

    public List<Doctor> findDoctorSelectedDetails();

    public List<DoctorDTO> findDoctorBySpecializationID(Long id);

    public Doctor selectAdminByUsername(String username);

    public List<DoctorDTO> findAllDoctorWithSpecialization(Long id);

    public List<DoctorDTO> findDoctorByID(Long id);
}
