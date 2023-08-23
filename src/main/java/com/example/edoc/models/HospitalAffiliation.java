package com.example.edoc.models;

import com.example.edoc.base.BaseModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tbl_hospital")
@SelectBeforeUpdate
@DynamicUpdate
@Getter
@Setter
@Data
public class HospitalAffiliation extends BaseModel {

    @ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
    @JoinColumn(name = "doctor_id", updatable = false)
    private Doctor doctor;

    @Column(name="hospital_name")
    private String hospitalName;

    @Column(name="city")
    private String city;

    @Column(name="country")
    private String country;

    @DateTimeFormat(pattern ="yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    @Column(name="start_date")
    private Date startDate;

    @DateTimeFormat(pattern ="yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    @Column(name="end_date")
    private Date endDate;


}
