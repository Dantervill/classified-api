package com.senla.internship.classifiedapi.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * The {@code LocationRequest} class is used for transferring data
 * between client and the web application located on a server.
 * The class is associated with the {@link com.senla.internship.classifiedapi.model.advertisement.Location}
 * model object.
 * @author Vlas Nagibin
 * @version 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LocationRequest {
    @NotNull(message = "City should not be null.")
    @NotEmpty(message = "City should not be empty.")
    @NotBlank(message = "City should not be blank.")
    private String city;

    @NotNull(message = "State should not be null.")
    @NotEmpty(message = "State should not be empty.")
    @NotBlank(message = "State should not be blank.")
    private String state;
}
