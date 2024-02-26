package com.otproject.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="main_team")
@Data
public class MainTeamDTO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@Column(name="team_structure_id")
	private Integer teamStructureId;
	
	@Column(name="team_id")
	private Integer teamId;
	
	@Column(name="position_id")
	private Integer positionId;
	
	@Column(name="project_id")
	private Integer projectId;
}
