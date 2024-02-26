package com.otproject.dto;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

@Entity
@Table(name="team_structure")
@Data

public class TeamStructureDTO implements UserDetails{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="staff_id")
	private String staffId;
	
	@Column(name="signature")
	private String sign;

	@Column(name="check_delete",columnDefinition = "integer default 0")
	private Integer checkDelete;
	
	@Column(name="salary")
	private BigDecimal salary;
	
	@Column(name="password")
	private String password;
	
	@Column(name="account_non_locked",columnDefinition = "integer default 0")
	private boolean accountNonLocked;
	
	@ManyToMany(mappedBy = "formTeam")
	private Set<OTFormDTO> formTeam;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(()->"read");
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return staffId;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Transient
    public String getPhotosImagePath() {
        if (sign == null || staffId == null) return null;
         
        return "../resources/staff-signature/"+staffId+"/"+ sign;
    }
}
