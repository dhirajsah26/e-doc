package com.example.edoc.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
@Data
public class UserRoleMenuDTO {

	private BigInteger id;
	
	private BigInteger menuId;
	
}
