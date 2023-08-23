package com.example.edoc.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.util.List;

@Getter @Setter @Data
public class ModuleNewDTO {

	private BigInteger id;
	
	private String name;
	
	private String icon;
	
	private String targetUrl;
	
	private Integer rank;
	
//	Integer totalSubModules, totalMenuLines;
	
	private BigInteger parentModuleId;
	
	private String role;
	
	private List<ModuleNewDTO> subModules;
		
}
