package com.otproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.otproject.dto.OTFormDTO;

public interface OTFormRepository extends JpaRepository<OTFormDTO, Integer>{

	// Last form id to create a form id newly.
	@Query(nativeQuery = true, value = "SELECT * FROM ot_form ORDER BY ot_id DESC LIMIT 1")
	OTFormDTO selectLastId();
	
	// update approval form.
	@Query(nativeQuery = true, value = "SELECT * FROM ot_form WHERE ot_check = 0 and ot_id = :id")
	OTFormDTO findByIdApprovalForm(@Param("id") Integer id);
}
