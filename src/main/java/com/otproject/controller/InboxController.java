package com.otproject.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.otproject.dto.ManagementResView;
import com.otproject.dto.OTFormDTO;
import com.otproject.dto.TeamMemberResView;
import com.otproject.dto.TeamStructureDTO;
import com.otproject.security.annotations.manage.IsManageRead;
import com.otproject.service.InboxService;
import com.otproject.service.ManagementService;
import com.otproject.service.OTListService;
import com.otproject.service.TeamMemberService;
import com.otproject.service.TeamStructureService;

@Controller
public class InboxController {
	
	@Autowired
	private TeamMemberService teamMemberService;
	
	@Autowired
	private TeamStructureService teamStructureService;
	
	@Autowired
	private InboxService inboxService;
	
	@Autowired
	private OTListService otListService;
	
	//	@IsManageRead
	@GetMapping("/account/inbox")
	public ModelAndView inbox(Model model, @RequestParam(defaultValue = "0") int page) {
		// Get the authentication object from the security context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        TeamMemberResView memberDetails = null;
        String positionName = null;
        TeamStructureDTO teamStructure = null;
        List<TeamMemberResView> memberTeamInfoList = null;
        Page<OTFormDTO> inboxList = null;

        // Check if the user is authenticated
        if (authentication != null && authentication.isAuthenticated()) {

            // Get the authenticated user's name
        	String username = authentication.getName();
            memberDetails = teamMemberService.teamMemberDetailForOTForm(username);
            teamStructure = teamStructureService.findByStaffId(username);
            memberTeamInfoList = teamMemberService.teamMemberTeamInfo(username);
            positionName = memberDetails.getMemberPositionName();
            Pageable pageable = PageRequest.of(page, 5);
            
            switch(positionName) {
            	case "Project Manager":
            		inboxList = inboxService.projectManagerInboxService(pageable);
            	break;
            	case "Dept Head":
            		inboxList = inboxService.departmentManagerInboxService(pageable);
            	break;
            	default:
            		inboxList = null;
            }
            if(!inboxList.hasContent()) {
            	model.addAttribute("message", "Data Not Found!");
            }
        } else {
        	// This need to return Error Handling page
        	model.addAttribute("message", "User is invalid authentication!");
        }
        
        if(inboxList.getTotalPages() != 0) {
        	model.addAttribute("inboxListTotalPages", "display pagination");
        }
        
        model.addAttribute("inboxList", inboxList);
        model.addAttribute("memberDetails", memberDetails);
        model.addAttribute("teamStructure", teamStructure);
        model.addAttribute("memberTeamInfoList", memberTeamInfoList);
        model.addAttribute("currentPage", page);
        return new ModelAndView("IBX001", "otFormBean", new OTFormDTO());
	}

	@GetMapping("/account/approvalFormView")
	public ModelAndView approvalForm(@RequestParam("id") String formId, Model model) {
		// Get the authentication object from the security context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        // Check if the user is authenticated
        if (authentication != null && authentication.isAuthenticated()) {
        	OTFormDTO formInfo = otListService.findByFormIdForRequestForm(formId);
        	model.addAttribute("formInfo", formInfo);
        	
        	if(formInfo.getProjectManager() == null) {
				model.addAttribute("projectManager", null);
			} else {
				model.addAttribute("projectManager", "PM");
			}
			
			if(formInfo.getDepManager() == null) {
				model.addAttribute("departmentHead", null);
			} else {
				model.addAttribute("departmentHead", "Dep Head");
			}
			
			if(formInfo.getDivManager() == null) {
				model.addAttribute("divisionHead", null);
			} else {
				model.addAttribute("divisionHead", "Div Head");
			}
        	return new ModelAndView("IBX003-APF", "otFormBean", new OTFormDTO());
        } else {
        	// This need to return Error Handling page
        	model.addAttribute("message", "User detail information not found!");
        	return new ModelAndView("IBX003-APF", "otFormBean", new OTFormDTO());
        }
	}
	
	@GetMapping("/account/approve")
	@ResponseBody
	public OTFormDTO approveRes(Integer id) {
		return inboxService.approve(id);
	}
	
	@PostMapping("/account/approvalForm/approve")
	public String updateApprovalForm(@ModelAttribute("otFormBean") @Validated OTFormDTO dto
			, BindingResult result, RedirectAttributes redirectAtt) throws IOException {
		if(result.hasErrors()) {
			redirectAtt.addFlashAttribute("message", "Sorry for unconvenience,please try again.");
			return "redirect:/account/inbox";
		}
		
		// Get the authentication object from the security context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        TeamMemberResView memberDetails = null;

        // Check if the user is authenticated
        if (authentication != null && authentication.isAuthenticated()) {

        	// Get the authenticated user's name
        	String username = authentication.getName();
            memberDetails = teamMemberService.teamMemberDetailForOTForm(username);

        	try {
        		inboxService.updateApprovalForm(dto, dto.getId(), memberDetails);
        		redirectAtt.addFlashAttribute("message", "Approved Successfully.");
        	} catch(Exception e) {
        		redirectAtt.addFlashAttribute("message", "Error");
        	}
        	return "redirect:/account/inbox";
        } else {
        	// This need to return Error Handling page
        	redirectAtt.addFlashAttribute("message", "User detail information not found!");
        	return "redirect:/account/inbox";
        }
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
