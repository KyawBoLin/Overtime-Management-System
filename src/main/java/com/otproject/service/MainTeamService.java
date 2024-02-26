package com.otproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otproject.dto.MainTeamDTO;
import com.otproject.repository.MainTeamRepository;

@Service
public class MainTeamService {

	@Autowired
	private MainTeamRepository mainTeamRepo;
	
	public void insertMainTeam(MainTeamDTO mainTeam) {
		mainTeamRepo.save(mainTeam);
	}
	
	public void updateMainTeam(MainTeamDTO mainTeam,Integer id) {
		mainTeamRepo.save(mainTeam);
	}
	
	public void deleteMainTeam(Integer id) {
		mainTeamRepo.deleteById(id);
	}
	
}
