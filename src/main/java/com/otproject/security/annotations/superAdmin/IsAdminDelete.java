package com.otproject.security.annotations.superAdmin;

import static com.otproject.security.util.SecurityRoles.*;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.security.access.annotation.Secured;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Secured(ROLE_PREFIX + ADMIN_DELETE)
public @interface IsAdminDelete {

}
