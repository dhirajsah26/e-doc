package com.example.edoc.dao;

import com.example.edoc.DTO.DoctorSpecializationDTO;
import com.example.edoc.models.DoctorSpecialization;

import java.util.List;

public interface DoctorSpecializationDao {

    public Boolean save(DoctorSpecialization doctorSpecialization);

    public List<DoctorSpecialization> findAll();

    public boolean delete(Long id);

    public DoctorSpecialization findById(Long id);

    public List<DoctorSpecializationDTO> findAllByDoctorAndSpecialization();

    public List<DoctorSpecializationDTO> findByDoctorIDAndSpecialization(Long id);
}
