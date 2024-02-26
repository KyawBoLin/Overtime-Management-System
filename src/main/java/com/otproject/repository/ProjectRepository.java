package com.otproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.otproject.dto.ProjectDTO;

public interface ProjectRepository extends JpaRepository<ProjectDTO, Integer>{
	@Query(nativeQuery = true,value="Select * From project where project_check=0")
	List<ProjectDTO> findAllProjects();
	
	ProjectDTO findByProjectId(Integer id);
	
	@Query(nativeQuery=true,value="SELECT * FROM project where project_code=:projectCode and project_check=0")
	Integer projectIdForManagement(@Param("projectCode") String projectCode);
}
