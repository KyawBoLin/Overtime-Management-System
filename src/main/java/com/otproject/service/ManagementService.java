package com.otproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.otproject.dto.MainTeamDTO;
import com.otproject.dto.ManagementResView;
import com.otproject.dto.TeamStructureDTO;
import com.otproject.repository.ManagementResRepository;
import com.otproject.repository.PositionRepository;
import com.otproject.repository.ProjectRepository;
import com.otproject.repository.TeamRepository;

@Service
public class ManagementService {

	@Autowired
	private ManagementResRepository managementRepo;
	
	@Autowired
	private TeamStructureService teamStructureService;
	
	@Autowired
	private MainTeamService mainTeamService;
	
	@Autowired
	private PositionRepository positionRepo;
	
	@Autowired
	private TeamRepository teamRepo;
	
	@Autowired
	private ProjectRepository projectRepo;
	
	public ManagementResView management(Integer id) {
		return managementRepo.findByManagementId(id);
	}
	
	public void updateManagement(ManagementResView view) {
		TeamStructureDTO teamStructureDTO = new TeamStructureDTO();
		teamStructureDTO.setId(view.getTeamStructureId());
		teamStructureDTO.setName(view.getName());
		teamStructureDTO.setSalary(view.getSalary());
		teamStructureDTO.setStaffId(view.getStaffId());
		teamStructureDTO.setCheckDelete(0);
		teamStructureService.updateTeamStructure(teamStructureDTO, view.getTeamStructureId());
		
		int positionId = positionRepo.positionIdForManagement(view.getPositionName());
		int projectId = projectRepo.projectIdForManagement(view.getProjectCode());
		int teamId = teamRepo.teamIdForManagement(view.getTeamName());
		MainTeamDTO mainTeamDTO = new MainTeamDTO();
		mainTeamDTO.setId(view.getId());
		mainTeamDTO.setPositionId(positionId);
		mainTeamDTO.setProjectId(projectId);
		mainTeamDTO.setTeamId(teamId);
		mainTeamDTO.setTeamStructureId(view.getTeamStructureId());
		
		mainTeamService.updateMainTeam(mainTeamDTO, view.getId());
	}
	
	public void deleteManagement(ManagementResView view) {
		TeamStructureDTO teamStructureDTO = new TeamStructureDTO();
		teamStructureDTO.setId(view.getTeamStructureId());
		teamStructureDTO.setName(view.getName());
		teamStructureDTO.setSalary(view.getSalary());
		teamStructureDTO.setStaffId(view.getStaffId());
		teamStructureDTO.setCheckDelete(1);
		teamStructureService.updateTeamStructure(teamStructureDTO, view.getTeamStructureId());
		
		mainTeamService.deleteMainTeam(view.getId());
	}
	
	public Page<ManagementResView> managementPaging(Pageable pageable){
		return managementRepo.findManagePagable(pageable);
	}
	
	public ManagementResView signatureforOtRequestForm(String username) {
		return managementRepo.findByUsernameForReqForm(username);
	}
}
