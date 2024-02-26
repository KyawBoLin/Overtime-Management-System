package com.otproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.otproject.dto.MainTeamDTO;
import com.otproject.dto.PositionDTO;
import com.otproject.dto.ProjectDTO;
import com.otproject.dto.TeamDTO;
import com.otproject.dto.TeamStructureDTO;
import com.otproject.reader.TeamStructureExcelReader;
import com.otproject.security.annotations.superAdmin.IsAdminCreate;
import com.otproject.security.annotations.superAdmin.IsAdminRead;
import com.otproject.service.MainTeamService;
import com.otproject.service.PositionService;
import com.otproject.service.ProjectService;
import com.otproject.service.TeamService;
import com.otproject.service.TeamStructureService;

@Controller
public class TeamStructureController {

	@Autowired
	TeamStructureService teamStructureService;
	
	@Autowired
	PositionService positionService;
	
	@Autowired
	ProjectService projectService;
	
	@Autowired
	TeamService teamService;
	
	@Autowired
	MainTeamService mainTeamService;
	
	@GetMapping("/teamStructure")
	public void teamStructure() {
		TeamStructureDTO teamSDto = new TeamStructureDTO();
		teamSDto.setId(3);
		teamSDto.setName("Lin");
		teamSDto.setStaffId("25-003");
		teamSDto.setCheckDelete(0);
		
		teamStructureService.TeamStructureInsert(teamSDto);
		System.out.println("teamstructure success");
		
		PositionDTO positionDto = new PositionDTO();
		positionDto.setPositionId(2);
		positionDto.setPositionName("Project Manager");
//		BigDecimal b = new BigDecimal(400000);
//		positionDto.setSalary(b);
		
		positionService.positionInsert(positionDto);
		System.out.println("position success");
		
		ProjectDTO projectDto = new ProjectDTO();
		projectDto.setProjectId(2);
		projectDto.setProjectCode("DAT_02");
		
		projectService.projectInsert(projectDto);
		System.out.println("project success");
		
		TeamDTO teamDto = new TeamDTO();
		teamDto.setTeamId(2);
		teamDto.setTeamName("AEO");
		
		teamService.teamInsert(teamDto);
		System.out.println("team successful");
		
		MainTeamDTO mainTeam = new MainTeamDTO();
		mainTeam.setId(2);
		mainTeam.setTeamStructureId(3);
		mainTeam.setPositionId(2);
		mainTeam.setProjectId(1);
		mainTeam.setTeamId(1);
		
		mainTeamService.insertMainTeam(mainTeam);
	}
	
	@IsAdminRead
	@GetMapping("/admin/upload")
	public String upload() {
		return "UPL001";
	}
	
	@IsAdminCreate
	@PostMapping("/admin/upload/uploadTeam")
	public String uploadTeamStructureFile(@RequestParam("file") MultipartFile file,ModelMap model,RedirectAttributes redirectAtt) {
	    String message = "";
	    if (TeamStructureExcelReader.hasExcelFormat(file)) {
	      try {
	    	teamStructureService.excelInsert(file);
	    	teamStructureService.mainTeamInsert(file);
	        message = "Uploaded the Team Structure file successfully: " + file.getOriginalFilename();
	        redirectAtt.addFlashAttribute("message", message);
	        return "redirect:/admin/upload";
	      } catch (Exception e) {
	        message = "Could not upload the Team Structure file: " + file.getOriginalFilename() + "!";
	        model.addAttribute("message", message);
	        return "UPL001";
	      }
	    }
	    message = "Please upload an excel file!";
	    model.addAttribute("message", message);
	    return "UPL001";
	  }
}
