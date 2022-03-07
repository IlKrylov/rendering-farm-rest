package com.krylov.renderfarm.service.impl;

import com.krylov.renderfarm.dto.RegistrationRequestDto;
import com.krylov.renderfarm.dto.UserDto;
import com.krylov.renderfarm.entity.User;
import com.krylov.renderfarm.exception.DataBaseUpdateException;
import com.krylov.renderfarm.exception.InvalidDtoException;
import com.krylov.renderfarm.repository.UserRepository;
import com.krylov.renderfarm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto register(RegistrationRequestDto registrationRequestDto) {
        if (registrationRequestDto == null) throw new InvalidDtoException("Invalid RegistrationRequestDto");
        if (registrationRequestDto.getUserName() == null || registrationRequestDto.getPassword() == null) {
            throw new InvalidDtoException("Invalid RegistrationRequestDto data");
        }
        if (userRepository.existsByUserName(registrationRequestDto.getUserName())) {
            throw new DataBaseUpdateException("User with username='" + registrationRequestDto.getUserName() + "' already exists");
        }

        User user = new User();
        user.setUserName(registrationRequestDto.getUserName());
        user.setPassword(passwordEncoder.encode(new String(registrationRequestDto.getPassword())));


        try {
            user = userRepository.save(user);
        } catch (Exception e) {
            throw new DataBaseUpdateException("Unable to register user");
        }
        return convertToDto(user);
    }

    public UserDto convertToDto(User user) {
        UserDto result = new UserDto();
        result.setId(user.getId());
        result.setUserName(user.getUserName());
        return result;
    }
}
