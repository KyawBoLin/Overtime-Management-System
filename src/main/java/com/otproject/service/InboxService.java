package com.otproject.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.otproject.dto.OTFormDTO;
import com.otproject.dto.TeamMemberResView;
import com.otproject.repository.InboxRepository;
import com.otproject.repository.OTFormRepository;

@Service
public class InboxService {
	
	@Autowired
	private InboxRepository inboxRepo;
	
	@Autowired
	private OTFormRepository otFormRepo;
	
	public Page<OTFormDTO> projectManagerInboxService(Pageable pageable) {
		return inboxRepo.projectManagerInbox(pageable);
	}

	public Page<OTFormDTO> departmentManagerInboxService(Pageable pageable) {
		return inboxRepo.depManagerInbox(pageable);
	}
	
	public OTFormDTO approve(Integer id) {
		return otFormRepo.findByIdApprovalForm(id);
	}
	
	public void updateApprovalForm(OTFormDTO dto, Integer id, TeamMemberResView memberDetails) {
		OTFormDTO oldDto = otFormRepo.findByIdApprovalForm(id);
		oldDto.setRemark(dto.getRemark());
		String userPosition = memberDetails.getMemberPositionName();
		LocalDateTime localDateTime = LocalDateTime.now();
		
		switch(userPosition) {
		case "Project Manager":
			oldDto.setProjectManager(memberDetails.getMemberName());
			break;
		case "Dept Head":
			oldDto.setDepManager(memberDetails.getMemberName());
			break;
		case "Division Head":
			oldDto.setDivManager(memberDetails.getMemberName());
			break;
		default:
			oldDto.setHr(memberDetails.getMemberName());;
		}
		oldDto.setUpdateUser(memberDetails.getMemberName());
		oldDto.setUpdateTime(localDateTime);
		otFormRepo.save(oldDto);
	}
}
