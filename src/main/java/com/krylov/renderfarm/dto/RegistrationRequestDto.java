package com.krylov.renderfarm.dto;

import lombok.Data;

@Data
public class RegistrationRequestDto {

    private String userName;

    private char[] password;

}
