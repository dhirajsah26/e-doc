package com.example.edoc.models;

import com.example.edoc.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tbl_treatment")
@SelectBeforeUpdate
@DynamicUpdate
@Getter
@Setter
@Data
@EqualsAndHashCode(callSuper=false)
public class Treatment extends BaseModel {

	@ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
	@JoinColumn(name = "patient_id", updatable = false)
	private Patient patient;

	@ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
	@JoinColumn(name = "doctor_id", updatable = false)
	private Doctor doctor;
	/*

	* @ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
	@JoinColumn(name="patient_id")
	private Patient patient;*/
	
	@Column(name="prescription_feedback")
	private String prescriptionFeedback;

	@Column(name="prescription_photo")
	private String prescriptionPhoto;

	@DateTimeFormat(pattern ="yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	@Column(name="date_of_prescription")
	private Date dateOfPrescription;

	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "created_by", updatable = false)
	private Admin createdBy;

	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "modified_by", insertable = false)
	private Admin modifiedBy;

}
