package com.senla.internship.classifiedapi.security.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Vlas Nagibin
 * @version 1.0
 */
@Slf4j
@Component
public class RestAccessDeniedHandler implements AccessDeniedHandler {
    private static final String ERROR_MESSAGE_403 = "You have no access to this resource";

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        response.sendError(HttpServletResponse.SC_FORBIDDEN, ERROR_MESSAGE_403);
    }
}
