package com.example.edoc.models;
/*
* @author : Dhiraj sah

*/

import com.example.edoc.base.BaseModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tbl_patient")
@SelectBeforeUpdate
@DynamicUpdate
@Getter
@Setter
@Data
@EqualsAndHashCode(callSuper=false)
public class Patient extends BaseModel {

	@Column(name = "name", length = 100)
	private String name;

	@DateTimeFormat(pattern ="yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	@Column(name="date_of_birth")
	private Date dateOfBirth;
	
	private String gender;
	
	@Column
	private String address;
	
	@Column(name="mobile_number")
	private String mobileNumber;
	
	@Column(name="landline_number")
	private String landlineNumber;
	
	@Column
	private String image;

	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "created_by", updatable = false)
	private Patient createdBy;

	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "modified_by", insertable = false)
	private Patient modifiedBy;

	@Column(unique=true,length=50,nullable=false)
	private String username;

	@Column(unique=true,length=50,nullable=false)
	private String email;

	@Column(nullable=false)
	private String salt;

	@Column(nullable=false)
	private String password;
   
}
























