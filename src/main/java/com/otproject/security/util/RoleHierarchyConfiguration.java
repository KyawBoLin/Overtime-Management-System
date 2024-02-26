package com.otproject.security.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import static com.otproject.security.util.SecurityRoles.*;

@Configuration
public class RoleHierarchyConfiguration {

	@Bean
	public RoleHierarchy roleHierarchy() {
		RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
		
		roleHierarchy.setHierarchy(
					new RolesHierarchyBuilder()
						.append(SUPER_ADMIN, ADMIN_CREATE)
						.append(SUPER_ADMIN, ADMIN_READ)
						.append(SUPER_ADMIN, ADMIN_DELETE)
						.append(SUPER_ADMIN, ADMIN_PAG_VIEW)
						
						.append(MEMBER, MEMBER_CREATE)
						.append(MEMBER, MEMBER_READ)
						.append(MEMBER, MEMBER_DELETE)
						.append(MEMBER, MEMBER_PAG_VIEW)
						
						.append(MANAGE, MANAGE_CREATE)
						.append(MANAGE, MANAGE_READ)
						.append(MANAGE, MANAGE_DELETE)
						.append(MANAGE, MANAGE_PAG_VIEW)
						
						.append(ALL_VIEWS, MEMBER)
						.append(ALL_VIEWS, MANAGE)
						.build()
				);
		return roleHierarchy;
	}
}
