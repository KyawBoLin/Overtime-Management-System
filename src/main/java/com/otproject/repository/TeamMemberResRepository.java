package com.otproject.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.otproject.dto.TeamMemberResView;

public interface TeamMemberResRepository extends ReadOnlyRepository<TeamMemberResView, Integer>{

	@Query(nativeQuery=true,value="select main_team.id,main_team.team_structure_id,"
			+ "pj.project_code,t.name,t.staff_id,ta.team_name,p.position_name,t.salary,t.signature\r\n"
			+ "from main_team,position p,team_structure t,team ta,project pj\r\n"
			+ "where main_team.position_id = p.position_id \r\n"
			+ "and main_team.team_structure_id = t.id\r\n"
			+ "and main_team.project_id = pj.project_id\r\n"
			+ "and main_team.team_id = ta.team_id\r\n"
			+ "and p.status = \"member\" \r\n"
			+ "and p.position_check=0 \r\n"
			+ "and t.check_delete=0\r\n"
			+ "and ta.team_check = 0\r\n"
			+ "and pj.project_check =0")
	Page<TeamMemberResView> findTeamMemberPagable(Pageable pageable);
	
	@Query(nativeQuery=true,value="select m.id,m.team_structure_id,pj.project_code,t.name,t.staff_id,"
			+ "ta.team_name,p.position_name,t.salary,t.signature\r\n"
			+ "from main_team m ,position p,team_structure t,team ta,project pj\r\n"
			+ "where m.position_id = p.position_id \r\n"
			+ "and m.team_structure_id = t.id\r\n"
			+ "and m.project_id = pj.project_id\r\n"
			+ "and m.team_id = ta.team_id\r\n"
			+ "and p.status = \"member\" \r\n"
			+ "and p.position_check=0 \r\n"
			+ "and t.check_delete=0\r\n"
			+ "and ta.team_check =0\r\n"
			+ "and pj.project_check =0\r\n"
			+ "and m.id =:id")
	TeamMemberResView findByTeamMemberId(@Param("id") Integer id);
	
	@Query(nativeQuery=true,value="select m.id,m.team_structure_id,pj.project_code,t.name,t.staff_id,"
			+ "ta.team_name,p.position_name,t.salary,t.signature \r\n"
			+ "from main_team m ,position p,team_structure t,team ta,project pj\r\n"
			+ "where m.position_id = p.position_id \r\n"
			+ "and m.team_structure_id = t.id\r\n"
			+ "and m.project_id = pj.project_id\r\n"
			+ "and m.team_id = ta.team_id\r\n"
			+ "and p.position_check=0 \r\n"
			+ "and t.check_delete=0\r\n"
			+ "and ta.team_check =0\r\n"
			+ "and pj.project_check =0\r\n"
			+ "and t.staff_id =:username\r\n"
			+ "group by t.staff_id")
	TeamMemberResView findByUsername(@Param("username") String username);
	
	@Query(nativeQuery=true,value="select m.id,m.team_structure_id,pj.project_code,t.name,t.staff_id,"
			+ "ta.team_name,p.position_name,t.salary,t.signature \r\n"
			+ "from main_team m ,position p,team_structure t,team ta,project pj\r\n"
			+ "where m.position_id = p.position_id \r\n"
			+ "and m.team_structure_id = t.id\r\n"
			+ "and m.project_id = pj.project_id\r\n"
			+ "and m.team_id = ta.team_id\r\n"
			+ "and p.position_check=0 \r\n"
			+ "and t.check_delete=0\r\n"
			+ "and ta.team_check =0\r\n"
			+ "and pj.project_check =0\r\n"
			+ "and t.staff_id =:username\r\n")
	List<TeamMemberResView> teamInfoForEachMember(@Param("username") String username);
	
}
