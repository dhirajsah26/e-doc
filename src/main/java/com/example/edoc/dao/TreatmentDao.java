package com.example.edoc.dao;

import com.example.edoc.DTO.TreatmentDTO;
import com.example.edoc.models.Treatment;

import java.util.List;

public interface TreatmentDao {

	public boolean save(Treatment treatment);

	public boolean update(Treatment treatment);

	public List<Treatment> getAll();

	public boolean delete(Long id);

	public Treatment getById(Long id);

	public List<TreatmentDTO> getTreatmentDetail();

	public TreatmentDTO getTreatmentDetailById(Long id);

	public List<TreatmentDTO> getTreatmentByPatient(Long patientId);

	public List<TreatmentDTO> getTreatmentByDoctor(Long doctorId);

}
