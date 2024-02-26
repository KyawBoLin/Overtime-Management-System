package com.otproject.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.otproject.dto.UserRolesView;

public interface UserRoleRepository extends ReadOnlyRepository<UserRolesView, Integer>{
	
	@Query(nativeQuery = true, value = "select main_team.id,team_structure.staff_id,team_structure.password,position.status\r\n"
			+ "from main_team,position,team_structure\r\n"
			+ "where \r\n"
			+ "main_team.position_id = position.position_id \r\n"
			+ "and main_team.team_structure_id = team_structure.id\r\n"
			+ "and position.position_check=0 \r\n"
			+ "and team_structure.check_delete=0\r\n"
			+ "and team_structure.staff_id=:username\r\n"
			+ "group by staff_id")
	UserRolesView findByUserRoles(@Param("username") String username);
}
