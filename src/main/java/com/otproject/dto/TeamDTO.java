package com.otproject.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="team")
@Data
public class TeamDTO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="team_id")
	private Integer teamId;
	
	@Column(name="team_name")
	private String teamName;
	
	@Column(name="team_check",columnDefinition = "integer default 0")
	private Integer teamCheck;
	
//	@ManyToMany(mappedBy = "teamTeam")
//	private Set<TeamStructureDTO> teamTeam;
}
