package com.otproject.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

@Entity
@Table(name="ot_form")
@Data

public class OTFormDTO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ot_id")
	private Integer id;
	
	@Column(name="form_id")
	private String formId;
	
	@Column(name="ot_date")
	private String otDate;
	
	@Column(name="day")
	private String day;
	
	@Column(name="plan_start_hr")
	private String planStartHr;
	
	@Column(name="plan_finish_hr")
	private String planFinishHr;
	
	@Column(name="actual_start_hr")
	private String actualStartHr;
	
	@Column(name="actual_finish_hr")
	private String actualFinishHr;
	
	@Column(name="reason")
	private String reason;
	
	@Column(name="filename")
	private String filename;
	
	@Column(name="payment")
	private BigDecimal payment;
	
	@Column(name = "projectId")
	private String projectId;
	
	@Column(name = "createTime")
	private LocalDateTime createTime;
	
	@Column(name = "createUser")
	private String createUser;
	
	@Column(name = "updateTime")
	private LocalDateTime updateTime;
	
	@Column(name = "updateUser")
	private String updateUser;
	
	@Column(name = "staffId")
	private String staffId;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "position")
	private String position;
	
	@Column(name = "ot_check", columnDefinition = "integer default 0")
	private Integer otCheck;
	
	@Column(name = "remark")
	private String remark;
	
	@Column(name = "projectManager")
	private String projectManager;
	
	@Column(name = "depManager")
	private String depManager;
	
	@Column(name = "divManager")
	private String divManager;
	
	@Column(name = "hr")
	private String hr;
	
	@Column(name = "planTotal_hour")
	private Integer planTotalHour;
	
	@Column(name = "actualTotal_hour")
	private Integer actualTotalHour;
	
	@Column(name="signature")
	private String signature;
	
	@Column(name="salary")
	private BigDecimal salary;
	
	@ManyToMany(cascade = CascadeType.PERSIST)
	private Set<TeamStructureDTO> formTeam;
	
	@Transient
    public String getPhotosImagePath() {
        if (signature == null || staffId == null) return null;
         
        return "../resources/staff-signature/"+staffId+"/"+ signature;
    }
	
}
