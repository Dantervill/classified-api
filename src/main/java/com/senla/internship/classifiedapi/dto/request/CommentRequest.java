package com.senla.internship.classifiedapi.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * The {@code CommentRequest} class is used for transferring data
 * between a client and the web application located on a server.
 * The class is associated with the {@link com.senla.internship.classifiedapi.model.advertisement.Comment}
 * model object.
 * @author Vlas Nagibin
 * @version 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequest {
    @NotNull(message = "Header should not be null.")
    @NotEmpty(message = "Header should not be empty.")
    private String header;

    @NotNull(message = "Body should not be null.")
    @NotEmpty(message = "Body should not be empty.")
    private String body;
}
