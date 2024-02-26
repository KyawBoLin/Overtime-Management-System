package com.otproject.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otproject.dto.OTFormDTO;
import com.otproject.dto.ProjectDTO;
import com.otproject.repository.OTFormRepository;
import com.otproject.repository.ProjectRepository;

@Service
public class OTFormService {

	@Autowired
	private OTFormRepository otFormRepo;
	
	@Autowired
	private ProjectRepository projectRepo;
	
	public void insertOtForm(OTFormDTO formDto) {
		otFormRepo.save(formDto);
	}
	
	public OTFormDTO checkFormId() {
		return otFormRepo.selectLastId();
	}
	
	public Map<String,String> selectAllProjectListOTForm(){
		Map<String,String> map = new HashedMap<String, String>();
		List<ProjectDTO> list = projectRepo.findAllProjects();
		for(ProjectDTO pid:list) {
			map.put(pid.getProjectCode(),pid.getProjectCode());
		}
		return map;
	}
}
