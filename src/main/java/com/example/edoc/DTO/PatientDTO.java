package com.example.edoc.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.sql.Date;

@Getter
@Setter
@Data
public class PatientDTO {
	
	    private String name, address, mobileNumber, department ,image,landlineNumber ,gender;
	    
	    private Date dateOfBirth;
	    
	    private BigInteger id;
	    
	    //*******************************
	    

	    

	    
	    
}























