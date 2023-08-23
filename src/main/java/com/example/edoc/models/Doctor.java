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
@Table(name ="tbl_doctor")
@SelectBeforeUpdate
@DynamicUpdate
@Getter
@Setter
@Data
@EqualsAndHashCode(callSuper=false)
public class Doctor extends BaseModel {

    @Column(name = "name", length = 100)
    private String name;

    @DateTimeFormat(pattern ="yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    @Column(name="practicing_from_date")
    private Date practicingFromDate;

    @Column(name="professional_statement")
    private String professionalStatement;

    @Column(unique=true,length=50,nullable=false)
    private String username;

    @Column(unique=true,length=50,nullable=false)
    private String email;

    @Column(nullable=false)
    private String salt;

    @Column(nullable=false)
    private String password;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "created_by", updatable = false)
    private Doctor createdBy;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "modified_by", insertable = false)
    private Doctor modifiedBy;
}
