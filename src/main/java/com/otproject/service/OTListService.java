package com.otproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.otproject.dto.OTFormDTO;
import com.otproject.repository.OTListRepository;

@Service
public class OTListService {

	@Autowired
	private OTListRepository otListRepo;
	
	public Page<OTFormDTO> findByStaffId(String username, Pageable pageable){
		Page<OTFormDTO> list = otListRepo.findByStaffId(username, pageable);
		return list;
	}
	
	public OTFormDTO findByFormIdForRequestForm(String formId) {
		OTFormDTO dto = otListRepo.findByFormId(formId);
		return dto;
	}
}
