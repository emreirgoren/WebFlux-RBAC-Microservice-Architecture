package com.xpress.auth_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterVM {

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String langKey;

}
