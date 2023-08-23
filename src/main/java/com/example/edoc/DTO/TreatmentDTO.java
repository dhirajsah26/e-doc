package com.example.edoc.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.sql.Date;

@Getter
@Setter
@Data
public class TreatmentDTO {
	
	private String prescriptionFeedback, prescriptionPhoto,patientName ,dName;
	
	private Date dateOfPrescription;
	
	private BigInteger id , pId , doctorId;
	
}
