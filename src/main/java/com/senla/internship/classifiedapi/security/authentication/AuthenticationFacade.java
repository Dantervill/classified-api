package com.senla.internship.classifiedapi.security.authentication;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * The {@code AuthenticationFacade} class is the specific implementation of the {@link IAuthenticationFacade} interface.
 * @author Vlas Nagibin
 * @version 1.0
 */
@Slf4j
@Component
public class AuthenticationFacade implements IAuthenticationFacade {

    /**
     * Returns the representation of an authentication token.
     * @return the authentication
     */
    @Override
    public Authentication getAuthentication() {
        log.info("Authentication received");
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
