package com.otproject.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.otproject.dto.ManagementResView;

public interface ManagementResRepository extends ReadOnlyRepository<ManagementResView, Integer>{
	
	@Query(nativeQuery=true,value="select m.id,m.team_structure_id,pj.project_code,t.name,t.staff_id,ta.team_name,p.position_name,t.salary \r\n"
			+ "from main_team m ,position p,team_structure t,team ta,project pj\r\n"
			+ "where m.position_id = p.position_id \r\n"
			+ "and m.team_structure_id = t.id\r\n"
			+ "and m.project_id = pj.project_id\r\n"
			+ "and m.team_id = ta.team_id\r\n"
			+ "and p.status = \"manage\" \r\n"
			+ "and p.position_check=0 \r\n"
			+ "and t.check_delete=0\r\n"
			+ "and ta.team_check =0\r\n"
			+ "and pj.project_check =0\r\n"
			+ "and m.id =:id")
	ManagementResView findByManagementId(@Param("id") Integer id);
	
	@Query(nativeQuery=true,value="select main_team.id,main_team.team_structure_id,pj.project_code,t.name,t.staff_id,ta.team_name,p.position_name,t.salary,t.signature\r\n"
			+ "from main_team,position p,team_structure t,team ta,project pj\r\n"
			+ "where main_team.position_id = p.position_id \r\n"
			+ "and main_team.team_structure_id = t.id\r\n"
			+ "and main_team.project_id = pj.project_id\r\n"
			+ "and main_team.team_id = ta.team_id\r\n"
			+ "and p.status = \"manage\" \r\n"
			+ "and p.position_check=0 \r\n"
			+ "and t.check_delete=0\r\n"
			+ "and ta.team_check = 0\r\n"
			+ "and pj.project_check =0")
	Page<ManagementResView> findManagePagable(Pageable pageable);
	
	@Query(nativeQuery=true,value="select main_team.id,main_team.team_structure_id,pj.project_code,t.name,t.staff_id,ta.team_name,p.position_name,t.salary,t.signature\r\n"
			+ "from main_team,position p,team_structure t,team ta,project pj\r\n"
			+ "where main_team.position_id = p.position_id \r\n"
			+ "and main_team.team_structure_id = t.id\r\n"
			+ "and main_team.project_id = pj.project_id\r\n"
			+ "and main_team.team_id = ta.team_id\r\n"
			+ "and p.status = \"manage\" \r\n"
			+ "and p.position_check=0 \r\n"
			+ "and t.check_delete=0\r\n"
			+ "and ta.team_check = 0\r\n"
			+ "and pj.project_check =0\r\n"
            + "and t.name = :username limit 1")
	ManagementResView findByUsernameForReqForm(@Param("username") String username);
}
