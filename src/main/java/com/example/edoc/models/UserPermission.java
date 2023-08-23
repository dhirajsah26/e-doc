package com.example.edoc.models;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;

import javax.persistence.*;

@Entity
@Table(name="tbl_user_permission")
@Getter @Setter @SelectBeforeUpdate @DynamicUpdate
public class UserPermission {

	@Id
	@GeneratedValue
	private int id;
	
	@Column(nullable=false,unique=true)
	private String name;
	
	@Column(nullable=false,unique=true)
	private String permission_key;
	
	/*@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name="module_category_id",nullable=false)
	private ModuleCategory moduleCategory;*/
	
	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "module_new_id")
	private ModuleNew moduleNew;

}
