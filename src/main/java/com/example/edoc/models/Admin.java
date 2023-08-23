package com.example.edoc.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "tbl_admin")
@SelectBeforeUpdate
@DynamicUpdate
@Getter
@Setter
@Data
public class Admin {
	
	@Id
	@GeneratedValue
	private int id;
	
	@Column(length=50,nullable=false)
	private String name;
	
	@Column(unique=true,length=50,nullable=false)
	private String username;
	
	@Column(unique=true,length=50,nullable=false)
	private String email;
	
	@Column(unique=true,length=10)
	private String phone;
	
	@Column(nullable=false)
	private String salt;
	
	@Column(nullable=false)
	private String password;
	
	@Column
	private String image;	
	
	@Column(columnDefinition="boolean default 1")
	private boolean status;

   /* @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "role_id")
    private UserRoleSchool role;*/
	
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date")
    private Date createdDate;
    

}
