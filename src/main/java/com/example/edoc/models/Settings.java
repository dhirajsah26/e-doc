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
@Table(name = "tbl_settings")
@SelectBeforeUpdate
@DynamicUpdate
@Getter
@Setter
@Data
@EqualsAndHashCode(callSuper=false)
public class Settings extends BaseModel {
	

    @Column(name = "operation_date_setting")
	private String operationDateSetting;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "package_template_info_id")
    private PackageTemplateInfo packageTemplateInfo;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "created_by", updatable = false)
    private Admin createdBy;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "modified_by", insertable = false)
    private Admin modifiedBy;

}
