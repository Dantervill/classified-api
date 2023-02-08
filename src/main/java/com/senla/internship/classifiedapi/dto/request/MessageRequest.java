package com.senla.internship.classifiedapi.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * The {@code MessageRequest} class is used for obtaining data during
 * message management processes. The class is associated with the
 * {@link com.senla.internship.classifiedapi.model.account.Message}
 * model object.
 * @author Vlas Nagibin
 * @version 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageRequest {

    @NotNull(message = "Text should not be null.")
    @NotEmpty(message = "Text should not be empty.")
    private String text;
}
