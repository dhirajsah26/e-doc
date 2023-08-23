package com.example.edoc.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "tbl_package_template")
@Getter @Setter
public class PackageTemplate {

	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "package_template_info_id")
	private PackageTemplateInfo packageTemplateInfo;
	
	@OneToOne
	@JoinColumn(name = "module_new_id")
	private ModuleNew moduleNew;
	
}
