package com.example.edoc.models;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;

import javax.persistence.*;

@Entity
@Table(name="tbl_user_role_permission_new")
@Getter
@Setter
@DynamicUpdate
@SelectBeforeUpdate
public class UserRolePermissionNew{
	
	@Id
	@GeneratedValue
	private Long id;
	
	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name="user_role_id")
	private UserRoleSchool userRoleSchool;
	
	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name="user_permission_id")
	private UserPermission userPermission;

}
