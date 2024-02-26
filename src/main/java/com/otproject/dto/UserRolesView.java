package com.otproject.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.Immutable;

import lombok.Data;

@Entity(name = "user_roles_view")
@Data
@Immutable
public class UserRolesView {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@Column(name="staff_id")
	private String username;
	
	@Column(name="password")
	private String password;
	
	@Column(name="status")
	private String status;
}
