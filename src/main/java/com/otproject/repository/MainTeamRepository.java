package com.otproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.otproject.dto.MainTeamDTO;

public interface MainTeamRepository extends JpaRepository<MainTeamDTO, Integer>{
	
	@Query(nativeQuery=true,value="select * from main_team where id=:id and team_structure_id=:teamStructureId")
	MainTeamDTO forUpdateManagement(@Param("id") Integer id,@Param("teamStructureId") Integer teamStructureId);
	
}
