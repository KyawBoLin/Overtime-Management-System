package com.otproject.security.annotations.superAdmin;

import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import static com.otproject.security.util.SecurityRoles.*;
import org.springframework.security.access.annotation.Secured;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Secured(ROLE_PREFIX + SUPER_ADMIN)
public @interface IsSuperAdmin {

}
