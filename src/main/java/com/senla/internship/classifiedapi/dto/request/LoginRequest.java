package com.senla.internship.classifiedapi.dto.request;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * The {@code LoginRequest} class is used for obtaining data during
 * the authentication process.
 * @author Vlas Nagibin
 * @version 1.0
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    @NotNull(message = "Email should not be null")
    @NotEmpty(message = "Email should not be empty")
    @Email(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$",
            message = "Email should have \"@\" and \".\" before the top level domain")
    private String email;

    @NotNull(message = "Password should not be null")
    @NotEmpty(message = "Password should not be empty")
    private String password;
}
