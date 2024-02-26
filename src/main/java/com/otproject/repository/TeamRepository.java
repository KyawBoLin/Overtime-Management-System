package com.otproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.otproject.dto.TeamDTO;

public interface TeamRepository extends JpaRepository<TeamDTO, Integer>{
	TeamDTO findByTeamId(Integer id);
	
	@Query(nativeQuery=true,value="Select * from team where team_check=0")
	List<TeamDTO> findAllTeam();
	
	@Query(nativeQuery=true,value="SELECT * FROM team where team_name=:teamName and team_check=0")
	Integer teamIdForManagement(@Param("teamName") String teamName);
}
