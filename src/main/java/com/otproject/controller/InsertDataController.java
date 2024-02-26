package com.otproject.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.otproject.dto.InsertDataDTO;
import com.otproject.dto.MainTeamDTO;
import com.otproject.dto.TeamStructureDTO;
import com.otproject.security.annotations.superAdmin.IsAdminCreate;
import com.otproject.security.annotations.superAdmin.IsAdminRead;
import com.otproject.service.MainTeamService;
import com.otproject.service.PositionService;
import com.otproject.service.ProjectService;
import com.otproject.service.TeamService;
import com.otproject.service.TeamStructureService;

@Controller
public class InsertDataController {
	
	@Autowired
	ProjectService projectService;
	
	@Autowired
	TeamService teamService;
	
	@Autowired
	PositionService positionService;
	
	@Autowired
	TeamStructureService teamStructureService;
	
	@Autowired
	MainTeamService mainTeamService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@IsAdminRead
	@GetMapping("/admin/insertData")
	public ModelAndView insertData(Model model) {
		model.addAttribute("staffId26", teamStructureService.lastStaffId26());
		model.addAttribute("staffId25", teamStructureService.lastStaffId25());
		return new ModelAndView("INT001","insertDataBean",new InsertDataDTO());
	}
	
	@ModelAttribute("projectList")
	public Map<String, Integer> projectList(){
		Map<String,Integer> projectList = new HashedMap<String, Integer>();
		projectList = projectService.selectAllProjectList();
		return projectList;
	}
	
	@ModelAttribute("teamList")
	public Map<String, Integer> teamList(){
		Map<String,Integer> teamList = new HashMap<String, Integer>();
		teamList = teamService.selectAllTeam();
		return teamList;
	}
	
	@ModelAttribute("positionList")
	public Map<String,Integer> positionList(){
		Map<String, Integer> positionList = new HashedMap<String, Integer>();
		positionList = positionService.selectAllPositionList();
		return positionList;
	}
	
	@IsAdminCreate
	@PostMapping("/admin/insertData/saveEmployeeData")
	public String saveEmployeeData(@ModelAttribute ("insertDataBean") 
	  @Validated InsertDataDTO insertDataBean,BindingResult result
	  ,RedirectAttributes redirectAtt) throws IOException {
		if(result.hasErrors()) {
			redirectAtt.addFlashAttribute("message", "Sorry for unconvinence,please try again.");
			return "redirect:/admin/insertData";
		}
		
		TeamStructureDTO teamStructureDTO = new TeamStructureDTO();
		teamStructureDTO.setName(insertDataBean.getName());
		teamStructureDTO.setSalary(insertDataBean.getSalary());
		teamStructureDTO.setStaffId(insertDataBean.getEmployeeId());
		teamStructureDTO.setPassword(passwordEncoder.encode("123dat"));
		teamStructureDTO.setAccountNonLocked(true);
		teamStructureDTO.setCheckDelete(0);
		
		teamStructureService.TeamStructureInsert(teamStructureDTO);
		Integer lastTeamStructureId = teamStructureService.lastTeamStructureId();
		MainTeamDTO mainTeamDTO = new MainTeamDTO();
		mainTeamDTO.setPositionId(insertDataBean.getPosition());
		mainTeamDTO.setProjectId(insertDataBean.getProjectId());
		mainTeamDTO.setTeamId(insertDataBean.getTeam());
		mainTeamDTO.setTeamStructureId(lastTeamStructureId);
		
		mainTeamService.insertMainTeam(mainTeamDTO);
		redirectAtt.addFlashAttribute("message", "Employee Data Insert Successful.");
		
		return "redirect:/admin/insertData";
	}
	
}
