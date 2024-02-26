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

import com.otproject.dto.TeamDTO;
import com.otproject.security.annotations.superAdmin.IsAdminCreate;
import com.otproject.security.annotations.superAdmin.IsAdminDelete;
import com.otproject.security.annotations.superAdmin.IsAdminRead;
import com.otproject.service.TeamService;

@Controller
public class TeamController {
	
	@Autowired
	TeamService teamService;
	
	@IsAdminRead
	@GetMapping("/admin/team")
	public ModelAndView team(Model model) {
		model.addAttribute("teamList", teamService.selectTeams());
		return new ModelAndView("INT001-T","teamBean",new TeamDTO());
	}
	
	@IsAdminCreate
	@PostMapping("/admin/teamInsert")
	public String teamInsert(@ModelAttribute("teamBean") @Validated TeamDTO teamBean,RedirectAttributes redirectAtt,BindingResult result) throws IOException {
		if(result.hasErrors()) {
			redirectAtt.addFlashAttribute("message", "Sorry for unconvenience,please try again.");
			return "redirect:/admin/team";
		}
		
		TeamDTO teamDto = new TeamDTO();
		teamDto.setTeamName(teamBean.getTeamName());
		teamDto.setTeamCheck(0);
		
		try {
			teamService.teamInsert(teamDto);
			redirectAtt.addFlashAttribute("message", "Team Insert Successful.");
		}catch(Exception e) {
			redirectAtt.addFlashAttribute("message", "Error.");
		}
		return "redirect:/admin/team";
	}
	
	@GetMapping("/admin/findTeam")
	@ResponseBody
	public TeamDTO findTeam(Integer id) {
		return teamService.team(id);
	}
	
	@IsAdminCreate
	@PostMapping("/admin/teamUpdate")
	public String updateTeam(@ModelAttribute("teamBean") @Validated TeamDTO dto,BindingResult result,RedirectAttributes redirectAtt) throws IOException {
		if(result.hasErrors()) {
			redirectAtt.addFlashAttribute("message", "Sorry for unconvenience,please try again.");
			return "redirect:/admin/team";
		}
		try {
			teamService.updateTeam(dto, dto.getTeamId());
			redirectAtt.addFlashAttribute("message", "Updated Successfully.");
		}catch(Exception e) {
			redirectAtt.addFlashAttribute("message", "Error.");
		}
		return "redirect:/admin/team";
	}
	
	@IsAdminDelete
	@PostMapping("/admin/teamDelete")
	public String deleteTeam(@ModelAttribute("teamBean") TeamDTO bean,RedirectAttributes redirectAttributes,BindingResult result) {
		if(result.hasErrors()) {
			redirectAttributes.addFlashAttribute("message", "Sorry for unconvenience,please try again.");
			return "redirect:/admin/team";
		}
		TeamDTO dto = new TeamDTO();
		dto.setTeamId(bean.getTeamId());
		dto.setTeamName(bean.getTeamName());
		dto.setTeamCheck(1);
		
		try {
			teamService.updateTeam(dto, bean.getTeamId());
			redirectAttributes.addFlashAttribute("message", "Deleted Successfully.");
		}catch(Exception e) {
			redirectAttributes.addFlashAttribute("message", "Error.");
		}
		return "redirect:/admin/team";
	}
}
