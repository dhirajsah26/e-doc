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
@Table(name = "tbl_specialization")
@SelectBeforeUpdate
@DynamicUpdate
@Getter
@Setter
@Data
@EqualsAndHashCode(callSuper=false)
public class Specialization extends BaseModel {

    @Column(name="specialization_name")
    private String specializationName;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "created_by", updatable = false)
    private Admin createdBy;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "modified_by", insertable = false)
    private Admin modifiedBy;
}
