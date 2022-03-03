package com.krylov.renderfarm.service;

import com.krylov.renderfarm.dto.RegistrationRequestDto;
import com.krylov.renderfarm.dto.UserDto;
import com.krylov.renderfarm.entity.User;

public interface UserService {

    UserDto register(RegistrationRequestDto registrationRequestDto);

}
