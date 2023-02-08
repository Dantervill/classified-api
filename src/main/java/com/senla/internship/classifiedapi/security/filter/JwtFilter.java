package com.senla.internship.classifiedapi.security.filter;

import com.senla.internship.classifiedapi.security.jwt.JwtHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The {@code JwtFilter} class is the specific extension of the {@link OncePerRequestFilter}.
 *
 * @author Vlas Nagibin
 * @version 1.0
 */
@Slf4j
@Component
public class JwtFilter extends OncePerRequestFilter {
    private static final String BEARER = "Bearer ";
    private static final String AUTH_HEADER = "Authorization";
    private final JwtHandler jwtHandler;
    private final UserDetailsService userDetailsService;

    /**
     * Constructs a {@code JwtFilter} with a jwt handler,
     * and a user details service.
     *
     * @param jwtHandler         the jwt handler
     * @param userDetailsService the user details service
     */
    @Autowired
    public JwtFilter(JwtHandler jwtHandler,
                     UserDetailsService userDetailsService) {
        this.jwtHandler = jwtHandler;
        this.userDetailsService = userDetailsService;
    }

    /**
     * Handles the HTTP request header and checks if jwt token is signed by the
     * application and user having a valid jwt token passed the authentication process.
     *
     * @param request the HTTP request
     * @param response the HTTP response
     * @param filterChain the filter chain
     * @throws ServletException the servlet exception
     * @throws IOException the i/o exception
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = parseJwt(request);

            if (jwt != null && !jwt.isBlank()) {
                String username = jwtHandler.validateToken(jwt);

                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        } catch (Exception e) {
            log.error("Cannot set user authentication: {}", e.getMessage());
        }

        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) {
        String authHeader = request.getHeader(AUTH_HEADER);

        if (StringUtils.hasText(authHeader) && authHeader.startsWith(BEARER)) {
            return authHeader.substring(7);
        }

        return null;
    }
}
