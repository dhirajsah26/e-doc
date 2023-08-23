package com.example.edoc.models;

import com.example.edoc.base.BaseModel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;

import javax.persistence.*;

@Entity
@Table(name="tbl_user_role_school")
@Getter @Setter
@DynamicUpdate
@SelectBeforeUpdate
public class UserRoleSchool extends BaseModel {

	@Column(nullable=false,length=100)
	private String name;
	
	@Column(nullable = false, columnDefinition = "boolean default 1")
	private boolean status;
	
	@Column(nullable = false, columnDefinition = "tinyint(1) default 0", name="is_super_user_role")
	private boolean superUserRole;

	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "created_by", updatable = false)
	private Admin createdBy;

	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "modified_by", insertable = false)
	private Admin modifiedBy;
	
}
