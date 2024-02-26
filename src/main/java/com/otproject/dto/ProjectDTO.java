package com.otproject.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="project")
@Data
public class ProjectDTO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="project_id")
	private Integer projectId;
	
	@Column(name="project_code")
	private String projectCode;
	
	@Column(name="project_check",columnDefinition = "integer default 0")
	private Integer projectCheck;
}
