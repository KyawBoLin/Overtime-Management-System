package com.otproject.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="ot_rule")
@Data
public class OTRuleDTO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="rule_id")
	private Integer ruleId;
	
	@Column(name="rule_name")
	private String ruleName;
	
	@Column(name="rule_check",columnDefinition = "integer default 0")
	private Integer ruleCheck;
}
