package com.otproject.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.otproject.dto.AttemptsDTO;

public interface AttemptsRepository extends JpaRepository<AttemptsDTO, Integer>{
	Optional<AttemptsDTO> findAttemptsByUsername(String username);
}
