package com.example.edoc.dao;

import com.example.edoc.DTO.AppointmentDTO;
import com.example.edoc.models.Appointment;

import java.util.List;

public interface AppointmentDao {

    public boolean saveOrUpdate(Appointment appointment);

    public AppointmentDTO getAppointmentByDoctorId(Long id);

    public List<Appointment> getAll();

    public List<AppointmentDTO> getAllAppointment();

    public boolean delete(Long id);

    public List<AppointmentDTO> getAppointmentsByDoctorId(Long id);

    public List<AppointmentDTO> getAppointmentsByPatientId(Long id);
}
