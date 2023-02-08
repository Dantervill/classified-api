package com.senla.internship.classifiedapi.controller;

import com.senla.internship.classifiedapi.dto.response.DashboardResponse;
import com.senla.internship.classifiedapi.service.low.level.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The {@code AdminController} class is responsible for
 * managing admins' requests.
 * @author Vlas Nagibin
 * @version 1.0
 */

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/admin", produces = "application/json")
public class AdminController {
    private final IUserService userService;

    /**
     * Create an {@code AdminController} with a user service.
     * @param userService the user service
     */
    @Autowired
    public AdminController(IUserService userService) {
        this.userService = userService;
    }

    /**
     * Return the response entity of a dashboard and the 200 HTTP status code.
     * @return the response entity to a client
     */
    @GetMapping("/dashboard")
    public ResponseEntity<?> dashboard() {
        log.debug("Getting a dashboard");
        DashboardResponse dashboardResponse = new DashboardResponse(userService.findAll().size());

        log.info("Dashboard opened");
        return new ResponseEntity<>(dashboardResponse, HttpStatus.OK);
    }
}
