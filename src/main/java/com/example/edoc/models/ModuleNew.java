package com.example.edoc.models;

import com.example.edoc.DTO.UserPermissionDTO;
import com.example.edoc.base.BaseModel;
import com.example.edoc.enums.ModuleRole;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="tbl_module_new")
@Getter @Setter
public class ModuleNew extends BaseModel {
	
	@Column(nullable=false, length=100)
	private String name;
	
	@Column(length=50, nullable=false)
	public String icon;
	
	@Column(columnDefinition="boolean default 1")
	private boolean status;
	
	@Column(columnDefinition="int default 0")
	private int rank;
	
	@Column(name="target_url")
	private String targetUrl;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 10)
	private ModuleRole role;
	
	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name="parent_module_id")
	private ModuleNew parentModule;
	
	@Column(columnDefinition = "longtext")
	private String helpSection;
	
	@Transient
	private UserPermissionDTO userPermissionDTO;
	
	@Transient
	private List<ModuleNew> childModuleNews;
	
	@Column(columnDefinition = "boolean default 1", nullable = false, name = "is_view_menu")
	private boolean viewMenu;
	
	@Column(name="permission_key")
	private String permissionKey;

	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "created_by", updatable = false)
	private Admin createdBy;

	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "modified_by", insertable = false)
	private Admin modifiedBy;
	
}
