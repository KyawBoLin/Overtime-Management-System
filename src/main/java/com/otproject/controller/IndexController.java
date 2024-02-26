package com.otproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.otproject.dto.OTRuleDTO;
import com.otproject.service.OtRuleService;

@Controller
public class IndexController {
	
	@Autowired
	OtRuleService otRuleService;
	
	@GetMapping({"/","/home"})
	public ModelAndView index(Model model) {
		model.addAttribute("ruleList", otRuleService.selectRules());
		return new ModelAndView("IDX001","ruleBean",new OTRuleDTO());
	}
}
