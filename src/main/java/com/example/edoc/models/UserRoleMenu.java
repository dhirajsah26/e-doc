package com.example.edoc.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;

import javax.persistence.*;

@Entity
@Table(name="tbl_user_role_menu")
@Getter
@Setter
@Data
@DynamicUpdate
@SelectBeforeUpdate
public class UserRoleMenu {

	@Id
	@GeneratedValue
	private Long id;
	
	@OneToOne(cascade =CascadeType.PERSIST)
	@JoinColumn(name="module_new_id")
	private ModuleNew moduleNew;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name="user_role_menu_info_id", nullable = false)
	private UserRoleMenuInfo userRoleMenuInfo;
}
