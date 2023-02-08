package com.senla.internship.classifiedapi.dto.request;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * The {@code ProfileRequest} class is used for obtaining data during
 * the profile management processes. The class is associated with the
 * {@link com.senla.internship.classifiedapi.model.account.Profile}
 * model object.
 * @author Vlas Nagibin
 * @version 1.0
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProfileRequest {
    @NotNull(message = "Name should not be null.")
    @NotEmpty(message = "Name should not be empty.")
    private String name;

    @NotNull(message = "Phone should not be null.")
    @NotEmpty(message = "Phone should not be empty.")
    @Pattern(regexp = "(^8|7|\\+7)((\\d{10})|(\\s\\(\\d{3}\\)\\s\\d{3}\\s\\d{2}\\s\\d{2}))",
            message = "Phone is not valid.")
    private String phone;
}
