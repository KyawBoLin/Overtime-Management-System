package com.otproject.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.otproject.dto.OTFormDTO;

public interface OTListRepository extends JpaRepository<OTFormDTO, Integer>{

	@Query(nativeQuery = true,value = "select * from ot_form "
			+ "where staff_id = :username and ot_check = 0 order by ot_date desc")
	Page<OTFormDTO> findByStaffId(@Param("username") String username, Pageable pageable);
	
	@Query(nativeQuery = true, value = "select ot.*, t.signature, t.salary\r\n"
			+ "from ot_form ot, team_structure t\r\n"
			+ "where ot.form_id = :formId \r\n"
			+ "and ot.staff_id = t.staff_id\r\n"
			+ "and t.check_delete = 0\r\n"
			+ "and ot.ot_check = 0;\r\n")
	OTFormDTO findByFormId(@Param("formId") String formId);
}
