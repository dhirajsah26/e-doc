package com.example.edoc.models;

import com.example.edoc.base.BaseModel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="tbl_user_role_menu_info")
@Getter
@Setter
@DynamicUpdate
@SelectBeforeUpdate
public class UserRoleMenuInfo extends BaseModel {
	
	@Column(length=200)
	private String remarks;
	
	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name="user_role_school_id", nullable = false)
	private UserRoleSchool userRoleSchool;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "userRoleMenuInfo", fetch = FetchType.EAGER)
	private List<UserRoleMenu> userRoleMenus;

	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "created_by", updatable = false)
	private Admin createdBy;

	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "modified_by", insertable = false)
	private Admin modifiedBy;
}
