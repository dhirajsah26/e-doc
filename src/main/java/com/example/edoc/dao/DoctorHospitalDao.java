package com.example.edoc.dao;

import com.example.edoc.DTO.DoctorHospitalDTO;
import com.example.edoc.models.HospitalAffiliation;

import java.util.List;

public interface DoctorHospitalDao {
    public Boolean save(HospitalAffiliation hospitalAffiliation);

    public List<HospitalAffiliation> findAll();

    public boolean delete(Long id);

    public HospitalAffiliation findById(Long id);

    public List<DoctorHospitalDTO> findHospitalAndDoctorName();

    public List<DoctorHospitalDTO> findHospitalByDoctorID(Long id);
}
