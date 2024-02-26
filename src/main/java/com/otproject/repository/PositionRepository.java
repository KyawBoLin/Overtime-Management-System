package com.otproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.otproject.dto.PositionDTO;

public interface PositionRepository extends JpaRepository<PositionDTO, Integer>{
	@Query(nativeQuery = true,value="Select * From position where position_check=0")
	List<PositionDTO> findAllPositions();
	
	PositionDTO findByPositionId(Integer id);
	
	@Query(nativeQuery=true,value="SELECT * FROM position where position_name=:positionName and position_check=0")
	Integer positionIdForManagement(@Param("positionName") String positionName);
 }
