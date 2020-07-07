package com.example.ssouse.config;

import java.util.HashSet;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

/**
 * SecurityContextUtils is used to get username and roles to set created by, last updated by fields.
 */
@Component
public class SecurityContextUtils {

  private static final Logger LOGGER = LoggerFactory.getLogger(SecurityContextUtils.class);

  private static final String ANONYMOUS = "anonymous";

  private SecurityContextUtils() {
  }

  public static String getUserName() {
    SecurityContext securityContext = SecurityContextHolder.getContext();
    Authentication authentication = securityContext.getAuthentication();
    String username = ANONYMOUS;

    if (null != authentication) {
      if(authentication instanceof JwtAuthenticationToken) {
        username = ((JwtAuthenticationToken) authentication).getTokenAttributes().get("preferred_username").toString();
      } else {
        LOGGER.debug("User details not found in Security Context");
      }
    } else {
      LOGGER.debug("Request not authenticated, hence no user name available");
    }

    return username;
  }

  public static Set<String> getUserRoles() {
    SecurityContext securityContext = SecurityContextHolder.getContext();
    Authentication authentication = securityContext.getAuthentication();
    Set<String> roles = new HashSet<>();

    if (null != authentication) {
      authentication.getAuthorities()
        .forEach(e -> roles.add(e.getAuthority()));
    }
    return roles;
  }
}
