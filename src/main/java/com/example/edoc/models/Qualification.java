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
@Table(name = "tbl_qualification")
@SelectBeforeUpdate
@DynamicUpdate
@Getter
@Setter
@Data
@EqualsAndHashCode(callSuper=false)
public class Qualification extends BaseModel {

    @ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
    @JoinColumn(name = "doctor_id", updatable = false)
    private Doctor doctor;

    @Column(name="qualification_name")
    private String qualificationName;

    @Column(name="institute_name")
    private String instituteName;

    @DateTimeFormat(pattern ="yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    @Column(name="procurement_year")
    private Date procurementYear;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "created_by", updatable = false)
    private Admin createdBy;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "modified_by", insertable = false)
    private Admin modifiedBy;

}
