package com.otproject.security.util;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.otproject.dto.AttemptsDTO;
import com.otproject.dto.TeamStructureDTO;
import com.otproject.repository.AttemptsRepository;
import com.otproject.repository.TeamStructureRepository;

@Component
public class AuthProvider implements AuthenticationProvider{

	private static final int ATTEMPTS_LIMIT = 3;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AttemptsRepository attemptsRepository;
	
	@Autowired
	private TeamStructureRepository teamStructureRepo;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
		String password = authentication.getCredentials().toString();
		
		// Check credentials against your stored
		if(isValidCredentials(username, password)) {
			
			Optional<AttemptsDTO> userAttempts = attemptsRepository.findAttemptsByUsername(username);
			if(userAttempts.isPresent()) {
				AttemptsDTO attempts = userAttempts.get();
				attempts.setAttempts(0);
				attemptsRepository.save(attempts);
			}
			
			// Return an Authentication object with the authenticated principal (user) and authorities (roles)
			 return new UsernamePasswordAuthenticationToken(username, null, null);
		} else {
			// Throw an exception if the credentials are invalid
			Optional<TeamStructureDTO> teamStructure = teamStructureRepo.findUserByStaffId(username);
			processFailedAttempts(username, teamStructure.orElse(null));
            throw new BadCredentialsException("Invalid credentials");
		}
		
	}

	private void processFailedAttempts (String username, TeamStructureDTO teamStructure) {
		Optional<AttemptsDTO> userAttempts = attemptsRepository.findAttemptsByUsername(username);
		int attemptsCount = 0;
		if(userAttempts.isEmpty()) {
			AttemptsDTO attempts = new AttemptsDTO();
			attempts.setUsername(username);
			attempts.setAttempts(1);
			attemptsRepository.save(attempts);
		} else {
			AttemptsDTO attempts = userAttempts.get();
			attemptsCount = attempts.getAttempts() + 1;
			attempts.setAttempts(attemptsCount);
			attemptsRepository.save(attempts);
			
			if(attemptsCount > ATTEMPTS_LIMIT) {
				teamStructure.setAccountNonLocked(false);
				teamStructureRepo.save(teamStructure);
				throw new LockedException("Too many invalid attempts. Account is locked!");
			}
		}
	}
	
	// Example method to validate the credentials
	private boolean isValidCredentials(String username, String password) {
		// Perform your validation logic here
        // This can include checking against a database, calling external services, 
		// or any other mechanism
		
		String encodedPassword = passwordEncoder.encode(password);
		// staffId and password from database
		TeamStructureDTO teamStructure = (TeamStructureDTO)findUserByUsername(username);
		String teamStructureStaffId = teamStructure.getStaffId();
		String teamStructurePassword = teamStructure.getPassword();
		
		return teamStructureStaffId.equals(username) 
				&& teamStructurePassword.equals(encodedPassword);
	}
	
	public UserDetails findUserByUsername(String username) throws UsernameNotFoundException {
		TeamStructureDTO teamStructure = teamStructureRepo.findUserByStaffId(username)
				.orElseThrow(()-> new UsernameNotFoundException("User not present"));
		
		return teamStructure;
	}
	
	@Override
	public boolean supports(Class<?> authentication) {
		return true;
	}

	// 123dat
}
