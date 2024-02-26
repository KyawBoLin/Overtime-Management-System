package com.otproject.service;

import static com.otproject.security.util.SecurityRoles.MEMBER;
import static com.otproject.security.util.SecurityRoles.SUPER_ADMIN;
import static com.otproject.security.util.SecurityRoles.MANAGE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.otproject.dto.TeamStructureDTO;
import com.otproject.dto.UserRolesView;
import com.otproject.repository.TeamStructureRepository;
import com.otproject.repository.UserRoleRepository;

@Service
public class SecurityUserDetailsService implements UserDetailsService{
	
	@Autowired
	private TeamStructureRepository teamStructureRepo;
	
	private final UserRoleRepository userRoleRepository;
    
    @Autowired
    public SecurityUserDetailsService(UserRoleRepository userRoleRepository) {
    	this.userRoleRepository = userRoleRepository;
    }
	
    @Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserRolesView user = userRoleRepository.findByUserRoles(username);
		String role = null;
		if(user.getStatus().equals("admin")) {
			role = SUPER_ADMIN;
		} else if(user.getStatus().equals("manage")){
			role = MANAGE;
		} else {
			role = MEMBER;
		}
        return createUserDetails(user.getUsername(), user.getPassword(), role);
	}

	public void registerUser(UserDetails user) {
		teamStructureRepo.save((TeamStructureDTO) user);
	}
	
	public UserDetails createUserDetails(String username, String password, String roles) {
		// Create and return a user details object with the specified username, password, and roles
        return org.springframework.security.core.userdetails.User
                .withUsername(username)
                .password(password)
                .roles(roles)
                .build();
	}
}
