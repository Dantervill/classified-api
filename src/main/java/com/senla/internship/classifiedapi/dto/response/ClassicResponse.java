package com.senla.internship.classifiedapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * The {@code ClassicResponse} class is used for representing the body section of the HTTP response.
 * @author Vlas Nagibin
 * @version 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClassicResponse {
    private LocalDateTime timestamp;
    private int status;
    private String reasonPhrase;
    private String message;
    private String path;
}
