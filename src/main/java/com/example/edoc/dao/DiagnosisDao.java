package com.example.edoc.dao;

import com.example.edoc.DTO.DiagnosisDTO;
import com.example.edoc.models.Diagnosis;

import java.util.List;

public interface DiagnosisDao {

    public boolean save(Diagnosis diagnosis);

    public boolean update(Diagnosis diagnosis);

    public List<Diagnosis> getAll();

    public boolean delete(Long id);

    public Diagnosis getById(Long id);

    public List<DiagnosisDTO> getDiagnosisDetail();

    public DiagnosisDTO getDiagnosisDetailById(Long id);

    public List<DiagnosisDTO> getDiagnosisByPatient(Long patientId);

    public List<DiagnosisDTO> getDiagnosisByDoctor(Long doctorId);
}
