package com.otproject.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otproject.dto.TeamDTO;
import com.otproject.repository.TeamRepository;

@Service
public class TeamService {
	@Autowired
	private TeamRepository teamRepo;
	
	public void teamInsert(TeamDTO team) {
		teamRepo.save(team);
	}
	
	public Map<String, Integer> selectAllTeam(){
		Map<String, Integer> map = new HashMap<String, Integer>();
		List<TeamDTO> list = teamRepo.findAllTeam();
		for(TeamDTO mid:list) {
			map.put(mid.getTeamName(), mid.getTeamId());
		}
		return map;
	}
	
	public List<TeamDTO> selectTeams(){
		List<TeamDTO> list = teamRepo.findAllTeam();
		return list;
	}
	
	public TeamDTO team(Integer id) {
		return teamRepo.findByTeamId(id);
	}
	
	public void updateTeam(TeamDTO dto,Integer id) {
		teamRepo.save(dto);
	}
}
