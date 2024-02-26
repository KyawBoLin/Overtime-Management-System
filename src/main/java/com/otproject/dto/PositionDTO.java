package com.otproject.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Table(name="position")
@Data
public class PositionDTO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="position_id")
	private Integer positionId;
	
	@Column(name="position_name")
	private String positionName;
	
	@Column(name="position_check",columnDefinition = "integer default 0")
	private Integer positionCheck;
	
	@Column(name="status")
	@NotNull(message = "Permission may not be null")
	private String status;
}
