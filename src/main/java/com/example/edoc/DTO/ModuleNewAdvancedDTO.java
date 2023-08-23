package com.example.edoc.DTO;

import com.example.edoc.util.Strings;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.util.Date;

@Getter @Setter @Data
public class ModuleNewAdvancedDTO {

	private BigInteger id, parentModuleId;
	
	private boolean status, viewMenu;
	
	private Integer rank;
	
	private String name, permissionKey, role, targetUrl, icon, createdBy, modifiedBy, parentModuleName;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = Strings.TIMEZONE)
	private Date createdDate, modifiedDate;
	
}
