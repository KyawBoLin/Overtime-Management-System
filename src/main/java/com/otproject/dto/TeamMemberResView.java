package com.otproject.dto;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.hibernate.annotations.Immutable;

import lombok.Data;

@Entity(name="teamMember_view")
@Data
@Immutable
public class TeamMemberResView {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer memberId;
	
	@Column(name="team_structure_id")
	private Integer teamStructureId;
	
	@Column(name="project_code")
	private String memberProjectCode;
	
	@Column(name="name")
	private String memberName;
	
	@Column(name="staff_id")
	private String memberStaffId;
	
	@Column(name="team_name")
	private String memberTeamName;
	
	@Column(name="position_name")
	private String memberPositionName;
	
	@Column(name="salary")
	private BigDecimal memberSalary;
	
	@Column(name="signature")
	private String signature;
	
	@Transient
    public String getPhotosImagePath() {
        if (signature == null || memberStaffId == null) return null;
         
        return "../resources/staff-signature/"+memberStaffId+"/"+ signature;
    }
	
}
