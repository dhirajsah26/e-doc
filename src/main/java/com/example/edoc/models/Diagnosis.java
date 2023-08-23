package com.example.edoc.models;

import com.example.edoc.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;

import javax.persistence.*;

@Entity
@Table(name = "tbl_diagnosis")
@SelectBeforeUpdate
@DynamicUpdate
@Getter
@Setter
@Data
@EqualsAndHashCode(callSuper=false)
public class Diagnosis extends BaseModel {

	@ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
	@JoinColumn(name = "patient_id", updatable = false)
	private Patient patient;

	@ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
	@JoinColumn(name = "doctor_id", updatable = false)
	private Doctor doctor;

	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "modified_by", insertable = false)
	private Admin modifiedBy;

	@Column(name="blood_pressure")
	public String bloodPressure;
	
	@Column(name="blood_glucose")
	public String bloodGlucose;

	@Column(name="body_weight")
	public String bodyWeight;

	@Column(name="body_mass_index")
	public String bodyMassIndex;

	@Column(name="diagnosis_photo")
	private String diagnosisPhoto;
	
}
