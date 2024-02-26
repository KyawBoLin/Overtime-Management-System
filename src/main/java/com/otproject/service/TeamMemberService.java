package com.otproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.otproject.dto.MainTeamDTO;
import com.otproject.dto.TeamMemberResView;
import com.otproject.dto.TeamStructureDTO;
import com.otproject.repository.PositionRepository;
import com.otproject.repository.ProjectRepository;
import com.otproject.repository.TeamMemberResRepository;
import com.otproject.repository.TeamRepository;

@Service
public class TeamMemberService {
	
	@Autowired
	private TeamMemberResRepository teamMemberRepo;
	
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
	
	public TeamMemberResView teamMember(Integer id) {
		return teamMemberRepo.findByTeamMemberId(id);
	}

	public void updateTeamMember(TeamMemberResView view) {
		TeamStructureDTO teamStructureDTO = new TeamStructureDTO();
		teamStructureDTO.setId(view.getTeamStructureId());
		teamStructureDTO.setName(view.getMemberName());
		teamStructureDTO.setSalary(view.getMemberSalary());
		teamStructureDTO.setStaffId(view.getMemberStaffId());
		teamStructureDTO.setCheckDelete(0);
		teamStructureService.updateTeamStructure(teamStructureDTO, view.getTeamStructureId());
		
		int positionId = positionRepo.positionIdForManagement(view.getMemberPositionName());
		int projectId = projectRepo.projectIdForManagement(view.getMemberProjectCode());
		int teamId = teamRepo.teamIdForManagement(view.getMemberTeamName());
		MainTeamDTO mainTeamDTO = new MainTeamDTO();
		mainTeamDTO.setId(view.getMemberId());
		mainTeamDTO.setPositionId(positionId);
		mainTeamDTO.setProjectId(projectId);
		mainTeamDTO.setTeamId(teamId);
		mainTeamDTO.setTeamStructureId(view.getTeamStructureId());
		
		mainTeamService.updateMainTeam(mainTeamDTO, view.getMemberId());
	}
	
	public void deleteTeamMember(TeamMemberResView view) {
		TeamStructureDTO teamStructureDTO = new TeamStructureDTO();
		teamStructureDTO.setId(view.getTeamStructureId());
		teamStructureDTO.setName(view.getMemberName());
		teamStructureDTO.setSalary(view.getMemberSalary());
		teamStructureDTO.setStaffId(view.getMemberStaffId());
		teamStructureDTO.setCheckDelete(1);
		teamStructureService.updateTeamStructure(teamStructureDTO, view.getTeamStructureId());
		
		mainTeamService.deleteMainTeam(view.getMemberId());
	}
	
	public Page<TeamMemberResView> teamMemberPaging(Pageable pageable){
		return teamMemberRepo.findTeamMemberPagable(pageable);
	}
	
	public TeamMemberResView teamMemberDetailForOTForm(String username) {
		return teamMemberRepo.findByUsername(username);
	}
	
	public List<TeamMemberResView> teamMemberTeamInfo(String username) {
		return teamMemberRepo.teamInfoForEachMember(username);
	}
}
