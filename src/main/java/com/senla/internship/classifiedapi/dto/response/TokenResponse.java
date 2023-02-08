package com.senla.internship.classifiedapi.dto.response;

import lombok.*;

import java.time.LocalDateTime;

/**
 * The {@code Token} class is used for representing the jwt token
 * of an authenticated user.
 * @author Vlas Nagibin
 * @version 1.0
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TokenResponse {
    private String token;
    private LocalDateTime issuedAt;
    private LocalDateTime expiresAt;
}
