package com.xpress.auth_service.dto;

import lombok.*;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;
    private String login;
    private String email;
    private String passwordHash;
    private String firstName;
    private String lastName;
    private boolean activated;
    private String langKey;
    private String createdBy;
    private Instant createdDate;
    private String lastModifiedBy;
    private Instant lastModifiedDate;
    private String userType;  // OWNER, ADMIN, CUSTOMER...

}
