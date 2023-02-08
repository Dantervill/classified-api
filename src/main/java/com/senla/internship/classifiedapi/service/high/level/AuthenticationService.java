package com.senla.internship.classifiedapi.service.high.level;

import com.senla.internship.classifiedapi.dto.request.LoginRequest;
import com.senla.internship.classifiedapi.dto.response.TokenResponse;
import com.senla.internship.classifiedapi.security.jwt.JwtHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Solves problems of business logic related to authentication-specific-requirements
 * @author Vlas Nagibin
 * @version 1.0
 */
@Slf4j
@Service
public class AuthenticationService {
    private final JwtHandler jwtHandler;
    private final AuthenticationProvider authenticationProvider;

    /**
     * Constructs an {@code AuthenticationService} with a jwt handler,
     * and authentication provider.
     * @param jwtHandler the jwt handler
     * @param authenticationProvider the authentication provider
     */
    public AuthenticationService(JwtHandler jwtHandler,
                                 AuthenticationProvider authenticationProvider) {
        this.jwtHandler = jwtHandler;
        this.authenticationProvider = authenticationProvider;
    }

    /**
     * Performs authentication process. In case of invalid username or password
     * {@link AuthenticationException} throws.
     * @param loginRequest the login data access object
     * @return the jwt token
     * @throws AuthenticationException the exception
     */
    public TokenResponse performLogin(LoginRequest loginRequest) throws AuthenticationException {
        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
        log.debug("Authenticating a user by token");
        authenticationProvider.authenticate(authInputToken);
        log.debug("Generating a jwt token");
        String generatedToken = jwtHandler.generateToken(loginRequest.getEmail());
        TokenResponse tokenResponse = new TokenResponse(generatedToken, LocalDateTime.now(),
                LocalDateTime.from(LocalDateTime.now().plusMinutes(60)));
        log.info("JWT token has been sent to the Controller Layer");
        return tokenResponse;
    }
}
