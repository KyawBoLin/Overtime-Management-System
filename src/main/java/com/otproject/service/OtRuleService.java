package com.otproject.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otproject.dto.OTRuleDTO;
import com.otproject.repository.OtRuleRepository;

@Service
public class OtRuleService {
	
	@Autowired
	private OtRuleRepository otRuleRepository;
	
	public void ruleInsert(OTRuleDTO rule) {
		otRuleRepository.save(rule);
	}
	
	public Map<String,Integer> selectAllRuleList(){
		Map<String,Integer> map = new HashedMap<String, Integer>();
		List<OTRuleDTO> list = otRuleRepository.findAllRules();
		for(OTRuleDTO rid:list) {
			map.put(rid.getRuleName(), rid.getRuleId());
		}
		return map;
	}
	
	public List<OTRuleDTO> selectRules(){
		List<OTRuleDTO> list = otRuleRepository.findAllRules();
		return list;
	}
	
	public OTRuleDTO rule(Integer id) {
		return otRuleRepository.findByRuleId(id);
	}
	
	public void updateRule(OTRuleDTO dto,Integer id) {
		otRuleRepository.save(dto);
	}
}
