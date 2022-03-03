package com.krylov.renderfarm.service.impl;

import com.krylov.renderfarm.dto.RegistrationRequestDto;
import com.krylov.renderfarm.dto.UserDto;
import com.krylov.renderfarm.entity.User;
import com.krylov.renderfarm.repository.UserRepository;
import com.krylov.renderfarm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto register(RegistrationRequestDto registrationRequestDto) {
        //TODO:
        User user = new User();
        user.setUserName(registrationRequestDto.getUserName());
        user.setPassword(registrationRequestDto.getPassword().toString());
        userRepository.save(user);
        return null;
    }

    public UserDto convertToDto(User user) {
        UserDto result = new UserDto();
        result.setId(user.getId());
        result.setUserName(user.getUserName());
        return result;
    }
}
