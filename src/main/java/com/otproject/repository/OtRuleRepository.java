package com.otproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.otproject.dto.OTRuleDTO;

public interface OtRuleRepository extends JpaRepository<OTRuleDTO, Integer>{
	@Query(nativeQuery = true, value = "Select * From ot_rule where rule_check=0")
	List<OTRuleDTO> findAllRules();
	
	OTRuleDTO findByRuleId(Integer id);
}
