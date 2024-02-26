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

/*
 * Create management_view
 */

@Entity(name = "management_view")
@Data
@Immutable
public class ManagementResView {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@Column(name="team_structure_id")
	private Integer teamStructureId;

	@Column(name="project_code")
	private String projectCode;
	
	@Column(name="name")
	private String name;
	
	@Column(name="staff_id")
	private String staffId;
	
	@Column(name="team_name")
	private String teamName;
	
	@Column(name="position_name")
	private String positionName;
	
	@Column(name="salary")
	private BigDecimal salary;
	
	@Column(name="signature")
	private String signature;
	
	@Transient
    public String getPhotosImagePath() {
        if (signature == null || staffId == null) return null;
         
        return "../resources/staff-signature/"+staffId+"/"+ signature;
    }
}
