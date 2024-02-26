package com.otproject.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class InsertDataDTO {
	private Integer projectId;
	private Integer team;
	private BigDecimal salary;
	private String name;
	private String employeeId;
	private Integer position;
	private String password;
}
