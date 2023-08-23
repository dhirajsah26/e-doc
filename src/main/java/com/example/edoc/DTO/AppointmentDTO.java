package com.example.edoc.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.sql.Time;
import java.util.Date;

@Getter
@Setter
@Data
public class AppointmentDTO {
    String   description,doctorName,patientName ;
    Date appointment_date;
    BigInteger id , patient_id ,doctor_id;
    Boolean status;
    Time appointment_time;

}
