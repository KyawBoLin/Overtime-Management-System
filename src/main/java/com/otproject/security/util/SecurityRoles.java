package com.otproject.security.util;

public interface SecurityRoles {

	String ROLE_PREFIX = "ROLE_";
	
	String SUPER_ADMIN = "SUPER_ADMIN";
	String ADMIN_CREATE = "ADMIN_CREATE";
	String ADMIN_READ = "ADMIN_READ";
	String ADMIN_DELETE = "ADMIN_DELETE";
	String ADMIN_PAG_VIEW = "ADMIN_PAG_VIEW";
	
	String MEMBER = "MEMBER";
	String MEMBER_CREATE = "MEMBER_CREATE";
	String MEMBER_READ = "MEMBER_READ";
	String MEMBER_DELETE = "MEMBER_DELETE";
	String MEMBER_PAG_VIEW = "MEMBER_PAG_VIEW";
	
	String MANAGE = "MANAGE";
	String MANAGE_CREATE = "MANAGE_CREATE";
	String MANAGE_READ = "MANAGE_READ";
	String MANAGE_DELETE = "MANAGE_DELETE";
	String MANAGE_PAG_VIEW = "MANAGE_PAG_VIEW";
	
	String ALL_VIEWS = "ALL_VIEWS";
}
