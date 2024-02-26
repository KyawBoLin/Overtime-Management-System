package com.otproject.security.util;

import static com.otproject.security.util.SecurityRoles.ADMIN_PAG_VIEW;
import static com.otproject.security.util.SecurityRoles.MEMBER;
import static com.otproject.security.util.SecurityRoles.MANAGE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;

import com.otproject.service.SecurityUserDetailsService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private RoleHierarchy roleHierarchy;
	
	private final SecurityUserDetailsService userDetailsService;
	
	@Autowired
	public WebSecurityConfig(SecurityUserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		String[] staticResources = {
				"/css/**",
				"/js/**",
				"/resources/**"
		};
		
		http
				.authorizeRequests()
				.mvcMatchers(staticResources).permitAll()
				.expressionHandler(expressionHandler())
				.mvcMatchers("/","/home").permitAll()
				.mvcMatchers("/admin").hasRole(ADMIN_PAG_VIEW)
				.mvcMatchers("/account").hasRole(MANAGE)
				.mvcMatchers("/account").hasRole(MEMBER)
				.anyRequest()
				.authenticated()
				.and()
				.formLogin()
				.loginPage("/login")
				.defaultSuccessUrl("/home",true)
				//.failureUrl("/login-error")
				.permitAll()
				.and()
				.logout()
				.logoutUrl("/logout")
				.logoutSuccessUrl("/login")
				.invalidateHttpSession(true)
				.clearAuthentication(true)
				.permitAll();
	}
	
	private DefaultWebSecurityExpressionHandler expressionHandler() {
		DefaultWebSecurityExpressionHandler expressionHandler = new DefaultWebSecurityExpressionHandler();
		expressionHandler.setRoleHierarchy(roleHierarchy);
		return expressionHandler;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
