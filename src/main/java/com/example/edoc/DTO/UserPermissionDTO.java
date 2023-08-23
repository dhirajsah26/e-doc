package com.example.edoc.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Getter @Setter @Data
public class UserPermissionDTO {
	
	private int id;
	
	private String name, permission_key;
	
	private BigInteger syncId, moduleId;

}
