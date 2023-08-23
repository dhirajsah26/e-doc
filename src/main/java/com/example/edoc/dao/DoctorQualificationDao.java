package com.example.edoc.dao;

import com.example.edoc.DTO.DoctorQualificationDTO;
import com.example.edoc.models.Qualification;

import java.util.List;

public interface DoctorQualificationDao {
    public Boolean save(Qualification qualification);

    public List<Qualification> findAll();

    public boolean delete(Long id);

    public Qualification findById(Long id);

    public List<DoctorQualificationDTO> findQualificationAndDoctorName();

    public List<DoctorQualificationDTO> findQualificationByDoctorID(Long id);
}
