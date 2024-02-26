package com.otproject.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otproject.dto.ProjectDTO;
import com.otproject.repository.ProjectRepository;

@Service
public class ProjectService {

	@Autowired
	private ProjectRepository projectRepo;
	
	public void projectInsert(ProjectDTO project) {
		projectRepo.save(project);
	}
	
	public Map<String,Integer> selectAllProjectList(){
		Map<String,Integer> map = new HashedMap<String, Integer>();
		List<ProjectDTO> list = projectRepo.findAllProjects();
		for(ProjectDTO pid:list) {
			map.put(pid.getProjectCode(),pid.getProjectId());
		}
		return map;
	}
	
	public List<ProjectDTO> selectProjects(){
		List<ProjectDTO> list = projectRepo.findAllProjects();
		return list;
	}
	
	public ProjectDTO project(Integer id) {
		return projectRepo.findByProjectId(id);
	}
	
	public void updateProject(ProjectDTO dto,Integer id) {
		projectRepo.save(dto);
	}
}
