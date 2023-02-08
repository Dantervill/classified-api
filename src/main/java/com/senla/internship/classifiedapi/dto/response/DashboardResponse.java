package com.senla.internship.classifiedapi.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The {@code Dashboard} class is used for representing
 * a dashboard of an admin.
 * @author Vlas Nagibin
 * @version 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DashboardResponse {
    @JsonProperty("registered_users")
    private int usersRegistered;
}
