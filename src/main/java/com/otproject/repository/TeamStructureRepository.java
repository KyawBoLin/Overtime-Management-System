package com.otproject.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.otproject.dto.TeamStructureDTO;

public interface TeamStructureRepository extends JpaRepository<TeamStructureDTO, Integer>{
	@Query(nativeQuery=true,value="SELECT * FROM team_structure where staff_id like '26%' order by staff_id desc limit 1")
	TeamStructureDTO lastStaffId26();
	
	@Query(nativeQuery=true,value="SELECT * FROM team_structure where staff_id like '25%' order by staff_id desc limit 1")
	TeamStructureDTO lastStaffId25();
	
	@Query(nativeQuery=true,value="select * from team_structure order by id desc limit 1;")
	TeamStructureDTO lastTeamStructureId();
	
	Optional<TeamStructureDTO> findUserByStaffId(String staffId);
	
	@Query(nativeQuery=true,value="select * from team_structure where staff_id = :staffId and check_delete = 0;")
	TeamStructureDTO findByStaffId(@Param("staffId") String staffId);
}
