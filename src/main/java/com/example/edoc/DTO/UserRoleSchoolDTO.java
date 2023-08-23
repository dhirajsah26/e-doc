package com.example.edoc.DTO;

import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Getter @Setter
public class UserRoleSchoolDTO {

	private BigInteger id;
	
	private String name;
	
	private boolean superUserRole;
	
}
