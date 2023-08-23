package com.example.edoc.models;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;

import javax.persistence.*;

@Entity
@Table(name="tbl_user_role")
@Getter @Setter
@SelectBeforeUpdate
@DynamicUpdate
public class UserRole {

	@Id
	@GeneratedValue
	private int id;
	
	@Column(nullable=false,unique=true,length=100)
	private String name;
	
	@Column(nullable=false,columnDefinition="datetime",updatable=false)
	private String created_date;
	
//	@OneToMany(fetch=FetchType.LAZY,mappedBy="userRole")
//	private List<UserRolePermission> userRolePermissions;
	
}
