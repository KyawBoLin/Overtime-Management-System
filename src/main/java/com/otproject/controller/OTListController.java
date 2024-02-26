package com.otproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.otproject.dto.ManagementResView;
import com.otproject.dto.OTFormDTO;
import com.otproject.dto.TeamMemberResView;
import com.otproject.dto.TeamStructureDTO;
import com.otproject.security.annotations.manage.IsManageRead;
import com.otproject.security.annotations.member.IsMemberRead;
import com.otproject.service.ManagementService;
import com.otproject.service.OTListService;
import com.otproject.service.TeamMemberService;
import com.otproject.service.TeamStructureService;

@Controller
public class OTListController {
	
	@Autowired
	private TeamMemberService teamMemberService;
	
	@Autowired
	private TeamStructureService teamStructureService;
	
	@Autowired
	private OTListService otListService;
	
	@Autowired
	private ManagementService managementService;

//	@IsManageRead
//	@IsMemberRead
	@GetMapping("/account/myOtList")
	public ModelAndView otList(Model model, @RequestParam(defaultValue = "0") int page) {
		// Get the authentication object from the security context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        TeamMemberResView memberDetails = null;
        TeamStructureDTO teamStructure = null;
        Page<OTFormDTO> formList = null;
        List<TeamMemberResView> memberTeamInfoList = null;

        // Check if the user is authenticated
        if (authentication != null && authentication.isAuthenticated()) {
        	
            // Get the authenticated user's name
            String username = authentication.getName();
            memberDetails = teamMemberService.teamMemberDetailForOTForm(username);
            teamStructure = teamStructureService.findByStaffId(username);
            memberTeamInfoList = teamMemberService.teamMemberTeamInfo(username);
            Pageable pageable = PageRequest.of(page, 5);
            formList = otListService.findByStaffId(username, pageable);
            if(!formList.hasContent()) {
            	model.addAttribute("message", "Data Not Found!");
            }
        } else {
        	// This need to return Error Handling page
        	model.addAttribute("message", "User is invalid authentication!");
        }
        
        if(formList.getTotalPages() != 0) {
        	model.addAttribute("otListTotalPages", "display pagination");
        }
        
        model.addAttribute("memberDetails", memberDetails);
        model.addAttribute("teamStructure", teamStructure);
        model.addAttribute("memberTeamInfoList", memberTeamInfoList);
        model.addAttribute("formList", formList);
        model.addAttribute("currentPage", page);
        switch (memberDetails.getMemberPositionName()) {
        case "Project Manager":
        case "Dept Head":
        case "Division Head":
        	return new ModelAndView("OTL001", "otFormBean", new OTFormDTO());
        default:
        	return new ModelAndView("OTL001-TM", "otFormBean", new OTFormDTO());
        }
	}
	
//	@IsManageRead
//	@IsMemberRead
	@SuppressWarnings("null")
	@GetMapping("/account/requestFormView")
	public String formView(@RequestParam("id") String formId, Model model) {
		// Get the authentication object from the security context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		ManagementResView manager = null;
		String createDate = null;
		String updateDate = null;
		
		// Check if the user is authenticated
        if (authentication != null && authentication.isAuthenticated()) {
			OTFormDTO formInfo = otListService.findByFormIdForRequestForm(formId);
			createDate = formInfo.getCreateTime().toString();
			model.addAttribute("createDate", createDate.substring(0, 10));
			
			if(formInfo.getUpdateTime() != null) {
				updateDate = formInfo.getUpdateTime().toString();
				model.addAttribute("updateDate", updateDate.substring(0, 10));
			} else {
				model.addAttribute("updateDate", null);
			}
			
			if(formInfo.getProjectManager() != null) {
				manager = managementService.signatureforOtRequestForm(formInfo.getProjectManager());
				model.addAttribute("projectManager", manager);
			} else {
				model.addAttribute("projectManager", null);
				manager.setSignature(null);
				model.addAttribute("projectManagerSignature", manager);
			}
			
			if(formInfo.getDepManager() != null) {
				manager = managementService.signatureforOtRequestForm(formInfo.getDepManager());
				model.addAttribute("departmentHead", manager);
			} else {
				model.addAttribute("departmentHead", null);
				manager.setSignature(null);
				model.addAttribute("departmentHeadSignature", manager);
			}
			
			if(formInfo.getDivManager() != null) {
				manager = managementService.signatureforOtRequestForm(formInfo.getDivManager());
				model.addAttribute("divisionHead", manager);
			} else {
				model.addAttribute("divisionHead", null);
				manager.setSignature(null);
				model.addAttribute("divisionHeadSignature", manager);
			}
			model.addAttribute("formInfo", formInfo);
        } else {
        	// This need to return Error Handling page
        	model.addAttribute("message", "User detail information not found!");
        }
		return "OTL002";
	}
}
