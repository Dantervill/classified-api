package com.senla.internship.classifiedapi.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;

/**
 * The {@code JwtHandler} class is responsible for managing jwt validation/generation processes.
 * @author Vlas Nagibin
 * @version 1.0
 */
@Slf4j
@Component
public class JwtHandler {
    private static final String SUBJECT = "User Details";
    private static final String ISSUER = "ClassifiedApi";
    private static final String CLAIM = "username";

    @Value("${jwt.secret.key}")
    private String secret;

    /**
     * Generates jwt token based on username of a user.
     * @param username the username
     * @return the jwt token
     */
    public String generateToken(String username) {
        Date expirationDate = Date.from(ZonedDateTime.now().plusMinutes(60).toInstant());
        log.info("Generated token has been returned to a user with the username {}", username);
        return JWT.create()
                .withSubject(SUBJECT)
                .withClaim(CLAIM, username)
                .withIssuedAt(new Date())
                .withIssuer(ISSUER)
                .withExpiresAt(expirationDate)
                .sign(Algorithm.HMAC256(secret));
    }

    /**
     * Validates jwt token.
     * @param token the jwt token
     * @return the username hidden in jwt
     */
    public String validateToken(String token) {
        DecodedJWT decodedJWT = null;

        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                    .withSubject(SUBJECT)
                    .withIssuer(ISSUER)
                    .build();

            decodedJWT = verifier.verify(token);
        } catch (Exception e) {
            log.error("Verification failed: {}", e.getMessage());
        }

        assert decodedJWT != null;
        log.info("Jwt token has been verified");
        return decodedJWT.getClaim("username").asString();
    }
}
