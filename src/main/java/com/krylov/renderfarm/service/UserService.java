package com.krylov.renderfarm.service;

import com.krylov.renderfarm.dto.RegistrationRequestDto;
import com.krylov.renderfarm.dto.UserDto;

public interface UserService {

    UserDto register(RegistrationRequestDto registrationRequestDto);

}
