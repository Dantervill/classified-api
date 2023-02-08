package com.senla.internship.classifiedapi.security.authentication;

import org.springframework.security.core.Authentication;

/**
 * The {@code IAuthenticationFacade} class is the  add-on over the {@link Authentication} interface.
 * @author Vlas Nagibin
 * @version 1.0
 */
public interface IAuthenticationFacade {

    /**
     * Returns an authentication.
     * @return the authentication
     */
    Authentication getAuthentication();
}
