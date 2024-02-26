package com.otproject.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.otproject.dto.OTFormDTO;
import com.otproject.dto.TeamMemberResView;
import com.otproject.dto.TeamStructureDTO;
import com.otproject.security.annotations.manage.IsManageCreate;
import com.otproject.security.annotations.manage.IsManageRead;
import com.otproject.security.annotations.member.IsMemberCreate;
import com.otproject.security.annotations.member.IsMemberRead;
import com.otproject.service.OTFormService;
import com.otproject.service.TeamMemberService;
import com.otproject.service.TeamStructureService;

@Controller
public class OTFormController {

	@Autowired
	private OTFormService otFormService;
	
	@Autowired
	private TeamMemberService teamMemberService;
	
	@Autowired
	private TeamStructureService teamStructureService;
	
	@GetMapping("/otFormNew")
	public void otFormNew() {
		OTFormDTO form = new OTFormDTO();
		form.setId(3);
		form.setDay("week");
		form.setOtDate("2023-08-23");
		
		form.setFormId("DAT_03");
		form.setActualStartHr("05:00");
		form.setActualFinishHr("06:00");
		form.setReason("want ot");
		
		Set<TeamStructureDTO> teamStructure = new HashSet<TeamStructureDTO>();
		TeamStructureDTO teamStructureDto = new TeamStructureDTO();
		teamStructureDto.setId(3);
		teamStructure.add(teamStructureDto);
		form.setFormTeam(teamStructure);
		
		otFormService.insertOtForm(form);
		System.out.println("success ot form");
		
//		StatusDTO status = new StatusDTO();
//		status.setId(2);
//		status.setFormId("DAT_03");
//		LocalDateTime ctime =LocalDateTime.now();
//		status.setCreateTime(ctime);
//		status.setInboxStatus("request");
//		status.setApproveStaffId("25-01");
		
//		statusService.insertStatus(status);
		System.out.println("status success");
	}
	
//	@IsManageRead
	@GetMapping("/account/otForm")
	public ModelAndView otForm(Model model,RedirectAttributes redirectAtt) {
		// Get the authentication object from the security context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        TeamMemberResView memberDetails = null;
        TeamStructureDTO teamStructure = null;
        List<TeamMemberResView> memberTeamInfoList = null;

        // Check if the user is authenticated
        if (authentication != null && authentication.isAuthenticated()) {
            // Get the authenticated user's name
            String username = authentication.getName();
            memberDetails = teamMemberService.teamMemberDetailForOTForm(username);
            teamStructure = teamStructureService.findByStaffId(username);
            memberTeamInfoList = teamMemberService.teamMemberTeamInfo(username);
        } else {
        	model.addAttribute("message", "User detail information not found!");
        }
        model.addAttribute("memberDetails", memberDetails);
        model.addAttribute("teamStructure", teamStructure);
        model.addAttribute("memberTeamInfoList", memberTeamInfoList);
//        if(memberDetails.getMemberPositionName() IN ("Project Manager, Dept Head, Division Head")) {
//        	return new ModelAndView("OTF001","otFormBean",new OTFormDTO());
//        } else {
//        	return new ModelAndView("OTF001-TM","otFormBean",new OTFormDTO());
//        }
        
        switch (memberDetails.getMemberPositionName()) {
        case "Project Manager":
        case "Dept Head":
        case "Division Head":
        	return new ModelAndView("OTF001","otFormBean",new OTFormDTO());
        default:
        	return new ModelAndView("OTF001-TM","otFormBean",new OTFormDTO());
        }
	}
	
	@ModelAttribute("projectList")
	public Map<String, String> projectList(){
		Map<String,String> projectList = new HashedMap<String, String>();
		projectList = otFormService.selectAllProjectListOTForm();
		return projectList;
	}
	
//	@IsManageCreate
//	@IsMemberCreate
	@PostMapping("/account/otForm/uploadForm")
	public String uploadForm(@ModelAttribute("otFormBean") OTFormDTO otBean, RedirectAttributes redirectAtt
	  , BindingResult result, @RequestParam("file") MultipartFile multipartFile) throws IOException{
		
		if(result.hasErrors()) {
			redirectAtt.addFlashAttribute("message", "Sorry for unconvinence,please try again.");
			return "redirect:/account/otForm";
		}
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        TeamMemberResView memberDetails = null;
        // Check if the user is authenticated
        if (authentication != null && authentication.isAuthenticated()) {
            // Get the authenticated user's name
            String username = authentication.getName();
            memberDetails = teamMemberService.teamMemberDetailForOTForm(username);
        } else {
            redirectAtt.addFlashAttribute("message", "User is invalid.");
            return "redirect:/login";
        }
        
        // To create form id.
        OTFormDTO checkFormId = otFormService.checkFormId();
		String formId = "DAT_000001";
		Integer tablePkId;
		try {
			tablePkId = checkFormId.getId();
		} catch (Exception e) {
			tablePkId = 0;
		}
		if (tablePkId >= 99999) {
			formId = "DAT_" + (tablePkId + 1);
		} else if (tablePkId >= 9999) {
			formId = "DAT_0" + (tablePkId + 1);
		} else if (tablePkId >= 999) {
			formId = "DAT_00" + (tablePkId + 1);
		} else if (tablePkId >= 99) {
			formId = "DAT_000" + (tablePkId + 1);
		} else if (tablePkId >= 9) {
			formId = "DAT_0000" + (tablePkId + 1);
		} else {
			formId = "DAT_00000" + (tablePkId + 1);
		}
		
		LocalDateTime localDateTime = LocalDateTime.now();
		
		OTFormDTO dto = new OTFormDTO();
		dto.setFormId(formId);
		dto.setOtDate(otBean.getOtDate());
		dto.setDay(otBean.getDay());
		dto.setPlanStartHr(otBean.getPlanStartHr());
		dto.setPlanFinishHr(otBean.getPlanFinishHr());
		dto.setActualStartHr(otBean.getActualStartHr());
		dto.setActualFinishHr(otBean.getActualFinishHr());
		dto.setPlanTotalHour(otBean.getPlanTotalHour());
		dto.setActualTotalHour(otBean.getActualTotalHour());
		dto.setReason(otBean.getReason());
		dto.setProjectId(otBean.getProjectId());
		dto.setCreateTime(localDateTime);
		dto.setCreateUser(memberDetails.getMemberName());
		dto.setStaffId(memberDetails.getMemberStaffId());
		dto.setPosition(memberDetails.getMemberPositionName());
		dto.setStatus("request");
		dto.setOtCheck(0);
		dto.setSalary(memberDetails.getMemberSalary());
		dto.setProjectManager(null);
		dto.setDepManager(null);
		dto.setDivManager(null);
		dto.setHr(null);
		
		Optional<TeamStructureDTO> teamStructure = teamStructureService.findUserInfoForUpdateSignature(memberDetails.getMemberStaffId());
		
		if(!multipartFile.isEmpty()) {
			String signatureName = StringUtils.cleanPath("signature_"+memberDetails.getMemberStaffId()+".png");
	
			String uploadDir = "src/main/resources/static/resources/staff-signature/"+ memberDetails.getMemberStaffId();
			Path uploadPath = Paths.get(uploadDir);
			TeamStructureDTO teamStructureDto = new TeamStructureDTO();
			teamStructureDto.setAccountNonLocked(false);
			teamStructureDto.setCheckDelete(teamStructure.get().getCheckDelete());
			teamStructureDto.setId(teamStructure.get().getId());
			teamStructureDto.setName(teamStructure.get().getName());
			teamStructureDto.setPassword(teamStructure.get().getPassword());
			teamStructureDto.setSalary(teamStructure.get().getSalary());
			teamStructureDto.setStaffId(teamStructure.get().getStaffId());
			teamStructureDto.setSign(signatureName);
			dto.setSignature(signatureName);

			// check directory exist or not
			if (!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
			}
			
			try (InputStream inputStream = multipartFile.getInputStream()) {
				Path uploadFilePath = uploadPath.resolve(signatureName);
				Files.copy(inputStream, uploadFilePath, StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException ioe) {
				throw new IOException("Could not save signature file: " + signatureName, ioe);
			}
			teamStructureService.updateTeamStructure(teamStructureDto, teamStructure.get().getId());
		}
		
		otFormService.insertOtForm(dto);
		redirectAtt.addFlashAttribute("message", "OT form is applied successfully.");
		
		return "redirect:/account/otForm";
	}
	
}
