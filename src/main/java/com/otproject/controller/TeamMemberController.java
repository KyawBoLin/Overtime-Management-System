package com.otproject.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.otproject.dto.TeamMemberResView;
import com.otproject.security.annotations.superAdmin.IsAdminCreate;
import com.otproject.security.annotations.superAdmin.IsAdminDelete;
import com.otproject.security.annotations.superAdmin.IsAdminRead;
import com.otproject.service.PositionService;
import com.otproject.service.ProjectService;
import com.otproject.service.TeamMemberService;
import com.otproject.service.TeamService;

@Controller
public class TeamMemberController {

	@Autowired
	TeamMemberService teamMemberService;
	
	@Autowired
	ProjectService projectService;
	
	@Autowired
	TeamService teamService;
	
	@Autowired
	PositionService positionService;
	
	@IsAdminRead
	@GetMapping("/admin/teamMember")
	public ModelAndView teamMember(Model model,@RequestParam(defaultValue="0") int page) {
		Pageable pageable = PageRequest.of(page, 8);
		model.addAttribute("memberList", teamMemberService.teamMemberPaging(pageable));
		model.addAttribute("currentPage", page);
		return new ModelAndView("STF001","teamMemberBean",new TeamMemberResView());
	}
	
	@GetMapping("/admin/findTeamMember")
	@ResponseBody
	public TeamMemberResView findTeamMember(Integer id) {
		return teamMemberService.teamMember(id);
	}
	
	@IsAdminCreate
	@PostMapping("/admin/teamMemberUpdate")
	public String updateTeamMember(@ModelAttribute("teamMemberBean") @Validated TeamMemberResView view,
			BindingResult result,RedirectAttributes redirectAtt) throws IOException{
		if(result.hasErrors()) {
			redirectAtt.addFlashAttribute("message", "Sorry for unconvenience,please try again.");
			return "redirect:/admin/teamMember";
		}
		try {
			teamMemberService.updateTeamMember(view);
			redirectAtt.addFlashAttribute("message", "Updated Successfully.");
		}catch(Exception e) {
			redirectAtt.addFlashAttribute("message", "Error");
		}
		return "redirect:/admin/teamMember";
	}
	
	@IsAdminDelete
	@PostMapping("/admin/teamMemberDel")
	public String deleteTeamMember(@ModelAttribute("teamMemberBean") @Validated TeamMemberResView view,
			BindingResult result,RedirectAttributes redirectAtt) throws IOException{
		if(result.hasErrors()) {
			redirectAtt.addFlashAttribute("message", "Sorry for unconvenience,please try again.");
			return "redirect:/admin/teamMember";
		}
		try {
			teamMemberService.deleteTeamMember(view);
			redirectAtt.addFlashAttribute("message", "Deleted Successfully.");
		}catch(Exception e) {
			redirectAtt.addFlashAttribute("message", "Error");
		}
		return "redirect:/admin/teamMember";
	}
	
	@ModelAttribute("memberProjectList")
	public Map<String, Integer> projectList(){
		Map<String,Integer> projectList = new HashedMap<String, Integer>();
		projectList = projectService.selectAllProjectList();
		return projectList;
	}
	
	@ModelAttribute("memberTeamList")
	public Map<String, Integer> teamList(){
		Map<String,Integer> teamList = new HashMap<String, Integer>();
		teamList = teamService.selectAllTeam();
		return teamList;
	}
	
	@ModelAttribute("memberPositionList")
	public Map<String,Integer> positionList(){
		Map<String, Integer> positionList = new HashedMap<String, Integer>();
		positionList = positionService.selectAllPositionList();
		return positionList;
	}
}
