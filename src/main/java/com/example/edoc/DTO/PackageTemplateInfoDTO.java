package com.example.edoc.DTO;

import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.util.Date;

@Getter @Setter
public class PackageTemplateInfoDTO {
	
	private BigInteger id;
	
	private String name, remarks, createdBy, modifiedBy;
	
	private Date createdDate, modifiedDate;

}
