package com.otproject.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.otproject.dto.OTFormDTO;

public interface InboxRepository extends JpaRepository<OTFormDTO, Integer>{
	
	// for Project Manager Inbox only member position
	@Query(nativeQuery = true, value = "select ot.* from ot_form ot, position p\r\n"
			+ "where ot.position = p.position_name\r\n"
			+ "and ot.ot_check = 0\r\n"
			+ "and p.position_check = 0\r\n"
			+ "and p.status = \"member\"\r\n"
			+ "and ot.project_manager is null\r\n"
			+ "order by ot.ot_date desc")
	Page<OTFormDTO> projectManagerInbox(Pageable pageable);
	
	// for Department Manager Inbox only member position
	@Query(nativeQuery = true, value = "select * from ot_form\r\n"
			+ "where ot_check = 0\r\n"
			+ "and project_manager is not null\r\n"
			+ "and dep_manager is null\r\n"
			+ "order by ot_date desc")
	Page<OTFormDTO> depManagerInbox(Pageable pageable);

}
