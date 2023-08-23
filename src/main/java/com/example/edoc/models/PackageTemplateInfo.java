package com.example.edoc.models;

import com.example.edoc.base.BaseModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tbl_package_template_info")
@Getter @Setter
public class PackageTemplateInfo extends BaseModel {
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "remarks",length = 500)
	private String remarks;
	
	@OneToMany(cascade=CascadeType.ALL,mappedBy="packageTemplateInfo", fetch=FetchType.EAGER)
	private List<PackageTemplate> packageTemplates;


	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "created_by", updatable = false)
	private Admin createdBy;

	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "modified_by", insertable = false)
	private Admin modifiedBy;
	
}
