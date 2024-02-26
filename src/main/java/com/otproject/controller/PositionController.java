package com.otproject.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.otproject.dto.PositionDTO;
import com.otproject.dto.TeamStructureDTO;
import com.otproject.reader.PositionExcelReader;
import com.otproject.security.annotations.superAdmin.IsAdminCreate;
import com.otproject.security.annotations.superAdmin.IsAdminDelete;
import com.otproject.security.annotations.superAdmin.IsAdminRead;
import com.otproject.service.PositionService;
import com.otproject.service.TeamStructureService;

@Controller
public class PositionController {

	@Autowired
	PositionService positionService;
	
	@Autowired
	TeamStructureService teamStructureService;
	
	@IsAdminRead
	@GetMapping("/admin/position")
	public ModelAndView position(Model model) {
		model.addAttribute("positionList", positionService.selectPositions());
		return new ModelAndView("INT001-PO","positionBean",new PositionDTO());
	}
	
	@IsAdminCreate
	@PostMapping("/admin/positionInsert")
	public String positionInsert(@ModelAttribute("positionBean") @Validated PositionDTO positionBean,RedirectAttributes redirectAtt,BindingResult result) throws IOException {
		if(result.hasErrors()) {
			redirectAtt.addFlashAttribute("message", "Sorry for unconvenience,please try again.");
			return "redirect:/admin/position";
		}
		
		PositionDTO positionDto = new PositionDTO();
		positionDto.setPositionName(positionBean.getPositionName());
		positionDto.setPositionCheck(0);
		positionDto.setStatus(positionBean.getStatus());
		
		try {
			positionService.positionInsert(positionDto);
			redirectAtt.addFlashAttribute("message", "Project Insert Successful.");
		}catch(Exception e) {
			redirectAtt.addFlashAttribute("message", "Error");
		}
		return "redirect:/admin/position";
	}
	
	@GetMapping("/admin/findPosition")
	@ResponseBody
	public PositionDTO findPosition(Integer id) {
		return positionService.position(id);
	}
	
	@IsAdminCreate
	@PostMapping("/admin/positionUpdate")
	public String updatePosition(@ModelAttribute("positionBean") @Validated PositionDTO dto,BindingResult result,RedirectAttributes redirectAtt) throws IOException{
		if(result.hasErrors()) {
			redirectAtt.addFlashAttribute("message", "Sorry for unconvenience,please try again.");
			return "redirect:/admin/position";
		}
		try {
			positionService.updatePosition(dto, dto.getPositionId());
			redirectAtt.addFlashAttribute("message", "Updated Successfully.");
		}catch(Exception e) {
			redirectAtt.addFlashAttribute("message","Error");
		}
		return "redirect:/admin/position";
	}
	
	@IsAdminDelete
	@PostMapping("/admin/positionDelete")
	public String deletePosition(@ModelAttribute("positionBean") PositionDTO bean,RedirectAttributes redirectAtt,BindingResult result) {
		if(result.hasErrors()) {
			redirectAtt.addFlashAttribute("message", "Sorry for unconvenience,please try again.");
			return "redirect:/admin/position";
		}
		
		PositionDTO dto = new PositionDTO();
		dto.setPositionId(bean.getPositionId());
		dto.setPositionName(bean.getPositionName());
		dto.setPositionCheck(1);
		
		try {
			positionService.updatePosition(dto, bean.getPositionId());
			redirectAtt.addFlashAttribute("message", "Deleted Successfully.");
		}catch(Exception e) {
			redirectAtt.addFlashAttribute("message", "Error");
		}
		return "redirect:/admin/position";
	}
			
	
	@IsAdminCreate
	@PostMapping("/admin/position/uploadPosition")
	public String uploadSalaryFile(@RequestParam("fileSalary") MultipartFile file,ModelMap model,RedirectAttributes redirectAtt) {
	    String message = "";
	    if (PositionExcelReader.hasExcelFormat(file)) {
	      try {
	    	List<TeamStructureDTO> teamStructureList = teamStructureService.selectAllTeamStructure();
	    	if(teamStructureList.size()==0) {
	    		message = "Please upload Team Structure first.";
		        redirectAtt.addFlashAttribute("message", message);
	    		return "redirect:/admin/upload";
	    	}
	    	positionService.positionExcelInsert(file);
	        message = "Uploaded the salary file successfully: " + file.getOriginalFilename();
	        redirectAtt.addFlashAttribute("message", message);
	        return "redirect:/admin/upload";
	      } catch (Exception e) {
	        message = "Could not upload the salary file: " + file.getOriginalFilename() + "!";
	        model.addAttribute("message", message);
	        return "UPL001";
	      }
	    }
	    message = "Please upload an excel file!";
	    model.addAttribute("message", message);
	    return "UPL001";
	  }
	
	@ModelAttribute("permissionList")
	public Map<String,String> permissionList(){
		Map<String,String> perList = new HashMap<String, String>();
		perList.put("member", "member");
		perList.put("manage", "manage");
		return perList;
	}
}
