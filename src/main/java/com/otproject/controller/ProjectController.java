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

import com.otproject.dto.ProjectDTO;
import com.otproject.security.annotations.superAdmin.IsAdminCreate;
import com.otproject.security.annotations.superAdmin.IsAdminDelete;
import com.otproject.security.annotations.superAdmin.IsAdminRead;
import com.otproject.service.ProjectService;

@Controller
public class ProjectController {

	@Autowired
	ProjectService projectService;
	
	@IsAdminRead
	@GetMapping("/admin/project")
	public ModelAndView project(Model model) {
		model.addAttribute("projectList",projectService.selectProjects());
		return new ModelAndView("INT001-PJ","projectBean",new ProjectDTO());
	}
	
	@IsAdminCreate
	@PostMapping("/admin/projectInsert")
	public String projectInsert(@ModelAttribute("projectBean") @Validated ProjectDTO projectBean,RedirectAttributes redirectAtt,BindingResult result) throws IOException {
		if(result.hasErrors()) {
			redirectAtt.addFlashAttribute("message", "Sorry for unconvenience,please try again.");
			return "redirect:/admin/project";
		}
		
		ProjectDTO projectDto = new ProjectDTO();
		projectDto.setProjectCode(projectBean.getProjectCode());
		projectDto.setProjectCheck(0);
		
		try {
			projectService.projectInsert(projectDto);
			redirectAtt.addFlashAttribute("message", "Project Insert Successful.");
		}catch(Exception e) {
			redirectAtt.addFlashAttribute("message", "Error");
		}
		return "redirect:/admin/project";
	}
	
	@GetMapping("/admin/findProject")
	@ResponseBody
	public ProjectDTO findProject(Integer id) {
		return projectService.project(id);
	}
	
	@IsAdminCreate
	@PostMapping("/admin/projectUpdate")
	public String updateProject(@ModelAttribute("projectBean") @Validated ProjectDTO dto,BindingResult result,RedirectAttributes redirectAtt) throws IOException{
		if(result.hasErrors()) {
			redirectAtt.addFlashAttribute("message", "Sorry for unconvenience,please try again.");
			return "redirect:/admin/project";
		}
		try {
			projectService.updateProject(dto, dto.getProjectId());
			redirectAtt.addFlashAttribute("message", "Updated Successfully.");
		}catch(Exception e) {
			redirectAtt.addFlashAttribute("message","Error");
		}
		return "redirect:/admin/project";
	}
	
	@IsAdminDelete
	@PostMapping("/admin/projectDelete")
	public String deleteProject(@ModelAttribute("projectBean") ProjectDTO bean,RedirectAttributes redirectAtt,BindingResult result) {
		if(result.hasErrors()) {
			redirectAtt.addFlashAttribute("message", "Sorry for unconvenience,please try again.");
			return "redirect:/admin/project";
		}
		
		ProjectDTO dto = new ProjectDTO();
		dto.setProjectId(bean.getProjectId());
		dto.setProjectCode(bean.getProjectCode());
		dto.setProjectCheck(1);
		
		try {
			projectService.updateProject(dto, bean.getProjectId());
			redirectAtt.addFlashAttribute("message","Deleted Successfully.");
		}catch(Exception e) {
			redirectAtt.addFlashAttribute("message", "Error");
		}
		return "redirect:/admin/project";
	}
}
