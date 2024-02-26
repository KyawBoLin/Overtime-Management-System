package com.otproject.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.otproject.dto.OTRuleDTO;
import com.otproject.security.annotations.superAdmin.IsAdminCreate;
import com.otproject.security.annotations.superAdmin.IsAdminDelete;
import com.otproject.security.annotations.superAdmin.IsAdminRead;
import com.otproject.service.OtRuleService;

@Controller
public class OtRuleController {

	@Autowired
	OtRuleService otRuleService;
	
	@IsAdminRead
	@GetMapping("/admin/otrule")
	public ModelAndView rule(Model model) {
		model.addAttribute("ruleList", otRuleService.selectRules());
		return new ModelAndView("INT001-OTR","ruleBean",new OTRuleDTO());
	}
	
	@IsAdminCreate
	@PostMapping("/admin/ruleInsert")
	public String ruleInsert(@ModelAttribute("ruleBean") @Validated OTRuleDTO ruleBean,RedirectAttributes redirectAtt,BindingResult result) throws IOException{
		if(result.hasErrors()) {
			redirectAtt.addFlashAttribute("message", "Sorry for unconvenience, please try again.");
			return "redirect:/admin/otrule";
		}
		
		OTRuleDTO ruleDto = new OTRuleDTO();
		ruleDto.setRuleName(ruleBean.getRuleName());
		ruleDto.setRuleCheck(0);
		
		try {
			otRuleService.ruleInsert(ruleDto);
			redirectAtt.addFlashAttribute("message", "OT Rule Insert Successful.");
		}catch(Exception e) {
			redirectAtt.addFlashAttribute("message", "Error");
		}
		return "redirect:/admin/otrule";
	}
	
	@GetMapping("/admin/findRule")
	@ResponseBody
	public OTRuleDTO findRule(Integer id) {
		return otRuleService.rule(id);
	}
	
	@IsAdminCreate
	@PostMapping("/admin/ruleUpdate")
	public String updateRule(@ModelAttribute("ruleBean") @Validated OTRuleDTO dto,RedirectAttributes redirectAtt,BindingResult result) throws IOException{
		if(result.hasErrors()) {
			redirectAtt.addFlashAttribute("message", "Sorry for unconvenience,please try again.");
			return "redirect:/admin/otrule";
		}
		
		try {
			otRuleService.updateRule(dto, dto.getRuleId());
			redirectAtt.addFlashAttribute("message", "Updated Successfully.");
		}catch(Exception e) {
			redirectAtt.addFlashAttribute("message", "Error");
		}
		return "redirect:/admin/otrule";
	}
	
	@IsAdminDelete
	@PostMapping("/admin/ruleDelete")
	public String deleteRule(@ModelAttribute("ruleBean") OTRuleDTO bean,RedirectAttributes redirectAtt,BindingResult result) {
		if(result.hasErrors()) {
			redirectAtt.addFlashAttribute("message", "Sorry for unconvenience,please try again.");
			return "redirect:/admin/otrule";
		}
		
		OTRuleDTO ruleDto = new OTRuleDTO();
		ruleDto.setRuleId(bean.getRuleId());
		ruleDto.setRuleName(bean.getRuleName());
		ruleDto.setRuleCheck(1);
		
		try {
			otRuleService.updateRule(ruleDto, bean.getRuleId());
			redirectAtt.addFlashAttribute("message","Deleted Successfully.");
		}catch(Exception e) {
			redirectAtt.addFlashAttribute("message", "Error");
		}
		return "redirect:/admin/otrule";
	}
}
