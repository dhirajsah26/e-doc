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
@Table(name = "tbl_doctor_specialization")
@SelectBeforeUpdate
@DynamicUpdate
@Getter
@Setter
@Data
@EqualsAndHashCode(callSuper=false)
public class DoctorSpecialization extends BaseModel {

    @ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
    @JoinColumn(name = "doctor_id", updatable = false)
    private Doctor doctor;

    @ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
    @JoinColumn(name = "specialization_id", updatable = false)
    private Specialization specialization;


}
